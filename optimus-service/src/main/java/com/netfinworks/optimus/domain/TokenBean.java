package com.netfinworks.optimus.domain;

import org.apache.commons.codec.digest.DigestUtils;

import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.util.JSONUtil;

public class TokenBean {

	private String token;
	private String platNo;
	// 验证token的url地址
	private String url;

	private long deadline;
	// 对此对象加密后的值
	private String key;

	private UserInfo user;

	private MemberEntity member;

	public TokenBean(String token, String platNo) {
		super();
		this.token = token;
		this.platNo = platNo;
		setDeadline();

		this.key = DigestUtils.sha1Hex(JSONUtil.toJson(this));
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPlatNo() {
		return platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}

	public long getDeadline() {
		return deadline;
	}

	/**
	 * 有效期 30s
	 * 
	 * @param deadline
	 */
	public void setDeadline() {
		this.deadline = System.currentTimeMillis() + 30000;
	}

	/**
	 * key是否在有效期内
	 * 
	 * @return
	 */
	public boolean keyIsValid() {
		return System.currentTimeMillis() < deadline;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

}
