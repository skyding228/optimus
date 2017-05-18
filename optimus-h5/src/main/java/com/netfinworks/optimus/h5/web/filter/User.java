package com.netfinworks.optimus.h5.web.filter;

import java.io.Serializable;

public class User implements Serializable{
	 /** 渠道id */
    private String chanId;
    /** 渠道用户id */
    private String chanUserId;
    /** 渠道用户名 */
    private String chanUserName;
    /** 手机号 */
    private String mobile;
	public String getChanId() {
		return chanId;
	}
	public void setChanId(String chanId) {
		this.chanId = chanId;
	}
	public String getChanUserId() {
		return chanUserId;
	}
	public void setChanUserId(String chanUserId) {
		this.chanUserId = chanUserId;
	}
	public String getChanUserName() {
		return chanUserName;
	}
	public void setChanUserName(String chanUserName) {
		this.chanUserName = chanUserName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
}
