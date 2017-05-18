package com.netfinworks.optimus.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AccountEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(String value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(String value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(String value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(String value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(String value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLike(String value) {
            addCriterion("account_id like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotLike(String value) {
            addCriterion("account_id not like", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<String> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<String> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(String value1, String value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(String value1, String value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNull() {
            addCriterion("account_name is null");
            return (Criteria) this;
        }

        public Criteria andAccountNameIsNotNull() {
            addCriterion("account_name is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNameEqualTo(String value) {
            addCriterion("account_name =", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotEqualTo(String value) {
            addCriterion("account_name <>", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThan(String value) {
            addCriterion("account_name >", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("account_name >=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThan(String value) {
            addCriterion("account_name <", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLessThanOrEqualTo(String value) {
            addCriterion("account_name <=", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameLike(String value) {
            addCriterion("account_name like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotLike(String value) {
            addCriterion("account_name not like", value, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameIn(List<String> values) {
            addCriterion("account_name in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotIn(List<String> values) {
            addCriterion("account_name not in", values, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameBetween(String value1, String value2) {
            addCriterion("account_name between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountNameNotBetween(String value1, String value2) {
            addCriterion("account_name not between", value1, value2, "accountName");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoIsNull() {
            addCriterion("account_title_no is null");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoIsNotNull() {
            addCriterion("account_title_no is not null");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoEqualTo(String value) {
            addCriterion("account_title_no =", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoNotEqualTo(String value) {
            addCriterion("account_title_no <>", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoGreaterThan(String value) {
            addCriterion("account_title_no >", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoGreaterThanOrEqualTo(String value) {
            addCriterion("account_title_no >=", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoLessThan(String value) {
            addCriterion("account_title_no <", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoLessThanOrEqualTo(String value) {
            addCriterion("account_title_no <=", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoLike(String value) {
            addCriterion("account_title_no like", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoNotLike(String value) {
            addCriterion("account_title_no not like", value, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoIn(List<String> values) {
            addCriterion("account_title_no in", values, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoNotIn(List<String> values) {
            addCriterion("account_title_no not in", values, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoBetween(String value1, String value2) {
            addCriterion("account_title_no between", value1, value2, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andAccountTitleNoNotBetween(String value1, String value2) {
            addCriterion("account_title_no not between", value1, value2, "accountTitleNo");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(BigDecimal value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(BigDecimal value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(BigDecimal value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(BigDecimal value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<BigDecimal> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<BigDecimal> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceIsNull() {
            addCriterion("debitBalance is null");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceIsNotNull() {
            addCriterion("debitBalance is not null");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceEqualTo(BigDecimal value) {
            addCriterion("debitBalance =", value, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceNotEqualTo(BigDecimal value) {
            addCriterion("debitBalance <>", value, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceGreaterThan(BigDecimal value) {
            addCriterion("debitBalance >", value, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("debitBalance >=", value, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceLessThan(BigDecimal value) {
            addCriterion("debitBalance <", value, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("debitBalance <=", value, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceIn(List<BigDecimal> values) {
            addCriterion("debitBalance in", values, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceNotIn(List<BigDecimal> values) {
            addCriterion("debitBalance not in", values, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debitBalance between", value1, value2, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andDebitbalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("debitBalance not between", value1, value2, "debitbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceIsNull() {
            addCriterion("creditBalance is null");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceIsNotNull() {
            addCriterion("creditBalance is not null");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceEqualTo(BigDecimal value) {
            addCriterion("creditBalance =", value, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceNotEqualTo(BigDecimal value) {
            addCriterion("creditBalance <>", value, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceGreaterThan(BigDecimal value) {
            addCriterion("creditBalance >", value, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("creditBalance >=", value, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceLessThan(BigDecimal value) {
            addCriterion("creditBalance <", value, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("creditBalance <=", value, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceIn(List<BigDecimal> values) {
            addCriterion("creditBalance in", values, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceNotIn(List<BigDecimal> values) {
            addCriterion("creditBalance not in", values, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("creditBalance between", value1, value2, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andCreditbalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("creditBalance not between", value1, value2, "creditbalance");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionIsNull() {
            addCriterion("balance_direction is null");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionIsNotNull() {
            addCriterion("balance_direction is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionEqualTo(String value) {
            addCriterion("balance_direction =", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionNotEqualTo(String value) {
            addCriterion("balance_direction <>", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionGreaterThan(String value) {
            addCriterion("balance_direction >", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionGreaterThanOrEqualTo(String value) {
            addCriterion("balance_direction >=", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionLessThan(String value) {
            addCriterion("balance_direction <", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionLessThanOrEqualTo(String value) {
            addCriterion("balance_direction <=", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionLike(String value) {
            addCriterion("balance_direction like", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionNotLike(String value) {
            addCriterion("balance_direction not like", value, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionIn(List<String> values) {
            addCriterion("balance_direction in", values, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionNotIn(List<String> values) {
            addCriterion("balance_direction not in", values, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionBetween(String value1, String value2) {
            addCriterion("balance_direction between", value1, value2, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andBalanceDirectionNotBetween(String value1, String value2) {
            addCriterion("balance_direction not between", value1, value2, "balanceDirection");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceIsNull() {
            addCriterion("frozen_balance is null");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceIsNotNull() {
            addCriterion("frozen_balance is not null");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceEqualTo(BigDecimal value) {
            addCriterion("frozen_balance =", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceNotEqualTo(BigDecimal value) {
            addCriterion("frozen_balance <>", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceGreaterThan(BigDecimal value) {
            addCriterion("frozen_balance >", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("frozen_balance >=", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceLessThan(BigDecimal value) {
            addCriterion("frozen_balance <", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("frozen_balance <=", value, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceIn(List<BigDecimal> values) {
            addCriterion("frozen_balance in", values, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceNotIn(List<BigDecimal> values) {
            addCriterion("frozen_balance not in", values, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frozen_balance between", value1, value2, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andFrozenBalanceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("frozen_balance not between", value1, value2, "frozenBalance");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}