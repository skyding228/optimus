package com.netfinworks.optimus.admin.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.invest.entity.BidOrderInfoResult;
import com.netfinworks.invest.entity.InvestProfitInfoResult;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.invest.request.BidOrderInfoQueryRequest;
import com.netfinworks.invest.request.InvestProfitInfoQueryRequest;
import com.netfinworks.invest.request.InvestSubjectInfoQueryRequest;
import com.netfinworks.invest.request.ScatteredSubjectModifyRequest;
import com.netfinworks.invest.request.SubjectSaleStatusRequest;
import com.netfinworks.invest.response.BidOrderInfoQueryResponse;
import com.netfinworks.invest.response.InvestProfitInfoQueryResponse;
import com.netfinworks.invest.response.InvestSubjectInfoQueryResponse;
import com.netfinworks.invest.response.SubjectSaleStatusResponse;
import com.netfinworks.optimus.admin.util.ControllerUtil;
import com.netfinworks.optimus.admin.web.CommonConstants;
import com.netfinworks.optimus.admin.web.domain.BidOrderInfoResultVM;
import com.netfinworks.optimus.admin.web.domain.InvestProfitInfoQueryResponseVM;
import com.netfinworks.optimus.admin.web.domain.InvestProfitInfoResultVM;
import com.netfinworks.optimus.admin.web.domain.InvestSubjectInfoResultVM;
import com.netfinworks.optimus.domain.enums.InvestSubjectStatusKind;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.utils.LogUtil;
import com.netfinworks.util.JSONUtil;

/**
 * 产品相关
 * 
 * @author weichunhe create at 2016年3月22日
 */

@RestController
@RequestMapping("/product")
public class ProductController {

	static Logger log = LoggerFactory.getLogger(ProductController.class);

	@Resource(name = "investService")
	private InvestServiceImpl investService;

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;
	// 日志前缀
	private static final String LOG_PREFIX = "定期产品";

	/**
	 * 查询产品列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public Object querySubjects(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize,
			InvestSubjectInfoQueryRequest req, HttpServletRequest request) {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		req.setSubjectType(configService.getSubjectType(member.getChanId()));
		req.setPageSize(pageSize);
		req.setPageNum(pageNum);

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
				result.add(new InvestSubjectInfoResultVM(sub, records));
			}
			resp.setResult(result);
		}
		return resp;
	}

	/**
	 * 查询 投资记录
	 * 
	 * @param plat
	 *            平台编码，如果指定平台编码就是查询对应的备付金账户的投资记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/investRecords", method = RequestMethod.GET)
	public Object queryInvestHistory(
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "0") long startTime,
			@RequestParam(defaultValue = "0") long endTime,
			@RequestParam(defaultValue = "") String plat,
			HttpServletRequest request, BidOrderInfoQueryRequest bidReq) {
		Assert.isTrue(pageSize >= 1 && pageNum >= 1, "需要大于0的分页参数!");

		Assert.notNull(bidReq.getSubjectNo(), "缺少产品编号!");
		bidReq.setPageSize(pageSize);
		bidReq.setPageNum(pageNum);
		// List<String> investStatus = new ArrayList<String>();
		// // 投资成功的状态
		// investStatus.add(InvestOrderStatusEnum.BID_SUCCESS.getCode());
		// investStatus.add(InvestOrderStatusEnum.PAY_SUCCESS.getCode());
		// bidReq.setStatusList(investStatus);
		if (startTime > 0) {
			bidReq.setStartDate(new Date(startTime));
		}
		if (endTime > 0) {
			bidReq.setEndDate(new Date(endTime));
		}
		// 查询平台备付金账户
		if (StringUtils.isNotEmpty(plat)) {
			bidReq.setMemberId(configService.getPlatValue(plat,
					ConfigService.PLAT_PROVISION_MEMBER));
		}

		log.info("{}-查询用户投资记录-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(bidReq));
		BidOrderInfoQueryResponse bidResp = investService
				.queryBidRecord(bidReq);

		log.info("{}-查询用户投资记录-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(bidResp));

		List<BidOrderInfoResult> orders = bidResp.getResult();
		List<BidOrderInfoResult> vms = new ArrayList<BidOrderInfoResult>();

		orders.forEach(x -> {
			MemberEntity member = memberService.getMemberEntity(x.getMemberId());
			vms.add(new BidOrderInfoResultVM(x, member));
		});
		bidResp.setResult(vms);
		log.info("{}-查询用户投资记录-视图模型:{}", LOG_PREFIX, JSONUtil.toJson(vms));
		return bidResp;
	}

	/**
	 * 产品上下架
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/shelves", method = RequestMethod.POST)
	public SubjectSaleStatusResponse shelves(HttpServletRequest request,
			@RequestBody ScatteredSubjectModifyRequest param) {

		Assert.notNull(param.getSubjectNo(), "缺少产品编号!");
		Assert.notNull(param.getSubjectTag(), "缺少上下架参数!");
		Assert.isTrue(
				"1".equals(param.getSubjectTag())
						|| "0".equals(param.getSubjectTag()), "上下架参数有问题,"
						+ param.getSubjectTag());
		SubjectSaleStatusRequest req = new SubjectSaleStatusRequest();
		req.setSubjectNo(param.getSubjectNo());
		req.setOnSale("1".equals(param.getSubjectTag()));

		log.info("{}-上下架产品-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(req));

		SubjectSaleStatusResponse resp = investService.updateSubject(req);

		log.info("{}-上下架产品-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(resp));

		return resp;
	}

	/**
	 * 查询还款记录
	 * 
	 * @param plat
	 *            平台编码，如果指定平台编码就是查询对应的备付金账户的还款记录
	 * @param request
	 * @param pageSize
	 * @param pageNum
	 * @param param
	 * @return
	 */
	@RequestMapping("/repaymentRecords")
	public InvestProfitInfoQueryResponse queryRepaymentRecords(
			HttpServletRequest request,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "") String plat,
			InvestProfitInfoQueryRequest param) {
		Assert.notNull(param.getSubjectNo(), "缺少产品编号!");

		param.setPageNum(pageNum);
		param.setPageSize(pageSize);

		List<String> status = new ArrayList<String>();
		status.add(InvestSubjectStatusKind.FINISH.getCode());
		param.setStatus(status);
		// 查询平台备付金账户
		if (StringUtils.isNotEmpty(plat)) {
			param.setMemberId(configService.getPlatValue(plat,
					ConfigService.PLAT_PROVISION_MEMBER));
		}

		log.info("{}-还款记录-请求参数:{}", LOG_PREFIX, JSONUtil.toJson(param));

		InvestProfitInfoQueryResponse resp = investService
				.queryRepaymentRecords(param);

		log.info("{}-还款记录-响应信息:{}", LOG_PREFIX, JSONUtil.toJson(resp));

		// 转换为视图模型
		List<InvestProfitInfoResult> vms = new ArrayList<InvestProfitInfoResult>();
		List<InvestProfitInfoResult> models = resp.getResult();
		if (models != null) {
			models.forEach(x -> {
				MemberEntity member = memberService.getMemberEntity(x
						.getMemberId());
				vms.add(new InvestProfitInfoResultVM(x, member));
			});
		}
		resp.setResult(vms);
		return new InvestProfitInfoQueryResponseVM(resp);
	}
}
