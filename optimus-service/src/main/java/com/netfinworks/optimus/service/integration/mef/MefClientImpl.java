package com.netfinworks.optimus.service.integration.mef;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.netfinworks.enums.OrderType;
import com.netfinworks.optimus.ApplyRequest;
import com.netfinworks.optimus.RedeemRequest;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.domain.enums.ApplyOrderStateKind;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.RedeemOrderStateKind;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.fund.FundConstants;
import com.netfinworks.optimus.service.integration.ErrorKind;
import com.netfinworks.optimus.service.integration.ServiceException;
import com.netfinworks.optimus.service.integration.common.IntegrationProxy;
import com.netfinworks.optimus.service.repository.AccountRepository;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.optimus.utils.RestUtil;
import com.netfinworks.util.JSONUtil;
import com.vf.mef.jaxws.WsFaceService;
import com.vf.mef.vo.InvestRedeemRequest;
import com.vf.mef.vo.InvestRedeemResponse;

/**
 * <p>
 * mef调用实现
 * </p>
 *
 * @author
 * @version
 */
@Service("mefClient")
public class MefClientImpl implements MefClient {

	private static final Logger log = LoggerFactory
			.getLogger(MefClientImpl.class);
	private static final String LogPrefix = "基金相关";

	@Resource(name = "mefFacadeDataSignUtils")
	private MefFacadeDataSignUtils fsnFacadeDataSignUtils;

	@Resource(name = "mefInvestRedeemProxy")
	private IntegrationProxy<WsFaceService> wsInvestRedeemService;

	@Resource(name = "mefRedeemAllProxy")
	private IntegrationProxy<WsFaceService> wsRedeemAllService;

	@Resource
	private ConfigService configService;

	@Resource
	private AccountRepository accountRepository;

	@Resource
	private OrderRepository orderRepository;

	private RestTemplate restTmpl = new RestTemplate();

	/**
	 * 申购/赎回
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public InvestRedeemResponse investRedeemTrade(InvestRedeemRequest request) {
		Assert.isTrue(fundIsOn(request.getChannelNo()), "当前基金不可购买!");
		OrderEntity order = null;
		if (OrderType.INVEST.name().equals(request.getTradeType())) {
			ApplyRequest applyReq = new ApplyRequest();
			applyReq.setAmount(new BigDecimal(request.getAmount()));
			applyReq.setChanId(request.getChannelNo());
			applyReq.setChanUserId(request.getMemberId());
			applyReq.setMemberId(request.getMemberId());
			applyReq.setProductId(request.getProductNo());
			order = orderRepository.createApplyOrder(applyReq);
			order.setMemo(ApplyOrderStateKind.APPLY_SUCCESS.getMsg());
		} else if (OrderType.REDEEM.name().equals(request.getTradeType())) {
			RedeemRequest redeemReq = new RedeemRequest();
			redeemReq.setAmount(new BigDecimal(request.getAmount()));
			redeemReq.setChanId(request.getChannelNo());
			redeemReq.setChanUserId(request.getMemberId());
			redeemReq.setMemberId(request.getMemberId());
			redeemReq.setProductId(request.getProductNo());
			order = orderRepository.createRedeemOrder(redeemReq);
			order.setMemo(RedeemOrderStateKind.REDEEM_SUCCESS.getMsg());
		}
		InvestRedeemResponse response = new InvestRedeemResponse();
		
		log.info("[EFS->FSN]申购/赎回请求：{}", request);
		// 组装请求
		WsFaceService wsFaceService = wsInvestRedeemService.buildInst();
		try {
			response = fsnFacadeDataSignUtils.send(wsFaceService, request,
					InvestRedeemResponse.class);
			order.setOuterOrderId(response.getTradeNo());
			order.setOrderStatus(OrderStatus.SUCCESS.getValue());
		} catch (Throwable t) {
			order.setOrderStatus(OrderStatus.FAIL.getValue());
			order.setMemo(t.getMessage());
			if (!StringUtils.isEmpty(order.getMemo())) {
				order.setMemo(order.getMemo().substring(0,
						Math.min(order.getMemo().length(), 200)));
			}
			log.error("FsnClient.investRedeemTrade调用异常：{}", request, t);
			throw new ServiceException(ErrorKind.INTEGRATION_ERROR,
					request.toString(), t);
		} finally {
			accountRepository.updateAccountBalance(order);
			log.info("[FSN->EFS]申购/赎回应答：{}", response);
		}
		return response;
	}

	/**
	 * 全部赎回
	 * 
	 * @param request
	 * @return
	 */
	// @Override
	// public RedeemAllResponse redeemAllTrade(RedeemAllRequest request) {
	// Assert.isTrue(fundIsOn(request.getChannelNo()), "当前基金不可购买!");
	// RedeemAllResponse response = new RedeemAllResponse();
	// log.info("[EFS->FSN]全部赎回请求：{}", request);
	// // 组装请求
	// WsFaceService wsFaceService = wsRedeemAllService.buildInst();
	// try {
	// response = fsnFacadeDataSignUtils.send(wsFaceService, request,
	// RedeemAllResponse.class);
	// } catch (Throwable t) {
	// log.error("FsnClient.redeemAllTrade调用异常：{}", request, t);
	// throw new ServiceException(ErrorKind.INTEGRATION_ERROR,
	// request.toString(), t);
	// } finally {
	// log.info("[FSN->EFS]全部赎回应答：{}", response);
	// }
	// return response;
	// }

	/**
	 * 查询申购赎回订单
	 * 
	 * @param plat
	 *            渠道编码
	 * @param accountId
	 *            申购商户号
	 * @param orderType
	 *            订单类型
	 * @param orderStatus
	 *            订单状态
	 * @param startTime
	 *            开始时间 long类型
	 * @param endTime
	 *            结束时间
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public RestResult tradeOrders(String plat, String accountId,
			String orderType, String orderStatus, long startTime, long endTime,
			int pageNum, int pageSize) {
		if (!fundIsOn(plat)) {
			return new RestResult(pageNum);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageSize", pageSize);
		param.put("pageNum", pageNum);
		if (startTime > 0) {
			param.put("start", startTime);
		}
		if (endTime > 0) {
			param.put("end", endTime);
		}
		// 只有申购和赎回
		if ("INVEST".equals(orderType) || "REDEEM".equals(orderType)) {
			param.put("orderType", orderType);
		}
		if (!StringUtils.isEmpty(accountId)) {
			param.put("accountId", accountId);
		}
		// 订单状态
		/**
		 * TO_EXECUTE("待执行"), EXECUTED_SUCCESS("执行成功"), EXECUTED_FAIL("执行失败"),
		 */
		if (!StringUtils.isEmpty(orderStatus)) {
			param.put("orderStatus", orderStatus);
		}

		String url = configService.getPlatValue(plat,
				FundConstants.FUND_API_URL) + "/orders";

		log.info("{}-查询基金订单-请求参数:{}", LogPrefix, JSONUtil.toJson(param));

		RestResult result = restTmpl.getForObject(
				url + RestUtil.buildGetParamUrl(param, true), RestResult.class,
				param);
		log.info("{}-查询基金订单-响应信息:{}", LogPrefix, JSONUtil.toJson(result));
		return result;
	}

	/**
	 * 查询基金收益
	 * 
	 * @param plat
	 * @param accountId
	 * @param dividStatus
	 * @param date
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public RestResult profit(String plat, String accountId, String dividStatus,
			long date, int pageNum, int pageSize) {
		if (!fundIsOn(plat)) {
			return new RestResult(pageNum);
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageSize", pageSize);
		param.put("pageNum", pageNum);

		if (date > 0) {
			param.put("dateT", FormatUtil.dateFormat.format(new Date(date)));
		}
		/**
		 * INIT("初始化"),//登帐 IN_PROCESS("处理中"), WAIT_DIVI("待分发"),
		 * //登帐审核通过，或者通过接口拿到总收益的初始状态 DIVIDED("已分发"), //
		 * 收益已经分配给商户,收益记录明细上传到SFTP成功 SUCCESS("发放成功"), // 已经和钱包端同步确认成功
		 * FAILURE("发放失败"); // 还没有和钱包短同步确认
		 */
		if (!StringUtils.isEmpty(dividStatus)) {
			param.put("dividStatus", dividStatus);
		}
		if (!StringUtils.isEmpty(accountId)) {
			param.put("accountId", accountId);
		}

		String url = configService.getPlatValue(plat,
				FundConstants.FUND_API_URL) + "/profit/detail";

		log.info("{}-查询收益-请求参数:{}", LogPrefix, JSONUtil.toJson(param));
		RestResult result = restTmpl.getForObject(
				url + RestUtil.buildGetParamUrl(param, true), RestResult.class,
				param);
		log.info("{}-查询收益-响应信息:{}", LogPrefix, JSONUtil.toJson(result));

		return result;
	}

	/**
	 * 判断 是否部署了基金系统
	 * 
	 * @param plat
	 * @return
	 */
	private boolean fundIsOn(String plat) {
		String url = configService.getPlatValue(plat,
				FundConstants.FUND_API_URL);
		return !StringUtils.isEmpty(url);
	}

	public static void main(String[] args) {
		System.out.println(OrderType.INVEST.toString());
		System.out.println("abc".substring(0, Math.min(3, 10)));
	}
}
