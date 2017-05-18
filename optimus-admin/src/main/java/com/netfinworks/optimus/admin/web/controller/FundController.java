package com.netfinworks.optimus.admin.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.enums.OrderStatus;
import com.netfinworks.enums.OrderType;
import com.netfinworks.optimus.admin.util.ControllerUtil;
import com.netfinworks.optimus.admin.web.CommonConstants;
import com.netfinworks.optimus.domain.FundInfo;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.service.integration.mef.MefClientImpl;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;
import com.vf.mef.consts.ProfitDividStatus;

@RestController
@RequestMapping("/fund")
public class FundController {
    private static Logger log = LoggerFactory.getLogger(FundController.class);

    static final String LogPrefix = "货币基金";

    @Resource
    private ConfigService configService;

    @Resource(name = "investService")
    private InvestServiceImpl investService;

    @Resource(name = "mefClient")
    private MefClientImpl mefclient;

    /**
     * 查询基金的申购赎回记录
     *
     * @param request
     * @param pageSize
     * @param pageNum
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/orders")
    public Object fundRecords(HttpServletRequest request,
                              @RequestParam(defaultValue = "10") int pageSize,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "0") long startTime,
                              @RequestParam(defaultValue = "0") long endTime, OrderEntity order) {
        MemberEntity member = ControllerUtil.retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);

        RestResult result = mefclient.tradeOrders(member.getChanId(),
                order.getMemberId(), order.getOrderType(),
                order.getOrderStatus(), startTime, endTime, pageNum, pageSize);

        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        // 构造显示数据
        Object obj = result.getResult();
        if (obj != null) {
            List objs = (List) obj;
            objs.forEach(x -> {
                Map<String, Object> map = JSONUtil.fromJson(JSONUtil.toJson(x),
                        HashMap.class);
                try {
                    map.put("amountV", FormatUtil.formatRate(MapUtils
                            .getDouble(map, "amount")));
                    OrderType orderType = OrderType.valueOf(MapUtils.getString(
                            map, "orderType"));
                    map.put("orderTypeV", orderType.getDescription());

                    OrderStatus orderStatus = OrderStatus.valueOf(MapUtils
                            .getString(map, "orderStatus"));
                    map.put("orderStatusV", orderStatus.getDescription());
                    // 取商户编号
                    List parties = (List) map.get("orderParties");
                    parties.forEach(y -> {
                        Map party = (Map) y;
                        if ("PAYER".equals(MapUtils.getString(party,
                                "orderRole"))) {
                            Map account = (Map) party.get("account");
                            map.put("memberId",
                                    MapUtils.getString(account, "id"));
                        }
                    });
                } catch (Exception e) {
                    log.error("{}-构造订单视图模型时出错:{}", LogPrefix,
                            JSONUtil.toJson(map), e);
                }

                rows.add(map);
            });

        }

        result.setResult(rows);
        return result;
    }

    /**
     * 查询基金的申购赎回记录
     *
     * @param request
     * @param pageSize
     * @param pageNum
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/profit")
    public Object fundProfit(HttpServletRequest request,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "0") long time, OrderEntity order) {
        MemberEntity member = ControllerUtil.retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);

        RestResult result = mefclient.profit(member.getChanId(),
                order.getMemberId(), order.getOrderStatus(), time, pageNum,
                pageSize);

        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        // 构造显示数据
        Object obj = result.getResult();
        if (obj != null) {
            List objs = (List) obj;
            objs.forEach(x -> {
                Map<String, Object> map = JSONUtil.fromJson(JSONUtil.toJson(x),
                        HashMap.class);
                try {
                    map.put("amountV", FormatUtil.formatRate(MapUtils
                            .getDouble(map, "amount")));
                    map.put("profitV", FormatUtil.formatRate(MapUtils
                            .getDouble(map, "profitYesterday")));

                    ProfitDividStatus orderStatus = ProfitDividStatus
                            .valueOf(MapUtils.getString(map, "dividStatus"));
                    map.put("orderStatusV", orderStatus.getDescription());
                    // 取商户编号
                    map.put("memberId", MapUtils.getString(map, "accountId"));
                } catch (Exception e) {
                    log.error("{}-构造收益视图模型时出错:{}", LogPrefix,
                            JSONUtil.toJson(map), e);
                }

                rows.add(map);
            });

        }

        result.setResult(rows);
        return result;
    }

    @RequestMapping("/info")
    public Object fundInfo(HttpServletRequest request) {
        MemberEntity member = ControllerUtil.retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
        FundInfo info = FundInfo.newInstance(configService, member.getChanId());
        log.info("{}-查询基金数据:{}", LogPrefix, JSONUtil.toJson(info));
        return info;
    }

    /**
     * 保存基金信息
     *
     * @param fund
     * @return
     */
    @RequestMapping("/saveInfo")
    public Object saveInfo(@RequestBody FundInfo fund,
                           HttpServletRequest request) {
        MemberEntity member = ControllerUtil.retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
        configService.save(fund.toDicts(member.getChanId()));
        log.info("{}-更新基金信息-请求参数:{}", LogPrefix, JSONUtil.toJson(fund));
        fund = FundInfo.newInstance(configService, member.getChanId());
        log.info("{}-更新基金信息-响应信息:{}", LogPrefix, JSONUtil.toJson(fund));
        return fund;
    }

//	public static void main(String[] args) {
//		System.out.println(OrderType.valueOf("123"));
//	}
}
