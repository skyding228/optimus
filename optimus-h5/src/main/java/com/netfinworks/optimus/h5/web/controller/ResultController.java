package com.netfinworks.optimus.h5.web.controller;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netfinworks.invest.entity.InvestRepaymentInfoResult;
import com.netfinworks.invest.entity.InvestSubjectExtendEntity;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.h5.web.domain.InvestContract;
import com.netfinworks.optimus.h5.web.util.ControllerUtil;
import com.netfinworks.optimus.service.impl.AccountServiceImpl;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;

/**
 * 操作结果
 *
 * @author weichunhe
 */
@Controller
@RequestMapping("/action")
public class ResultController {

    private static Logger log = LoggerFactory.getLogger(ResultController.class);
    @Resource(name = "investService")
    private InvestServiceImpl investService;

    @SuppressWarnings("unchecked")
    @RequestMapping("/result")
    public ModelAndView result(HttpServletRequest request,
                               @RequestParam String key) {
        Object data = request.getSession().getAttribute(key);
        Map<String, Object> model = null;
        // 没有找到想要看的结果
        if (data == null) {
            model = ControllerUtil.resultModel(
                    ControllerUtil.getContextPath(request)
                            + ControllerUtil
                            .getStaticPath("/static/index.html"),
                    "错误!", "找不到想要查看的结果!", false);
        } else {
            model = (Map<String, Object>) data;
        }
        request.setAttribute("result", model.get("success"));
        return new ModelAndView("result", model);
    }

    /**
     * 合同信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/contract/{subjectNo}", method = RequestMethod.GET)
    public ModelAndView contract(HttpServletRequest request,
                                 @PathVariable String subjectNo) {
        InvestContract contract = new InvestContract();
        TokenBean bean = ControllerUtil.retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, TokenBean.class);
        // 借出人
        MemberEntity member = bean.getMember();
        contract.setMember(member);

        // 产品信息
        InvestSubjectInfoResult subject = investService
                .querySubjectExtends(subjectNo);
        Assert.notNull(subject, "未找到对应的产品,编号=" + subjectNo);
        contract.setSubject(subject);
        contract.setRewardRateV(FormatUtil.formatRate(subject.getRewardRate()));

        List<InvestSubjectExtendEntity> exts = subject.getExtendList();
        if (exts != null) {
            exts.forEach(x -> {
                contract.getExts().put(x.getName(), x.getValue());
            });
        }

        // 订单信息
        List<InvestRepaymentInfoResult> list = investService
                .querySubjectConfirm(member.getMemberId(), subjectNo);
        Assert.isTrue(list != null && list.size() > 0,
                "未找到购买信息:会员编号=" + member.getMemberId() + ",产品编号=" + subjectNo);
        Assert.isTrue(list.size() == 1, "找到多条购买信息:会员编号=" + member.getMemberId()
                + ",产品编号=" + subjectNo + "," + list.size());

        InvestRepaymentInfoResult repaymentInfo = list.get(0);
        contract.setAmountV(repaymentInfo.getBidAmount().format());

        // 查询预计还款时间
        Date date = investService.querySubjectPredictRepayDate(subjectNo);
        Assert.notNull(date, "查询不到产品到期时间");
        contract.setEndDateV(FormatUtil.formatDate(date));

        Long day = AccountServiceImpl.LoanTermDay.get(subject.getLoanTerm());
        contract.setLoanDays(day + "");

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, (int) (0 - day));

        contract.setStartDateV(FormatUtil.formatDate(calendar.getTime()));
        log.info("查询产品合同信息:{}-{}", subjectNo, JSONUtil.toJson(contract));
        return new ModelAndView("/contract", "data", contract);
    }

    @RequestMapping("/demo")
    public String demo() {
        return "/demo";
    }
}
