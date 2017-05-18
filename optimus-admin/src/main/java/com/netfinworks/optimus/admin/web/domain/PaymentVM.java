package com.netfinworks.optimus.admin.web.domain;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

/**
 * 出入款管理
 * 
 * @author weichunhe create at 2016年3月25日
 */
@Component
public class PaymentVM extends PaymentEntity {
	// 出款 or 入款
	private String paymentTypeV;

	private String amountV;
	// 定期的就是 放款 还款 基金的就是 申购 赎回
	private String actionTypeV;
	// fund 是基金,subject是定期产品
	private String type;

	// 只有基金使用，轧差结束时间
	private long nettingEndTime;

	private String platName;
	// 是否已审核
	private boolean hasAudited = false;

	private static ConfigService configService;

	private static OrderRepository orderRepository;

	public PaymentVM() {
	}

	public PaymentVM(PaymentEntity entity) {
		BeanUtil.copyProperties(this, entity);
		setPaymentTypeV(PaymentRecordEnum.valueOf(getPaymentType()).getDesc());
		setActionTypeV(PaymentRecordEnum.valueOf(getActionType()).getDesc());
		setType(getSubjectType());
		setAmountV(FormatUtil.formatRate(getAmount()));
		setPlatName(configService.getPlatName(getPlat()));
		if (StringUtils.isNotEmpty(getOrderId())) {
			// 判断此订单是否有ext2
			OrderEntity order = orderRepository.getOrderByNo(getOrderId());
			hasAudited = StringUtils.isNotEmpty(order.getExt2());
		}
	}

	@Autowired
	public void setConfigService(ConfigService configService) {
		PaymentVM.configService = configService;
	}

	@Autowired
	public void setOrderRepository(OrderRepository orderRepository) {
		PaymentVM.orderRepository = orderRepository;
	}

	public boolean isHasAudited() {
		return hasAudited;
	}

	public void setHasAudited(boolean hasAudited) {
		this.hasAudited = hasAudited;
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}

	public String getPaymentTypeV() {
		return paymentTypeV;
	}

	public void setPaymentTypeV(String paymentTypeV) {
		this.paymentTypeV = paymentTypeV;
	}

	public String getAmountV() {
		return amountV;
	}

	public void setAmountV(String amountV) {
		this.amountV = amountV;
	}

	public String getActionTypeV() {
		return actionTypeV;
	}

	public void setActionTypeV(String actionTypeV) {
		this.actionTypeV = actionTypeV;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getNettingEndTime() {
		return nettingEndTime;
	}

	public void setNettingEndTime(long nettingEndTime) {
		this.nettingEndTime = nettingEndTime;
	}

}
