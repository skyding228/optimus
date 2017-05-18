package com.netfinworks.optimus.seq;

import javax.annotation.Resource;

import com.netfinworks.common.mysql.repository.SequenceRepository;

/**
 * <p></p>
 *
 */
public class OptimusSequenceDAOImpl implements OptimusSequenceDAO {
//    private static final String APPLY_ORDER        = "applyOrder";
//    private static final String REDEEM_ORDER = "redeemOrder";
//    private static final String PROFIT_ID = "profitId";
    private static final String MEMBER_ID = "memberId";
    private static final String ORDER_ID = "orderId";
    
    @Resource
    private SequenceRepository sequenceRepository;
//    @Override
//    public long getApplyOrderId() {
//        return sequenceRepository.next(APPLY_ORDER);
//    }
//
//    @Override
//    public long getProfitId() {
//        return sequenceRepository.next(PROFIT_ID);
//    }
//
//    @Override
//    public long getRedeenOrderId() {
//        return sequenceRepository.next(REDEEM_ORDER);
//    }

	@Override
	public long getMemberId() {
		return sequenceRepository.next(MEMBER_ID);
	}

	@Override
	public long getOrderId() {
		return sequenceRepository.next(ORDER_ID);
	}
}
