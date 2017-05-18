package com.netfinworks.optimus.service;

import com.netfinworks.optimus.entity.MemberEntity;

public interface MemberService {
	MemberEntity getOrCreateMember(String chanId ,String chanUserId,String chanUserName ,String mobile) ;
	
	MemberEntity getMemberEntityByChanUserId(String chanId, String chanUserId);
	
	MemberEntity getMemberEntity(String memberId);	
	
	MemberEntity getMemberEntity(String chanId,String memberId);
	
	MemberEntity updateEntity(MemberEntity member);
	/**
	 * 获取平台后台管理员账户
	 * 
	 * @param plat
	 * @return
	 */
	public MemberEntity getPlatAdminMember(String plat);
}
