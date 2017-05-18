/**
 * 
 */
package com.netfinworks.optimus.service.repository.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.netfinworks.common.util.DateUtil;
import com.netfinworks.optimus.seq.OptimusSequenceDAO;
import com.netfinworks.optimus.service.repository.PrimaryNoRepository;

/**
 * <p>注释</p>
 */
@Repository
public class PrimaryNoRepositoryImpl implements PrimaryNoRepository {

    @Resource
    private OptimusSequenceDAO sequenceDAO;

    public static final String formatSequence(Long nextVal) {
        //  不足5位补零
        String nextVoucherNoSeq = String.format("%05d", nextVal);
        //  超过5位截取
        return StringUtils.substring(nextVoucherNoSeq, nextVoucherNoSeq.length() - 5);
    }

    private String buildDefaultPrimaryNo(Long nextSeq, String flagSubject) {
        String sequence = formatSequence(nextSeq);
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtil.getLongDateString(new Date()));
        sb.append(flagSubject);
        sb.append(sequence);
        return sb.toString();
    }

    @Override
    public String getApplyOrderNo() {
        return buildDefaultPrimaryNo(sequenceDAO.getOrderId(), PREFIX_APPLY_DEFAULT);
    }

    @Override
    public String getRedeemOrderNo() {
        return buildDefaultPrimaryNo(sequenceDAO.getOrderId(), PREFIX_REDEEM_DEFAULT);
    }

    @Override
    public Long getProfitId() {
        return sequenceDAO.getOrderId();
    }

    @Override
    public String getRedeemRequestNoWithPrefix(String prefix) {
        return buildDefaultPrimaryNo(sequenceDAO.getOrderId(), prefix);
    }

    @Override
    public String getApplyRequestNoWithPrefix(String prefix) {
        return buildDefaultPrimaryNo(sequenceDAO.getOrderId(), prefix);
    }

	@Override
	public String getMemberNo() {
		return "2001"+String.format("%07d", sequenceDAO.getMemberId());		
	}

	@Override
	public String getOrderNo() {
		return buildDefaultPrimaryNo(sequenceDAO.getOrderId(), "");
	}

}
