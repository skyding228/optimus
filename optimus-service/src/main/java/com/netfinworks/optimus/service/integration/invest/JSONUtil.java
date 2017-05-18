package com.netfinworks.optimus.service.integration.invest;

import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.invest.response.InvestSubjectInfoQueryResponse;
import com.netfinworks.optimus.web.JacksonObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;

import com.meidusa.fastjson.JSONObject;
import com.meidusa.fastmark.feature.Feature;

import java.io.IOException;

public class JSONUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将java对象转换为json字符串
     *
     * @param obj
     * @return 转换后的字符串或者null
     */
    public static String stringify(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stringify(String prefix, Object obj) {
        return prefix + stringify(obj);
    }

    /**
     * 将字符串转换为对象
     *
     * @param text
     * @param responseClass
     * @return
     */
    public static <T> T parse(String text, Class<T> responseClass) {
        return JSONObject.parseObject(text, responseClass,
                Feature.IgnoreNotMatch);
    }


    public static void main(String[] args) {
//        JacksonObjectMapper mapper = new JacksonObjectMapper();
        String bean = "{\"progressRate\":0.82,\"subjectNo\":\"20160518092651S90012\",\"applyNo\":\"20160518092651SA40012\",\"status\":\"05\",\"subjectName\":\"星驿宝1期-流标-系统补投9\",\"description\":\"星驿宝1期-流标-系统补投9\",\"applyAmount\":{\"amount\":400.00,\"currency\":\"CNY\",\"cent\":40000,\"centFactor\":100},\"biddableAmount\":{\"amount\":70.00,\"currency\":\"CNY\",\"cent\":7000,\"centFactor\":100},\"rewardRate\":6.25,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"1月\",\"validDate\":1463538600000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0.00,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100},\"bidMinAmount\":{\"amount\":10.00,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100},\"bidUnit\":{\"amount\":10.00,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100},\"bidBeginTime\":1463452200000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null}";
        InvestSubjectInfoResult result = null;
        try {
            result = mapper.readValue(bean, InvestSubjectInfoResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = "{\"returnCode\":\"S0001\",\"returnMessage\":\"处理成功\",\"itemCount\":32,\"pageCount\":4,\"pageSize\":10,\"pageIndex\":1,\"bonusPayRatio\":\"0.5\",\"result\":[{\"progressRate\":0.82,\"subjectNo\":\"20160518092651S90012\",\"applyNo\":\"20160518092651SA40012\",\"status\":\"05\",\"subjectName\":\"星驿宝1期-流标-系统补投9\",\"description\":\"星驿宝1期-流标-系统补投9\",\"applyAmount\":{\"amount\":400.0,\"currency\":\"CNY\",\"cent\":40000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":70.0,\"currency\":\"CNY\",\"cent\":7000,\"centFactor\":100.0},\"rewardRate\":6.25,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"1月\",\"validDate\":1463538600000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidUnit\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidBeginTime\":1463452200000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":0.5,\"subjectNo\":\"20160516154508S90005\",\"applyNo\":\"20160516154508SA40005\",\"status\":\"05\",\"subjectName\":\"星驿宝1期-流标-人工投资5\",\"description\":\"星驿宝1期-流标-人工投资5\",\"applyAmount\":{\"amount\":20.0,\"currency\":\"CNY\",\"cent\":2000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"rewardRate\":6.33,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"1月\",\"validDate\":1463385000000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidUnit\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidBeginTime\":1463298600000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160517101810S90006\",\"applyNo\":\"20160517101810SA40006\",\"status\":\"10\",\"subjectName\":\"星驿宝1期-流标-系统补投6\",\"description\":\"星驿宝1期-流标-系统补投6\",\"applyAmount\":{\"amount\":600.0,\"currency\":\"CNY\",\"cent\":60000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":3.77,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"1月\",\"validDate\":1463452620000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidUnit\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidBeginTime\":1463366220000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160515153136S70001\",\"applyNo\":\"20160515153136SA20002\",\"status\":\"10\",\"subjectName\":\"20160515\",\"description\":\"\",\"applyAmount\":{\"amount\":1000.0,\"currency\":\"CNY\",\"cent\":100000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":5.23,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"3月\",\"validDate\":1463297496000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":1,\"currency\":\"CNY\",\"cent\":100,\"centFactor\":100.0},\"bidUnit\":{\"amount\":1,\"currency\":\"CNY\",\"cent\":100,\"centFactor\":100.0},\"bidBeginTime\":1463297496000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160512164918S00002\",\"applyNo\":\"20160512164918SA50002\",\"status\":\"10\",\"subjectName\":\"星驿付1号\",\"description\":\"测试补足金额\",\"applyAmount\":{\"amount\":2000.0,\"currency\":\"CNY\",\"cent\":200000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":2.36,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"3月\",\"validDate\":1463302158000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":1,\"currency\":\"CNY\",\"cent\":100,\"centFactor\":100.0},\"bidUnit\":{\"amount\":1,\"currency\":\"CNY\",\"cent\":100,\"centFactor\":100.0},\"bidBeginTime\":1463042958000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160512153708S00001\",\"applyNo\":\"20160512153708SA50001\",\"status\":\"10\",\"subjectName\":\"公司补足测试\",\"description\":\"测试公司补足流程\",\"applyAmount\":{\"amount\":1000000.0,\"currency\":\"CNY\",\"cent\":100000000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":5.23,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"3月\",\"validDate\":1463297829000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":1,\"currency\":\"CNY\",\"cent\":100,\"centFactor\":100.0},\"bidUnit\":{\"amount\":1,\"currency\":\"CNY\",\"cent\":100,\"centFactor\":100.0},\"bidBeginTime\":1463038629000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160428111006S90004\",\"applyNo\":\"20160428111006SA40004\",\"status\":\"10\",\"subjectName\":\"回归测试1号2000\",\"description\":\"回归测试1号2000\",\"applyAmount\":{\"amount\":2000.0,\"currency\":\"CNY\",\"cent\":200000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":6.87,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"1月\",\"validDate\":1461899358000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":100.0,\"currency\":\"CNY\",\"cent\":10000,\"centFactor\":100.0},\"bidUnit\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidBeginTime\":1461812958000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160426154652S90003\",\"applyNo\":\"20160426154652SA40003\",\"status\":\"10\",\"subjectName\":\"星驿宝1期-0426无电子合同\",\"description\":\"星驿宝1期-0426无电子合同\",\"applyAmount\":{\"amount\":90000.0,\"currency\":\"CNY\",\"cent\":9000000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":6.39,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"3月\",\"validDate\":1462520846000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":100.0,\"currency\":\"CNY\",\"cent\":10000,\"centFactor\":100.0},\"bidUnit\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidBeginTime\":1461656846000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160422140620S50010\",\"applyNo\":\"20160422140620SA00010\",\"status\":\"10\",\"subjectName\":\"星驿宝1期-流标\",\"description\":\"“星驿宝1期”产品是星驿付推出的投融资服务产品。本项目为企业贷款产品，该产品募集资金用于日常资金需求。星驿付平台为融资方及出借人提供全程的专业第三方服务。借贷双方通过平台签订电子借贷协议，明确双方的债务与债权关系。该服务仅向符合中华人民共和国有关法律法规及平台规定的合格出借人和融资方提供。\",\"applyAmount\":{\"amount\":3300.0,\"currency\":\"CNY\",\"cent\":330000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":6.53,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"1月\",\"validDate\":1461308400000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":500.0,\"currency\":\"CNY\",\"cent\":50000,\"centFactor\":100.0},\"bidUnit\":{\"amount\":10.0,\"currency\":\"CNY\",\"cent\":1000,\"centFactor\":100.0},\"bidBeginTime\":1461222000000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null},{\"progressRate\":1,\"subjectNo\":\"20160422131512S50008\",\"applyNo\":\"20160422131512SA00008\",\"status\":\"10\",\"subjectName\":\"星驿宝1期-0424\",\"description\":\"“星驿宝1期”产品是星驿付推出的投融资服务产品。本项目为企业贷款产品，该产品募集资金用于日常资金需求。星驿付平台为融资方及出借人提供全程的专业第三方服务。借贷双方通过平台签订电子借贷协议，明确双方的债务与债权关系。该服务仅向符合中华人民共和国有关法律法规及平台规定的合格出借人和融资方提供。\",\"applyAmount\":{\"amount\":6000.0,\"currency\":\"CNY\",\"cent\":600000,\"centFactor\":100.0},\"biddableAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"rewardRate\":6.86,\"applyPurpose\":null,\"repayType\":\"4\",\"loanTerm\":\"3月\",\"validDate\":1461388532000,\"extension\":\"\",\"remark\":null,\"bidAmount\":{\"amount\":0,\"currency\":\"CNY\",\"cent\":0,\"centFactor\":100.0},\"bidMinAmount\":{\"amount\":500.0,\"currency\":\"CNY\",\"cent\":50000,\"centFactor\":100.0},\"bidUnit\":{\"amount\":100.0,\"currency\":\"CNY\",\"cent\":10000,\"centFactor\":100.0},\"bidBeginTime\":1461302132000,\"subjectType\":\"6\",\"subjectTag\":\"1\",\"extendList\":null}]}";
        InvestSubjectInfoQueryResponse response = parse(str, InvestSubjectInfoQueryResponse.class);

        try {
            response = mapper.readValue(str, InvestSubjectInfoQueryResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

}
