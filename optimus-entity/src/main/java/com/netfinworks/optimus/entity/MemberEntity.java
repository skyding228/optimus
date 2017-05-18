package com.netfinworks.optimus.entity;

import java.util.Date;

public class MemberEntity extends BaseEntity {
    /** 用户id   */
    private String memberId;

    /** 渠道id */
    private String chanId;

    /** 渠道用户id */
    private String chanUserId;

    /** 渠道用户名 */
    private String chanUserName;

    /** 手机号 */
    private String mobile;

    /** 账户id */
    private String accountId;

    /** 支付密码 */
    private String payPasswd;

    /** 创建时间 */
    private Date createTime;

    /** 最后更新时间 */
    private Date updateTime;

    /** 状态  Normal：正常     Lock：密码错误锁定     Freeze：风控冻结  */
    private String status;

    /** 锁定时间 */
    private Date lockTime;

    /** 类型 INVEST:投资账户,ADMIN:管理账户 */
    private String type;

    /** 渠道用户真实姓名 */
    private String chanUserRealname;

    /** 渠道用户身份证编号 */
    private String chanUserIdNumber;

    /**
     * 用户id  
     * @return memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 用户id  
     * @param memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 渠道id
     * @return chanId
     */
    public String getChanId() {
        return chanId;
    }

    /**
     * 渠道id
     * @param chanId
     */
    public void setChanId(String chanId) {
        this.chanId = chanId;
    }

    /**
     * 渠道用户id
     * @return chanUserId
     */
    public String getChanUserId() {
        return chanUserId;
    }

    /**
     * 渠道用户id
     * @param chanUserId
     */
    public void setChanUserId(String chanUserId) {
        this.chanUserId = chanUserId;
    }

    /**
     * 渠道用户名
     * @return chanUserName
     */
    public String getChanUserName() {
        return chanUserName;
    }

    /**
     * 渠道用户名
     * @param chanUserName
     */
    public void setChanUserName(String chanUserName) {
        this.chanUserName = chanUserName;
    }

    /**
     * 手机号
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 账户id
     * @return accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账户id
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 支付密码
     * @return payPasswd
     */
    public String getPayPasswd() {
        return payPasswd;
    }

    /**
     * 支付密码
     * @param payPasswd
     */
    public void setPayPasswd(String payPasswd) {
        this.payPasswd = payPasswd;
    }

    /**
     * 创建时间
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 最后更新时间
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 最后更新时间
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 状态  Normal：正常     Lock：密码错误锁定     Freeze：风控冻结 
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态  Normal：正常     Lock：密码错误锁定     Freeze：风控冻结 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 锁定时间
     * @return lockTime
     */
    public Date getLockTime() {
        return lockTime;
    }

    /**
     * 锁定时间
     * @param lockTime
     */
    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    /**
     * 类型 INVEST:投资账户,ADMIN:管理账户
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 INVEST:投资账户,ADMIN:管理账户
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 渠道用户真实姓名
     * @return chanUserRealname
     */
    public String getChanUserRealname() {
        return chanUserRealname;
    }

    /**
     * 渠道用户真实姓名
     * @param chanUserRealname
     */
    public void setChanUserRealname(String chanUserRealname) {
        this.chanUserRealname = chanUserRealname;
    }

    /**
     * 渠道用户身份证编号
     * @return chanUserIdNumber
     */
    public String getChanUserIdNumber() {
        return chanUserIdNumber;
    }

    /**
     * 渠道用户身份证编号
     * @param chanUserIdNumber
     */
    public void setChanUserIdNumber(String chanUserIdNumber) {
        this.chanUserIdNumber = chanUserIdNumber;
    }
}