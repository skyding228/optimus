package com.netfinworks.optimus.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChanViewEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public ChanViewEntityExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andChanIdIsNull() {
            addCriterion("chan_id is null");
            return (Criteria) this;
        }

        public Criteria andChanIdIsNotNull() {
            addCriterion("chan_id is not null");
            return (Criteria) this;
        }

        public Criteria andChanIdEqualTo(String value) {
            addCriterion("chan_id =", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdNotEqualTo(String value) {
            addCriterion("chan_id <>", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdGreaterThan(String value) {
            addCriterion("chan_id >", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdGreaterThanOrEqualTo(String value) {
            addCriterion("chan_id >=", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdLessThan(String value) {
            addCriterion("chan_id <", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdLessThanOrEqualTo(String value) {
            addCriterion("chan_id <=", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdLike(String value) {
            addCriterion("chan_id like", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdNotLike(String value) {
            addCriterion("chan_id not like", value, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdIn(List<String> values) {
            addCriterion("chan_id in", values, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdNotIn(List<String> values) {
            addCriterion("chan_id not in", values, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdBetween(String value1, String value2) {
            addCriterion("chan_id between", value1, value2, "chanId");
            return (Criteria) this;
        }

        public Criteria andChanIdNotBetween(String value1, String value2) {
            addCriterion("chan_id not between", value1, value2, "chanId");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterionForJDBCDate("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterionForJDBCDate("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterionForJDBCDate("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterionForJDBCDate("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andPreviousIsNull() {
            addCriterion("previous is null");
            return (Criteria) this;
        }

        public Criteria andPreviousIsNotNull() {
            addCriterion("previous is not null");
            return (Criteria) this;
        }

        public Criteria andPreviousEqualTo(BigDecimal value) {
            addCriterion("previous =", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousNotEqualTo(BigDecimal value) {
            addCriterion("previous <>", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousGreaterThan(BigDecimal value) {
            addCriterion("previous >", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("previous >=", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousLessThan(BigDecimal value) {
            addCriterion("previous <", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousLessThanOrEqualTo(BigDecimal value) {
            addCriterion("previous <=", value, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousIn(List<BigDecimal> values) {
            addCriterion("previous in", values, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousNotIn(List<BigDecimal> values) {
            addCriterion("previous not in", values, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("previous between", value1, value2, "previous");
            return (Criteria) this;
        }

        public Criteria andPreviousNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("previous not between", value1, value2, "previous");
            return (Criteria) this;
        }

        public Criteria andDepositIsNull() {
            addCriterion("deposit is null");
            return (Criteria) this;
        }

        public Criteria andDepositIsNotNull() {
            addCriterion("deposit is not null");
            return (Criteria) this;
        }

        public Criteria andDepositEqualTo(BigDecimal value) {
            addCriterion("deposit =", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotEqualTo(BigDecimal value) {
            addCriterion("deposit <>", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThan(BigDecimal value) {
            addCriterion("deposit >", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("deposit >=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThan(BigDecimal value) {
            addCriterion("deposit <", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositLessThanOrEqualTo(BigDecimal value) {
            addCriterion("deposit <=", value, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositIn(List<BigDecimal> values) {
            addCriterion("deposit in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotIn(List<BigDecimal> values) {
            addCriterion("deposit not in", values, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deposit between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andDepositNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("deposit not between", value1, value2, "deposit");
            return (Criteria) this;
        }

        public Criteria andWithdrawIsNull() {
            addCriterion("withdraw is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawIsNotNull() {
            addCriterion("withdraw is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawEqualTo(BigDecimal value) {
            addCriterion("withdraw =", value, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawNotEqualTo(BigDecimal value) {
            addCriterion("withdraw <>", value, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawGreaterThan(BigDecimal value) {
            addCriterion("withdraw >", value, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw >=", value, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawLessThan(BigDecimal value) {
            addCriterion("withdraw <", value, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawLessThanOrEqualTo(BigDecimal value) {
            addCriterion("withdraw <=", value, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawIn(List<BigDecimal> values) {
            addCriterion("withdraw in", values, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawNotIn(List<BigDecimal> values) {
            addCriterion("withdraw not in", values, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw between", value1, value2, "withdraw");
            return (Criteria) this;
        }

        public Criteria andWithdrawNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("withdraw not between", value1, value2, "withdraw");
            return (Criteria) this;
        }

        public Criteria andInvestIsNull() {
            addCriterion("invest is null");
            return (Criteria) this;
        }

        public Criteria andInvestIsNotNull() {
            addCriterion("invest is not null");
            return (Criteria) this;
        }

        public Criteria andInvestEqualTo(BigDecimal value) {
            addCriterion("invest =", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestNotEqualTo(BigDecimal value) {
            addCriterion("invest <>", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestGreaterThan(BigDecimal value) {
            addCriterion("invest >", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("invest >=", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestLessThan(BigDecimal value) {
            addCriterion("invest <", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("invest <=", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestIn(List<BigDecimal> values) {
            addCriterion("invest in", values, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestNotIn(List<BigDecimal> values) {
            addCriterion("invest not in", values, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest between", value1, value2, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("invest not between", value1, value2, "invest");
            return (Criteria) this;
        }

        public Criteria andPrincipalIsNull() {
            addCriterion("principal is null");
            return (Criteria) this;
        }

        public Criteria andPrincipalIsNotNull() {
            addCriterion("principal is not null");
            return (Criteria) this;
        }

        public Criteria andPrincipalEqualTo(BigDecimal value) {
            addCriterion("principal =", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotEqualTo(BigDecimal value) {
            addCriterion("principal <>", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThan(BigDecimal value) {
            addCriterion("principal >", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("principal >=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThan(BigDecimal value) {
            addCriterion("principal <", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("principal <=", value, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalIn(List<BigDecimal> values) {
            addCriterion("principal in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotIn(List<BigDecimal> values) {
            addCriterion("principal not in", values, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("principal between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andPrincipalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("principal not between", value1, value2, "principal");
            return (Criteria) this;
        }

        public Criteria andInterestIsNull() {
            addCriterion("interest is null");
            return (Criteria) this;
        }

        public Criteria andInterestIsNotNull() {
            addCriterion("interest is not null");
            return (Criteria) this;
        }

        public Criteria andInterestEqualTo(BigDecimal value) {
            addCriterion("interest =", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotEqualTo(BigDecimal value) {
            addCriterion("interest <>", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThan(BigDecimal value) {
            addCriterion("interest >", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("interest >=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThan(BigDecimal value) {
            addCriterion("interest <", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("interest <=", value, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestIn(List<BigDecimal> values) {
            addCriterion("interest in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotIn(List<BigDecimal> values) {
            addCriterion("interest not in", values, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("interest not between", value1, value2, "interest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestIsNull() {
            addCriterion("provision_invest is null");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestIsNotNull() {
            addCriterion("provision_invest is not null");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestEqualTo(BigDecimal value) {
            addCriterion("provision_invest =", value, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestNotEqualTo(BigDecimal value) {
            addCriterion("provision_invest <>", value, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestGreaterThan(BigDecimal value) {
            addCriterion("provision_invest >", value, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("provision_invest >=", value, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestLessThan(BigDecimal value) {
            addCriterion("provision_invest <", value, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("provision_invest <=", value, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestIn(List<BigDecimal> values) {
            addCriterion("provision_invest in", values, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestNotIn(List<BigDecimal> values) {
            addCriterion("provision_invest not in", values, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("provision_invest between", value1, value2, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionInvestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("provision_invest not between", value1, value2, "provisionInvest");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalIsNull() {
            addCriterion("provision_principal is null");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalIsNotNull() {
            addCriterion("provision_principal is not null");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalEqualTo(BigDecimal value) {
            addCriterion("provision_principal =", value, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalNotEqualTo(BigDecimal value) {
            addCriterion("provision_principal <>", value, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalGreaterThan(BigDecimal value) {
            addCriterion("provision_principal >", value, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("provision_principal >=", value, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalLessThan(BigDecimal value) {
            addCriterion("provision_principal <", value, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("provision_principal <=", value, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalIn(List<BigDecimal> values) {
            addCriterion("provision_principal in", values, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalNotIn(List<BigDecimal> values) {
            addCriterion("provision_principal not in", values, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("provision_principal between", value1, value2, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionPrincipalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("provision_principal not between", value1, value2, "provisionPrincipal");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestIsNull() {
            addCriterion("provision_interest is null");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestIsNotNull() {
            addCriterion("provision_interest is not null");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestEqualTo(BigDecimal value) {
            addCriterion("provision_interest =", value, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestNotEqualTo(BigDecimal value) {
            addCriterion("provision_interest <>", value, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestGreaterThan(BigDecimal value) {
            addCriterion("provision_interest >", value, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("provision_interest >=", value, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestLessThan(BigDecimal value) {
            addCriterion("provision_interest <", value, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("provision_interest <=", value, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestIn(List<BigDecimal> values) {
            addCriterion("provision_interest in", values, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestNotIn(List<BigDecimal> values) {
            addCriterion("provision_interest not in", values, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("provision_interest between", value1, value2, "provisionInterest");
            return (Criteria) this;
        }

        public Criteria andProvisionInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("provision_interest not between", value1, value2, "provisionInterest");
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