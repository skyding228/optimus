package com.netfinworks.optimus.cron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.meidusa.fastjson.JSON;
import com.netfinworks.invest.entity.InvestInstallment;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.entity.AccountCheckEntity;
import com.netfinworks.optimus.entity.ChanViewEntity;
import com.netfinworks.optimus.entity.DictEntity;
import com.netfinworks.optimus.mapper.DictEntityMapper;
import com.netfinworks.optimus.mapper.ManualEntityMapper;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.CheckingService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.repository.ChanViewRepository;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;

@Component
public class PaymentTask {
	private static Logger log = LoggerFactory.getLogger(PaymentTask.class);

	@Resource
	private CheckingService checkingService;

	@Resource
	private ConfigService configService;

	@Resource
	private AccountService accountService;

	@Resource
	private DictEntityMapper dictEntityMapper;

	@Resource
	private ManualEntityMapper manualEntityMapper;

	@Resource
	private ChanViewRepository chanViewRepository;

	/**
	 * 更新待出入款记录
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	// 每天3、5、8点执行一次
	public void scheduleUpdatePaymentRecords() {
		log.info("开始生成待出入款记录");
		accountService.updatePaymentRecords(null);
	}

	/**
	 * 生成渠道账户总览<br/>
	 * 统计渠道账户总览 日期 账户上日余额 转入通付（指从理财账户转入通付账户的） 转入理财（指从通付账户转入理财账户的） 产品购买支出
	 * 产品到期放款本金 产品到期放款利息 账户当日余额
	 */
	@SuppressWarnings("unused")
	@Scheduled(cron = "1 21 1,2,4 * * ?")
	public void statisticsChanView() {
		log.info("开始生成渠道账户总览!");
		List<String> plats = configService.getAllPlats();
		// 日期
		Date today = new Date(System.currentTimeMillis() - 24L * 3600 * 1000);
		String todayStr = FormatUtil.formatDate(today);
		Date yestoday = new Date(System.currentTimeMillis() - 2L * 24L * 3600
				* 1000);

		plats.forEach(plat -> {
			// 账户上日余额
			String accountId = accountService.getPlatAccountId(plat);
			BigDecimal yestodyBalance = accountService.getAccountBalanceOfDay(
					accountId, yestoday);
			// 账户当日余额
			BigDecimal todayBalance = accountService.getAccountBalanceOfDay(
					accountId, today);
			// 充值提现
			BigDecimal withdraw = BigDecimal.ZERO, deposit = BigDecimal.ZERO;
			withdraw = manualEntityMapper.countChanTotal(plat, today,
					OrderType.MANUAL_OUT.getValue(),
					PaymentRecordEnum.ACTION_WITHDRAW.getValue());
			deposit = manualEntityMapper.countChanTotal(plat, today,
					OrderType.CHAN_MANUAL_OUT.getValue(),
					PaymentRecordEnum.ACTION_DEPOSIT.getValue());

			// 放款总额 'ACTION_LOAN' 'manual_out'
			BigDecimal outBigDecimal = manualEntityMapper.countChanTotal(plat,
					today, OrderType.MANUAL_OUT.getValue(),
					PaymentRecordEnum.ACTION_LOAN.getValue());

			// 还款产品列表
			BigDecimal principal = BigDecimal.ZERO, interest = BigDecimal.ZERO;
			List<String> subjectNos = manualEntityMapper
					.queryChanRepayemntSubjectNos(plat, today);

			for (String no : subjectNos) {
				InvestInstallment installment = accountService
						.queryInvestInstatllment(no);
				principal = principal.add(installment.getPaidPrincipal()
						.getAmount());
				interest = interest.add(installment.getPaidInterest()
						.getAmount());
			}
			// 备付金相关
			// 备付金投资
			BigDecimal provisionInvest = manualEntityMapper
					.countProvisionInvest(plat, today);

			List<ChanViewEntity> chanViewEntities = manualEntityMapper
					.countProvisionRecovery(plat, today, configService
							.getPlatValue(plat,
									ConfigService.PLAT_PROVISION_MEMBER));
			ChanViewEntity chanViewEntity = null;
			if (chanViewEntities.size() > 0) {
				chanViewEntity = chanViewEntities.get(0);
			}
			if (chanViewEntity == null) {
				chanViewEntity = new ChanViewEntity();
			}
			chanViewEntity.setProvisionPrincipal(ifNull0(chanViewEntity
					.getProvisionPrincipal()));
			chanViewEntity.setProvisionInterest(ifNull0(chanViewEntity
					.getProvisionInterest()));
			chanViewEntity.setProvisionInvest(ifNull0(provisionInvest));
			chanViewEntity.setChanId(plat);
			chanViewEntity.setDate(today);
			chanViewEntity.setPrevious(ifNull0(yestodyBalance));
			chanViewEntity.setWithdraw(ifNull0(withdraw));
			chanViewEntity.setDeposit(ifNull0(deposit));
			chanViewEntity.setBalance(ifNull0(todayBalance));
			chanViewEntity.setInvest(ifNull0(outBigDecimal));
			chanViewEntity.setPrincipal(ifNull0(principal));
			chanViewEntity.setInterest(ifNull0(interest));
			chanViewEntity = chanViewRepository.saveOrUpdate(chanViewEntity);
			log.info("渠道账户总览:{}", JSONUtil.toJson(chanViewEntity));
		});

	}

	/**
	 * 生成对账文件
	 */
	@Scheduled(cron = "1 11 1,2,4 * * ?")
	// 每天1、4、6点执行一次
	public void genCheckingFiles() {
		log.info("开始执行对账!");
		List<String> plats = configService.getAllPlats();
		String date = FormatUtil.dateFormat.format(new Date(System
				.currentTimeMillis() - 24L * 3600 * 1000));

		plats.forEach(x -> {
			String id = makeId(x, date);
			makeDictAndSave(id, ConfigService.TYPE_CHECKING, x, "执行对账时出现异常!");

			log.info("对账:{}-{}", x, date);
			File vfFile = checkingService.getCheckingFile(x, date);
			File platFile = checkingService.getPlatCheckingFile(x, date);
			checkingFile(vfFile, platFile, x, date);

			dictEntityMapper.deleteByPrimaryKey(id);
		});
	}

	/**
	 * 对维金的文件与平台的文件进行对账
	 * 
	 * @param vfFile
	 * @param platFile
	 */
	private void checkingFile(File vfFile, File platFile, String plat,
			String date) {
		log.info("开始对账-{}-{}", date, plat);
		// 保存对账异常记录
		Map<String, AccountCheckEntity> vfMap = new HashMap<String, AccountCheckEntity>(), platMap = new HashMap<String, AccountCheckEntity>();
		// 读取对账文件
		try (BufferedReader vfReader = new BufferedReader(
				new FileReader(vfFile));
				BufferedReader platReader = new BufferedReader(new FileReader(
						platFile))) {
			String vfLine = vfReader.readLine(), platLine = platReader
					.readLine();
			boolean vfIsEnd = false, platIsEnd = false; // 文件结束了
			if (vfLine == null && platLine == null) {
				log.info("{}-{}:无充值提现交易记录!", date, plat);
				return;
			}
			AccountCheckEntity vfEntity = null, platEntity = null;
			while (true) {
				vfIsEnd = StringUtils.isEmpty(vfLine);
				platIsEnd = StringUtils.isEmpty(platLine);

				if (!vfIsEnd) {
					vfEntity = AccountCheckEntity.fromRow(vfLine);
					checkingRecord(vfMap, platMap, vfEntity);
					vfLine = vfReader.readLine();
				}
				if (!platIsEnd) {
					platEntity = AccountCheckEntity.fromRow(platLine);
					checkingRecord(platMap, vfMap, platEntity);
					platLine = platReader.readLine();
				}

				if (vfIsEnd && platIsEnd) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("{}-{}:对账失败!", date, plat, e);
		}

		// 保存对账结果
		saveCheckingResult(vfMap, platMap, plat, date);
	}

	/**
	 * 判断srcEntity是否在目标异常map中存在，如果不存在就放在自己的map中，如果存在就比较，如果相同就从目标map中移除，
	 * 否则也放入自己的异常map中
	 * 
	 * @param src
	 * @param target
	 * @param srcEntity
	 */
	private void checkingRecord(Map<String, AccountCheckEntity> src,
			Map<String, AccountCheckEntity> target, AccountCheckEntity srcEntity) {
		// 查看对方是否存在此记录
		AccountCheckEntity entity = target.get(srcEntity.getOrderId());
		if (entity == null) { // 不存在
			src.put(srcEntity.getOrderId(), srcEntity);
		} else { // 存在就进行比较
			if (srcEntity.eq(entity)) { // 相同就移除
				target.remove(srcEntity.getOrderId());
			} else {
				src.put(srcEntity.getOrderId(), srcEntity);
			}
		}
	}

	/**
	 * 保存对账结果
	 * 
	 * @param vfMap
	 * @param platMap
	 */
	private void saveCheckingResult(Map<String, AccountCheckEntity> vfMap,
			Map<String, AccountCheckEntity> platMap, String plat, String date) {
		if (vfMap.size() == 0 && platMap.size() == 0) {
			log.info("{}-{}对账结果:无异常!", date, plat);
			return;
		}
		log.info("{}-{}开始保存对账结果:{}-{}", date, plat, JSONUtil.toJson(vfMap),
				JSONUtil.toJson(platMap));
		List<DictEntity> results = new ArrayList<DictEntity>();
		vfMap.forEach((k, v) -> {
			// 构造数据进行保存
			DictEntity dict = new DictEntity();
			dict.setType(ConfigService.TYPE_CHECKING);
			dict.setId(makeId(plat, date, k));
			dict.setValue(k);
			AccountCheckEntity checkEntity = platMap.get(k);
			if (checkEntity != null) {
				dict.setDescription(FormatUtil.limitStr(checkEntity.toRow()
						.toString(), 100));
				platMap.remove(k);
			} else {
				dict.setDescription("B方不存在对应的记录!");
			}

			results.add(saveDict(dict));
		});

		platMap.forEach((k, v) -> {
			// 构造数据进行保存
			DictEntity dict = makeDictAndSave(makeId(plat, date, k),
					ConfigService.TYPE_CHECKING, k,
					FormatUtil.limitStr(v.toRow().toString(), 100));
			results.add(dict);
		});

		log.info("{}-{}对账结果:{}", date, plat, JSON.toJSON(results));

	}

	private String makeId(String... items) {
		return StringUtils.join(items, ".");
	}

	private BigDecimal ifNull0(BigDecimal amount) {
		return amount == null ? BigDecimal.ZERO : amount;
	}

	private DictEntity makeDictAndSave(String id, String type, String value,
			String desc) {
		DictEntity dictEntity = new DictEntity();
		dictEntity.setType(type);
		dictEntity.setId(id);
		dictEntity.setValue(value);
		dictEntity.setDescription(desc);
		return saveDict(dictEntity);
	}

	// 保存前进行判断是否存在
	private DictEntity saveDict(DictEntity dictEntity) {
		DictEntity exist = dictEntityMapper.selectByPrimaryKey(dictEntity
				.getId());
		if (exist == null) {
			dictEntityMapper.insert(dictEntity);
		}
		return dictEntity;
	}

	// @Scheduled(cron = "0 07 * * * ?")
	public void test() {
		log.info("h5 test schedule ------------------");
		genCheckingFiles();
	}
}
