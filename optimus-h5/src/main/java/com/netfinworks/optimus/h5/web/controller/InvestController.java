package com.netfinworks.optimus.h5.web.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.common.util.money.Money;
import com.netfinworks.invest.entity.BidOrderInfoResult;
import com.netfinworks.invest.entity.InvestRepaymentInfoResult;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.invest.request.BidOrderInfoQueryRequest;
import com.netfinworks.invest.request.BidRequest;
import com.netfinworks.invest.request.InvestRepaymentInfoQueryWapRequest;
import com.netfinworks.invest.request.InvestSubjectInfoQueryRequest;
import com.netfinworks.invest.response.BidOrderInfoQueryResponse;
import com.netfinworks.invest.response.BidResponse;
import com.netfinworks.invest.response.InvestRepaymentInfoQueryResponse;
import com.netfinworks.invest.response.InvestSubjectInfoQueryResponse;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.domain.enums.InvestOrderStatusEnum;
import com.netfinworks.optimus.domain.enums.InvestSubjectStatusKind;
import com.netfinworks.optimus.domain.enums.ReturnCode;
import com.netfinworks.optimus.domain.vm.BidOrderInfoResultVM;
import com.netfinworks.optimus.domain.vm.InvestRepaymentInfoResultVM;
import com.netfinworks.optimus.domain.vm.InvestSubjectInfoResultVM;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.h5.web.domain.RestResult;
import com.netfinworks.optimus.h5.web.util.ControllerUtil;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.fund.ApplyService;
import com.netfinworks.optimus.service.fund.RedeemService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.optimus.utils.LogUtil;
import com.netfinworks.util.JSONUtil;

@RestController
@RequestMapping("/invest")
public class InvestController {

	private static Logger log = LoggerFactory.getLogger(InvestController.class);
	// 日志前缀
	private static final String LOG_PREFIX = "定期理财";

	@Resource(name = "investService")
	private InvestServiceImpl investService;

	@Resource
	private ApplyService applyService;
	@Resource
	private RedeemService redeemService;

	@Resource
	private AccountService accountService;

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	/**
	 * 查询投资标记录
	 * 
	 * @return
	 */
	@RequestMapping("/subjects")
	public Object querySubjects(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize,
			HttpServletRequest request) {
		TokenBean bean = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, TokenBean.class);
		InvestSubjectInfoQueryRequest req = new InvestSubjectInfoQueryRequest();
		// 查询散标投资总条目
		List<String> statusList = new ArrayList<String>();
		statusList.add(InvestSubjectStatusKind.BIDDING.getCode()); // 招标中
		statusList.add(InvestSubjectStatusKind.HANDLEBIDFULL.getCode());
		statusList.add(InvestSubjectStatusKind.BIDFULL.getCode());// 已满标
		statusList.add(InvestSubjectStatusKind.REPAYING.getCode());
		statusList.add(InvestSubjectStatusKind.FINISH.getCode());
		req.setStatus(statusList);
		req.setPageSize(pageSize);
		req.setPageNum(pageNum);
		req.setSubjectTag("1");
		req.setSubjectType(configService.getSubjectType(bean.getPlatNo()));

		log.info("{}-查询标的列表-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(req));

		InvestSubjectInfoQueryResponse resp = null;
		try {
			resp = investService.queryInvestSubject(req);
		} catch (Exception e) {
			log.error(LOG_PREFIX + "-查询标的列表-出现异常:", e);
			Assert.isTrue(false, "出现异常了!根据异常ID:" + LogUtil.error(e) + ",联系管理员!");
		}

		log.info("{}-查询标的列表-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(resp));

		return beforeQueryReturn(resp, null);
	}

	/**
	 * 查询标的列表，返回之前进行一些数据处理
	 * 
	 * @param resp
	 */
	private InvestSubjectInfoQueryResponse beforeQueryReturn(
			InvestSubjectInfoQueryResponse resp,
			List<BidOrderInfoResult> records) {
		List<InvestSubjectInfoResult> list = resp.getResult();
		List<InvestSubjectInfoResult> result = new ArrayList<InvestSubjectInfoResult>();
		if (list != null && list.size() > 0) {
			for (InvestSubjectInfoResult sub : list) {
				result.add(new InvestSubjectInfoResultVM(sub, records,
						memberService));
			}
			resp.setResult(result);
		}
		return resp;
	}

	/**
	 * 查询 标的 详情
	 * 
	 * @param subjectNo
	 * @return
	 */
	@RequestMapping(value = "/subject/{subjectNo}", method = RequestMethod.GET)
	public Object querySubjectDetail(@PathVariable String subjectNo) {
		InvestSubjectInfoQueryRequest req = new InvestSubjectInfoQueryRequest();
		req.setSubjectNo(subjectNo);
		// List<String> statusList = new ArrayList<String>();
		// statusList.add(InvestSubjectStatusKind.BIDDING.getCode()); // 招标中
		// req.setStatus(statusList);
		req.setPageSize(1);
		req.setPageNum(1);
		log.info("{}-查询标的详情-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(req));

		InvestSubjectInfoQueryResponse resp = null;
		resp = investService.queryInvestSubject(req);

		log.info("{}-查询标的详情-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(resp));

		Assert.isTrue(resp.getResult().size() > 0, "找不到对应的产品:subjectNo="
				+ subjectNo);

		// 查询投资记录
		BidOrderInfoQueryRequest bidReq = new BidOrderInfoQueryRequest();
		// req.setMemberId(reqParam.getMemberId());
		bidReq.setSubjectNo(subjectNo);
		bidReq.setPageSize(10);
		bidReq.setPageNum(1);
		List<String> investStatus = new ArrayList<String>();
		investStatus.add(InvestOrderStatusEnum.BID_SUCCESS.getCode());
		investStatus.add(InvestOrderStatusEnum.PAY_SUCCESS.getCode());
		bidReq.setStatusList(investStatus);
		// bidReq.setNeedTotalFreezeAmt(reqParam.isNeedTotalFreezeAmt());

		BidOrderInfoQueryResponse bidResp = investService
				.queryBidRecord(bidReq);
		// Assert.isTrue(
		// ReturnCode.SUCCESS.getCode().equals(resp.getReturnCode()),
		// resp.getReturnMessage());

		log.info("{}-查询标的投资记录-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(bidResp));

		resp = beforeQueryReturn(resp, bidResp.getResult());

		return resp.getResult().get(0);
	}

	/**
	 * 查询 投资记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public Object queryInvestHistory(
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum,
			HttpServletRequest request, BidOrderInfoQueryRequest bidReq) {
		Assert.isTrue(pageSize >= pageNum && pageNum >= 1, "需要大于0的分页参数!");

		MemberEntity member = ControllerUtil.getMember(request, memberService);
		// 查询投资记录
		bidReq.setMemberId(member.getMemberId());
		// bidReq.setSubjectNo("20160322134845S00001");
		bidReq.setPageSize(pageSize);
		bidReq.setPageNum(pageNum);
		if (bidReq.getStatusList() == null
				|| bidReq.getStatusList().size() == 0) {

			List<String> investStatus = new ArrayList<String>();
			for (InvestOrderStatusEnum statusEnum : InvestOrderStatusEnum
					.values()) {
				investStatus.add(statusEnum.getCode());
			}
			// 投资成功的状态
			// investStatus.add(InvestOrderStatusEnum.BID_SUCCESS.getCode());
			// investStatus.add(InvestOrderStatusEnum.PAY_SUCCESS.getCode());
			bidReq.setStatusList(investStatus);
		}
		bidReq.setNeedTotalFreezeAmt(true);

		log.info("{}-查询用户投资记录-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(bidReq));
		BidOrderInfoQueryResponse bidResp = investService
				.queryBidRecord(bidReq);

		log.info("{}-查询用户投资记录-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(bidResp));

		List<BidOrderInfoResult> orders = bidResp.getResult();
		List<BidOrderInfoResult> vms = new ArrayList<BidOrderInfoResult>();
		orders.forEach(x -> {
			vms.add(new BidOrderInfoResultVM(x, memberService));
		});
		bidResp.setResult(vms);
		log.info("{}-查询用户投资记录-视图模型:{}", LOG_PREFIX, JSONUtil.toJson(vms));
		return bidResp;
	}

	/**
	 * 进行投资购买
	 * 
	 * @param order
	 *            amount 必须
	 * @return
	 */
	@RequestMapping(value = "/subject/{subjectNo}", method = RequestMethod.POST)
	public Object apply(@PathVariable String subjectNo,
			@RequestBody OrderEntity order, HttpServletRequest request) {
		BigDecimal amount = order.getAmount();
		Assert.notNull(amount, "请输入购买金额!");
		Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "购买金额必须大于0!");

		MemberEntity member = ControllerUtil.getMember(request, memberService);

		AccountEntity account = accountService
				.getAccount(member.getAccountId());

		if (account.getBalance().compareTo(amount) < 0) {
			RestResult result = new RestResult();
			result.setCode(1);
			result.setMsg("余额不足!");
			return result;
		}

		BidRequest req = new BidRequest();
		req.setAmount(new Money(amount.toString()));
		req.setExtension("");
		req.setMemberId(member.getMemberId());
		req.setRemark("购买产品");
		req.setSubjectNo(subjectNo);
		req.setSubmitType("1"); // 手动购买

		log.info("{}-投资购买-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(req));

		BidResponse resp = investService.apply(req);

		log.info("{}-投资购买-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(resp));
		Map<String, Object> result = BeanUtil.pick(account, "balance");
		String session_key = "subject_apply_session_key_";
		Map<String, Object> model = ControllerUtil.resultModel(
				ControllerUtil.getContextPath(request)
						+ ControllerUtil.getStaticPath("/static/me.html"),
				"投资成功!", "本次投资" + amount + "元!", true);
		if (!ReturnCode.SUCCESS.getCode().equals(resp.getReturnCode())) {
			model = ControllerUtil.resultModel(
					ControllerUtil.getContextPath(request)
							+ ControllerUtil.getStaticPath("/static/me.html"),
					"投资失败!", resp.getReturnMessage(), false);
		}
		ControllerUtil.storeToSession(request, session_key, model);
		result.put("url", ControllerUtil.getContextPath(request)
				+ "/action/result?key=" + session_key);

		return result;
	}

	/**
	 * 查询个人投资情况
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/mysubjects")
	public Object queryInvestRepaymentInfo(
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum,
			HttpServletRequest request, InvestRepaymentInfoQueryWapRequest param) {

		MemberEntity member = ControllerUtil.getMember(request, memberService);
		Map<String, Object> resp = accountService.queryInvestOverview(member);
		log.info("{}-个人投资信息:{}", LOG_PREFIX, JSONUtil.toJson(resp));
		return resp;
	}

}
