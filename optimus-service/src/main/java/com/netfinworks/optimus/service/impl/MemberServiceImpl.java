package com.netfinworks.optimus.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.netfinworks.optimus.domain.enums.MemberStateKind;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.MemberEntityExample;
import com.netfinworks.optimus.mapper.AccountEntityMapper;
import com.netfinworks.optimus.mapper.MemberEntityMapper;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.repository.PrimaryNoRepository;

@Service
public class MemberServiceImpl implements MemberService {
	private static Logger log = LoggerFactory
			.getLogger(MemberServiceImpl.class);

	@Resource
	private MemberEntityMapper memberEntityMapper;
	@Resource
	private AccountEntityMapper accountEntityMapper;
	@Resource
	private PrimaryNoRepository primaryNoRepository;

	// 事务管理
	@Resource(name = "transactionTemplate")
	private TransactionTemplate txTmpl;

	@Override
	public MemberEntity getOrCreateMember(String chanId, String chanUserId,
			String chanUserName, String mobile) {
		MemberEntity member = getMemberEntityByChanUserId(chanId, chanUserId);
		if (member == null) {
			member = txTmpl.execute(new TransactionCallback<MemberEntity>() {
				@Override
				public MemberEntity doInTransaction(TransactionStatus status) {
					Date date = new Date();
					String memberId = primaryNoRepository.getMemberNo();
					MemberEntity member = new MemberEntity();
					member.setAccountId(memberId);
					member.setChanId(chanId);
					member.setChanUserId(chanUserId);
					member.setChanUserName(chanUserName);
					member.setMemberId(memberId);
					member.setMobile(mobile);
					member.setStatus(MemberStateKind.Normal.getCode());
					member.setType("INVEST");
					member.setCreateTime(date);
					member.setUpdateTime(date);
					memberEntityMapper.insert(member);

					AccountEntity record = new AccountEntity();
					record.setAccountId(memberId);
					record.setAccountName("");
					record.setAccountTitleNo("2001");
					record.setBalance(new BigDecimal("0"));
					record.setBalanceDirection("");
					record.setCreateTime(date);
					record.setCreditbalance(new BigDecimal("0"));
					record.setDebitbalance(new BigDecimal("0"));
					record.setFrozenBalance(new BigDecimal("0"));
					record.setUpdateTime(date);

					accountEntityMapper.insert(record);
					return member;
				}
			});
		}
		return member;
	}

	public MemberEntity getMemberEntity(String chanId, String memberId) {
		MemberEntityExample example = new MemberEntityExample();
		example.createCriteria().andChanIdEqualTo(chanId)
				.andMemberIdEqualTo(memberId).andTypeEqualTo("INVEST");
		List<MemberEntity> list = memberEntityMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public MemberEntity getMemberEntity(String memberId) {
		return memberEntityMapper.selectByPrimaryKey(memberId);
	}

	@Override
	public MemberEntity getMemberEntityByChanUserId(String chanId,
			String chanUserId) {
		MemberEntityExample example = new MemberEntityExample();
		example.createCriteria().andChanIdEqualTo(chanId)
				.andChanUserIdEqualTo(chanUserId);
		List<MemberEntity> list = memberEntityMapper.selectByExample(example);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public MemberEntity updateEntity(MemberEntity member) {
		memberEntityMapper.updateByPrimaryKey(member);
		return member;
	}

	/**
	 * 获取平台后台管理员账户
	 * 
	 * @param plat
	 * @return
	 */
	public MemberEntity getPlatAdminMember(String plat) {
		MemberEntityExample example = new MemberEntityExample();
		example.createCriteria().andChanIdEqualTo(plat).andTypeEqualTo("ADMIN");
		List<MemberEntity> list = memberEntityMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
