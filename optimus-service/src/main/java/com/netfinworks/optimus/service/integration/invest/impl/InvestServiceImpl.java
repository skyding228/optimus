package com.netfinworks.optimus.service.integration.invest.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.invest.entity.InvestInstallment;
import com.netfinworks.invest.entity.InvestRepaymentInfoResult;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.invest.facade.InvestFacade;
import com.netfinworks.invest.facade.InvestManagementFacade;
import com.netfinworks.invest.facade.InvestQueryFacade;
import com.netfinworks.invest.request.BidOrderInfoQueryRequest;
import com.netfinworks.invest.request.BidRequest;
import com.netfinworks.invest.request.InvestProfitInfoQueryRequest;
import com.netfinworks.invest.request.InvestRepaymentInfoQueryWapRequest;
import com.netfinworks.invest.request.InvestScheduleInfoQueryRequest;
import com.netfinworks.invest.request.InvestSubjectInfoQueryRequest;
import com.netfinworks.invest.request.SubjectSaleStatusRequest;
import com.netfinworks.invest.response.BidOrderInfoQueryResponse;
import com.netfinworks.invest.response.BidResponse;
import com.netfinworks.invest.response.InvestPreProfitQueryResponse;
import com.netfinworks.invest.response.InvestProfitInfoQueryResponse;
import com.netfinworks.invest.response.InvestRepaymentInfoQueryResponse;
import com.netfinworks.invest.response.InvestScheduleInfoQueryResponse;
import com.netfinworks.invest.response.InvestSubjectDetailQueryResponse;
import com.netfinworks.invest.response.InvestSubjectInfoQueryResponse;
import com.netfinworks.invest.response.ReceivedProfitQueryResponse;
import com.netfinworks.invest.response.SubjectSaleStatusResponse;
import com.netfinworks.optimus.domain.enums.InvestOrderStatusEnum;
import com.netfinworks.optimus.domain.enums.InvestSubjectStatusKind;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.domain.enums.ReturnCode;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.OrderEntityExample;
import com.netfinworks.optimus.mapper.OrderEntityMapper;
import com.netfinworks.optimus.service.integration.invest.EnvUtil;
import com.netfinworks.optimus.service.integration.invest.InvestService;
import com.netfinworks.util.JSONUtil;

/**
 * <p>
 * 投资服务
 * </p>
 *
 * @author wangtq
 * @version $Id: InvestService.java, v 0.1 2015-1-15 下午1:33:13 wangtq Exp $
 */

@Service("investService")
public class InvestServiceImpl implements InvestService {
    private Logger log = LoggerFactory.getLogger(InvestServiceImpl.class);
    @Autowired
    private InvestFacade investFacade;

    @Autowired
    private InvestQueryFacade investQueryFacade;

    @Autowired
    private InvestManagementFacade investManagementFacade;

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    public InvestSubjectInfoQueryResponse queryInvestSubject(
            InvestSubjectInfoQueryRequest req) {

        OperationEnvironment oe = EnvUtil.buildEnv();

        long beginTime = System.currentTimeMillis();

        log.info("查询投资标总条目,请求参数:{}", JSONUtil.toJson(req));

        InvestSubjectInfoQueryResponse resp = investQueryFacade
                .queryInvestSubjectInfo(req, oe);
        long consumeTime = System.currentTimeMillis() - beginTime;
        log.info("查询投资标总条目， 耗时:{} (ms); 响应结果:{} ", consumeTime,
                JSONUtil.toJson(resp));

        Assert.isTrue(
                ReturnCode.SUCCESS.getCode().equals(resp.getReturnCode()),
                resp.getReturnMessage());
        return resp;
    }

    /**
     * 查询 标的
     *
     * @param subjectNo
     * @return
     */
    public InvestSubjectInfoResult querySubject(String subjectNo) {
        InvestSubjectInfoQueryRequest req = new InvestSubjectInfoQueryRequest();
        req.setPageNum(1);
        req.setPageSize(1);
        req.setSubjectNo(subjectNo);

        InvestSubjectInfoQueryResponse resp = investQueryFacade
                .queryInvestSubjectInfo(req, EnvUtil.buildEnv());
        if (resp.getResult() != null && resp.getResult().size() > 0) {
            return resp.getResult().get(0);
        }
        return null;
    }

    /**
     * 查询 标的 包含扩展信息
     *
     * @param subjectNo
     * @return
     */
    public InvestSubjectInfoResult querySubjectExtends(String subjectNo) {

        InvestSubjectDetailQueryResponse resp = investQueryFacade
                .findBySubjectNo(subjectNo);

        return resp.getResult();
    }

    /**
     * 查询用户收到产品还款的时间
     *
     * @param memberId
     * @param subjectNo
     * @return
     */
    public Date querySubjectRecoveryDate(String subjectNo, String memberId) {
        OrderEntityExample example = new OrderEntityExample();
        example.createCriteria().andProductIdEqualTo(subjectNo)
                .andOrderTypeEqualTo(OrderType.RECOVERY.getValue())
                .andMemberIdEqualTo(memberId);
        List<OrderEntity> orders = orderEntityMapper.selectByExample(example);
        if (orders != null && orders.size() > 0) {
            return orders.get(0).getOrderTime();
        }
        return null;
    }

    /**
     * 查询标的投资记录
     *
     * @param reqParam
     */
    public BidOrderInfoQueryResponse queryBidRecord(BidOrderInfoQueryRequest req) {

        long beginTime = System.currentTimeMillis();

        log.info("查询投资记录,请求参数:{}", JSONUtil.toJson(req));
        BidOrderInfoQueryResponse resp = investQueryFacade.queryBidOrderInfo(
                req, EnvUtil.buildEnv());
        long consumeTime = System.currentTimeMillis() - beginTime;
        log.info("查询投资记录， 耗时:{} (ms); 响应结果:{} ", consumeTime,
                JSONUtil.toJson(resp));

        return resp;
    }

    /**
     * 查询个人投资记录
     *
     * @param req 参数内容与标的投资记录相同，所以复用的查询投资标的记录的请求参数类型
     * @return
     */
    public Object queryInvestHistory(BidOrderInfoQueryRequest req) {

        return null;
    }

    /**
     * 进行投资
     *
     * @param request
     * @return
     */
    public BidResponse apply(BidRequest req) {

        long beginTime = System.currentTimeMillis();

        log.info("投资,请求参数:{}", JSONUtil.toJson(req));

        BidResponse resp = investFacade.apply(req, EnvUtil.buildEnv());

        long consumeTime = System.currentTimeMillis() - beginTime;
        log.info("投资， 耗时:{} (ms); 响应结果:{} ", consumeTime, JSONUtil.toJson(resp));

        return resp;
    }

    /**
     * 更新标的信息
     *
     * @param request
     * @return
     */
    public SubjectSaleStatusResponse updateSubject(
            SubjectSaleStatusRequest request) {

        SubjectSaleStatusResponse response = investManagementFacade
                .modifySubjectSaleStatus(request, EnvUtil.buildEnv());

        return response;
    }

    /**
     * 查询还款记录
     *
     * @param request
     * @return
     */
    public InvestProfitInfoQueryResponse queryRepaymentRecords(
            InvestProfitInfoQueryRequest request) {
        InvestProfitInfoQueryResponse response = investQueryFacade
                .queryInvestProfitInfo(request, EnvUtil.buildEnv());

        return response;
    }

    /**
     * 查询 个人投资情况,产品列表
     *
     * @param request
     * @return
     */
    public InvestRepaymentInfoQueryResponse queryInvestRepaymentInfo(
            InvestRepaymentInfoQueryWapRequest request) {
        InvestRepaymentInfoQueryResponse response = investQueryFacade
                .queryInvestRepaymentForWap(request, EnvUtil.buildEnv());
        return response;
    }

    /**
     * 查询预计收益
     *
     * @param memberId
     * @return
     */
    public InvestPreProfitQueryResponse queryPreProfit(String memberId) {
        InvestPreProfitQueryResponse resp = investQueryFacade.queryPreProfit(
                memberId, EnvUtil.buildEnv());

        return resp;
    }

    /**
     * 累计总利息收益=已收利息之和 文案提示：指已经收到的利息的总和
     *
     * @param memberId
     */
    public ReceivedProfitQueryResponse queryReceivedProfit(String memberId) {

        ReceivedProfitQueryResponse response = investQueryFacade
                .queryReceivedProfit(memberId, EnvUtil.buildEnv());
        return response;
    }

    /**
     * 查询定期产品的未确认的投资
     *
     * @param memberId
     * @return
     */
    public BidOrderInfoQueryResponse querySubjectNotConfirm(String memberId) {
        BidOrderInfoQueryRequest bidReq = new BidOrderInfoQueryRequest();
        bidReq.setMemberId(memberId);
        bidReq.setPageSize(1000);
        bidReq.setPageNum(1);

        List<String> investStatus = new ArrayList<String>();

        // 支付成功的状态
        investStatus.add(InvestOrderStatusEnum.PAY_SUCCESS.getCode());
        bidReq.setStatusList(investStatus);
        bidReq.setNeedTotalFreezeAmt(true);

        log.info("查询未确认定期投资-请求参数:{}", JSONUtil.toJson(bidReq));
        BidOrderInfoQueryResponse bidResp = queryBidRecord(bidReq);
        log.info("查询未确认定期投资-响应信息:{}", JSONUtil.toJson(bidResp));
        return bidResp; // bidResp.getTotalFreezeAmt().getAmount();
    }

    /**
     * 查询定期产品已确认未还款的投资
     *
     * @param memberId
     * @return
     */
    public List<InvestRepaymentInfoResult> querySubjectConfirm(String memberId,
                                                               String subjectNo) {
        InvestRepaymentInfoQueryWapRequest param = new InvestRepaymentInfoQueryWapRequest();
        param.setMemberId(memberId);
        param.setPageNum(1);
        param.setPageSize(1000);

        List<String> status = new ArrayList<String>();
        status.add(InvestSubjectStatusKind.BIDDING.getCode());
        status.add(InvestSubjectStatusKind.BIDFULL.getCode());
        status.add(InvestSubjectStatusKind.REPAYING.getCode());
        param.setStatus(status);

        if (!StringUtil.isEmpty(subjectNo)) {
            param.setSubjectNo(subjectNo);
        }

        log.info("查询已确认定期投资-请求参数:{}", JSONUtil.toJson(param));
        InvestRepaymentInfoQueryResponse resp = queryInvestRepaymentInfo(param);
        log.info("查询已确认定期投资-响应信息:{}", JSONUtil.toJson(resp));

        return resp.getResult();
    }

    /**
     * 查询定期产品已完结的投资
     *
     * @param memberId
     * @return
     */
    public InvestRepaymentInfoQueryResponse querySubjectFinish(String memberId) {
        InvestRepaymentInfoQueryWapRequest param = new InvestRepaymentInfoQueryWapRequest();
        param.setMemberId(memberId);
        param.setPageNum(1);
        param.setPageSize(1000);

        List<String> status = new ArrayList<String>();
        status.add(InvestSubjectStatusKind.FINISH.getCode());
        param.setStatus(status);

        log.info("定期投资-已完结-请求参数:{}", JSONUtil.toJson(param));
        InvestRepaymentInfoQueryResponse resp = queryInvestRepaymentInfo(param);
        log.info("定期投资-已完结-响应信息:{}", JSONUtil.toJson(resp));

        return resp;
    }

    /**
     * 查询预计还款时间
     *
     * @param subjectNo
     */
    public Date querySubjectPredictRepayDate(String subjectNo) {
        InvestScheduleInfoQueryRequest request = new InvestScheduleInfoQueryRequest();
        request.setSubjectNo(subjectNo);
        InvestScheduleInfoQueryResponse response = investQueryFacade
                .queryInvestScheduleInfo(request, EnvUtil.buildEnv());
        log.info("查询{}预计还款日:{}", subjectNo, JSONUtil.toJson(response));
        if (!ReturnCode.SUCCESS.getCode().equals(response.getReturnCode())) {
            return null;
        }
        List<InvestInstallment> list = response.getInstallmentList();
        if (list != null && list.size() > 0) {
            return list.get(0).getPredictRepayDate();
        }
        return null;
    }

    /**
     * 查询已过期产品
     *
     * @return
     */
    public List<InvestSubjectInfoResult> queryOverdueSubject() {
        List<InvestSubjectInfoResult> subjects = new ArrayList<InvestSubjectInfoResult>();
        InvestSubjectInfoQueryResponse response = investQueryFacade
                .queryExpiredInvestSubjectInfo();
        log.info("查询到已过期标的:{}", JSONUtil.toJson(response));
        if (response.getResult() != null) {
            subjects = response.getResult();
        }
        return subjects;
    }
}
