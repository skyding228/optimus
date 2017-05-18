package com.netfinworks.optimus.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public MemberEntityExample() {
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

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(String value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(String value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(String value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(String value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(String value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLike(String value) {
            addCriterion("member_id like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotLike(String value) {
            addCriterion("member_id not like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<String> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<String> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(String value1, String value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(String value1, String value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
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

        public Criteria andChanUserIdIsNull() {
            addCriterion("chan_user_id is null");
            return (Criteria) this;
        }

        public Criteria andChanUserIdIsNotNull() {
            addCriterion("chan_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andChanUserIdEqualTo(String value) {
            addCriterion("chan_user_id =", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNotEqualTo(String value) {
            addCriterion("chan_user_id <>", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdGreaterThan(String value) {
            addCriterion("chan_user_id >", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("chan_user_id >=", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdLessThan(String value) {
            addCriterion("chan_user_id <", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdLessThanOrEqualTo(String value) {
            addCriterion("chan_user_id <=", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdLike(String value) {
            addCriterion("chan_user_id like", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNotLike(String value) {
            addCriterion("chan_user_id not like", value, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdIn(List<String> values) {
            addCriterion("chan_user_id in", values, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNotIn(List<String> values) {
            addCriterion("chan_user_id not in", values, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdBetween(String value1, String value2) {
            addCriterion("chan_user_id between", value1, value2, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNotBetween(String value1, String value2) {
            addCriterion("chan_user_id not between", value1, value2, "chanUserId");
            return (Criteria) this;
        }

        public Criteria andChanUserNameIsNull() {
            addCriterion("chan_user_name is null");
            return (Criteria) this;
        }

        public Criteria andChanUserNameIsNotNull() {
            addCriterion("chan_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andChanUserNameEqualTo(String value) {
            addCriterion("chan_user_name =", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameNotEqualTo(String value) {
            addCriterion("chan_user_name <>", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameGreaterThan(String value) {
            addCriterion("chan_user_name >", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("chan_user_name >=", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameLessThan(String value) {
            addCriterion("chan_user_name <", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameLessThanOrEqualTo(String value) {
            addCriterion("chan_user_name <=", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameLike(String value) {
            addCriterion("chan_user_name like", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameNotLike(String value) {
            addCriterion("chan_user_name not like", value, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameIn(List<String> values) {
            addCriterion("chan_user_name in", values, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameNotIn(List<String> values) {
            addCriterion("chan_user_name not in", values, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameBetween(String value1, String value2) {
            addCriterion("chan_user_name between", value1, value2, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andChanUserNameNotBetween(String value1, String value2) {
            addCriterion("chan_user_name not between", value1, value2, "chanUserName");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
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

        public Criteria andPayPasswdIsNull() {
            addCriterion("pay_passwd is null");
            return (Criteria) this;
        }

        public Criteria andPayPasswdIsNotNull() {
            addCriterion("pay_passwd is not null");
            return (Criteria) this;
        }

        public Criteria andPayPasswdEqualTo(String value) {
            addCriterion("pay_passwd =", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdNotEqualTo(String value) {
            addCriterion("pay_passwd <>", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdGreaterThan(String value) {
            addCriterion("pay_passwd >", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdGreaterThanOrEqualTo(String value) {
            addCriterion("pay_passwd >=", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdLessThan(String value) {
            addCriterion("pay_passwd <", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdLessThanOrEqualTo(String value) {
            addCriterion("pay_passwd <=", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdLike(String value) {
            addCriterion("pay_passwd like", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdNotLike(String value) {
            addCriterion("pay_passwd not like", value, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdIn(List<String> values) {
            addCriterion("pay_passwd in", values, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdNotIn(List<String> values) {
            addCriterion("pay_passwd not in", values, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdBetween(String value1, String value2) {
            addCriterion("pay_passwd between", value1, value2, "payPasswd");
            return (Criteria) this;
        }

        public Criteria andPayPasswdNotBetween(String value1, String value2) {
            addCriterion("pay_passwd not between", value1, value2, "payPasswd");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNull() {
            addCriterion("lock_time is null");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNotNull() {
            addCriterion("lock_time is not null");
            return (Criteria) this;
        }

        public Criteria andLockTimeEqualTo(Date value) {
            addCriterion("lock_time =", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotEqualTo(Date value) {
            addCriterion("lock_time <>", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThan(Date value) {
            addCriterion("lock_time >", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lock_time >=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThan(Date value) {
            addCriterion("lock_time <", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThanOrEqualTo(Date value) {
            addCriterion("lock_time <=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeIn(List<Date> values) {
            addCriterion("lock_time in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotIn(List<Date> values) {
            addCriterion("lock_time not in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeBetween(Date value1, Date value2) {
            addCriterion("lock_time between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotBetween(Date value1, Date value2) {
            addCriterion("lock_time not between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameIsNull() {
            addCriterion("chan_user_realname is null");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameIsNotNull() {
            addCriterion("chan_user_realname is not null");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameEqualTo(String value) {
            addCriterion("chan_user_realname =", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameNotEqualTo(String value) {
            addCriterion("chan_user_realname <>", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameGreaterThan(String value) {
            addCriterion("chan_user_realname >", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("chan_user_realname >=", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameLessThan(String value) {
            addCriterion("chan_user_realname <", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameLessThanOrEqualTo(String value) {
            addCriterion("chan_user_realname <=", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameLike(String value) {
            addCriterion("chan_user_realname like", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameNotLike(String value) {
            addCriterion("chan_user_realname not like", value, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameIn(List<String> values) {
            addCriterion("chan_user_realname in", values, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameNotIn(List<String> values) {
            addCriterion("chan_user_realname not in", values, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameBetween(String value1, String value2) {
            addCriterion("chan_user_realname between", value1, value2, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserRealnameNotBetween(String value1, String value2) {
            addCriterion("chan_user_realname not between", value1, value2, "chanUserRealname");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberIsNull() {
            addCriterion("chan_user_id_number is null");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberIsNotNull() {
            addCriterion("chan_user_id_number is not null");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberEqualTo(String value) {
            addCriterion("chan_user_id_number =", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberNotEqualTo(String value) {
            addCriterion("chan_user_id_number <>", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberGreaterThan(String value) {
            addCriterion("chan_user_id_number >", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberGreaterThanOrEqualTo(String value) {
            addCriterion("chan_user_id_number >=", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberLessThan(String value) {
            addCriterion("chan_user_id_number <", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberLessThanOrEqualTo(String value) {
            addCriterion("chan_user_id_number <=", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberLike(String value) {
            addCriterion("chan_user_id_number like", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberNotLike(String value) {
            addCriterion("chan_user_id_number not like", value, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberIn(List<String> values) {
            addCriterion("chan_user_id_number in", values, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberNotIn(List<String> values) {
            addCriterion("chan_user_id_number not in", values, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberBetween(String value1, String value2) {
            addCriterion("chan_user_id_number between", value1, value2, "chanUserIdNumber");
            return (Criteria) this;
        }

        public Criteria andChanUserIdNumberNotBetween(String value1, String value2) {
            addCriterion("chan_user_id_number not between", value1, value2, "chanUserIdNumber");
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