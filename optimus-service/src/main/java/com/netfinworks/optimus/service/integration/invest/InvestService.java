package com.netfinworks.optimus.service.integration.invest;

import com.netfinworks.invest.request.BidOrderInfoQueryRequest;
import com.netfinworks.invest.request.BidRequest;
import com.netfinworks.invest.request.InvestProfitInfoQueryRequest;
import com.netfinworks.invest.request.InvestSubjectInfoQueryRequest;
import com.netfinworks.invest.request.ScatteredSubjectModifyRequest;
import com.netfinworks.invest.request.SubjectSaleStatusRequest;
import com.netfinworks.invest.response.BidOrderInfoQueryResponse;
import com.netfinworks.invest.response.BidResponse;
import com.netfinworks.invest.response.InvestProfitInfoQueryResponse;
import com.netfinworks.invest.response.InvestSubjectInfoQueryResponse;
import com.netfinworks.invest.response.ScatteredSubjectModifyResponse;
import com.netfinworks.invest.response.SubjectSaleStatusResponse;

/**
 * 定期投资
 * 
 * @author weichunhe
 *
 */
public interface InvestService {

	/**
	 * 投资标的查询
	 * 
	 * @param req
	 * @return
	 */
	public InvestSubjectInfoQueryResponse queryInvestSubject(
			InvestSubjectInfoQueryRequest req);

	/**
	 * 查询投资记录
	 * 
	 * @param req
	 * @return
	 */
	public BidOrderInfoQueryResponse queryBidRecord(BidOrderInfoQueryRequest req);

	/**
	 * 进行投资
	 * 
	 * @param req
	 * @return
	 */
	public BidResponse apply(BidRequest req);

	/**
	 * 更新标的信息 上下架等
	 * 
	 * @param request
	 * @return
	 */
	public SubjectSaleStatusResponse updateSubject(
			SubjectSaleStatusRequest request);

	/**
	 * 查询还款记录
	 * 
	 * @param request
	 * @return
	 */
	public InvestProfitInfoQueryResponse queryRepaymentRecords(
			InvestProfitInfoQueryRequest request);
}
