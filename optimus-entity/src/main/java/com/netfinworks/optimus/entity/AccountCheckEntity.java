package com.netfinworks.optimus.entity;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 账户对账
 * 
 * @author weichunhe create at 2016年4月5日
 */
public class AccountCheckEntity extends BaseEntity {
	private String orderTime, orderId, outerOrderId, chanUserId, orderType,
			amount, orderStatus, memo;

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOuterOrderId() {
		return outerOrderId;
	}

	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}

	public String getChanUserId() {
		return chanUserId;
	}

	public void setChanUserId(String chanUserId) {
		this.chanUserId = chanUserId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 将对象转换为以|分隔的序列
	 * 
	 * @param this
	 * @return
	 */
	public StringBuilder toRow() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getOrderTime());
		sb.append("|");
		sb.append(this.getOrderId());
		sb.append("|");
		sb.append(this.getOuterOrderId());
		sb.append("|");
		sb.append(this.getChanUserId());
		sb.append("|");
		sb.append(this.getOrderType());
		sb.append("|");
		sb.append(this.getAmount());
		sb.append("|");
		sb.append(this.getOrderStatus());
		sb.append("|");
		sb.append(this.getMemo());
		return sb;
	}

	/**
	 * 将字符串转换为对象
	 * 
	 * @param row
	 * @return
	 */
	public static AccountCheckEntity fromRow(String row) {
		if (StringUtils.isEmpty(row)) {
			return null;
		}
		AccountCheckEntity entity = new AccountCheckEntity();
		String[] arrs = row.split("\\|");
		if (arrs.length != 8) { // 8 列
			return null;
		}
		entity.setOrderTime(arrs[0]);
		entity.setOrderId(arrs[1]);
		entity.setOuterOrderId(arrs[2]);
		entity.setChanUserId(arrs[3]);
		entity.setOrderType(arrs[4]);
		entity.setAmount(arrs[5]);
		entity.setOrderStatus(arrs[6]);
		entity.setMemo(arrs[7]);
		return entity;
	}

	/**
	 * 判断两个对象是否相等
	 * 
	 * @param entity
	 * @return
	 */
	public boolean eq(AccountCheckEntity entity) {
		if (!StringUtils.equals(getOrderId(), entity.getOrderId())) {
			return false;
		}
		if (!StringUtils.equals(getOrderTime(), entity.getOrderTime())) {
			return false;
		}
		BigDecimal a = new BigDecimal(getAmount()), b = new BigDecimal(
				entity.getAmount());
		if (a.compareTo(b) != 0) {
			return false;
		}

		if (!StringUtils.equals(getOuterOrderId(), entity.getOuterOrderId())) {
			return false;
		}

		if (!StringUtils.equals(getChanUserId(), entity.getChanUserId())) {
			return false;
		}
		if (!StringUtils.equals(getOrderType(), entity.getOrderType())) {
			return false;
		}
		if (!StringUtils.equals(getOrderStatus(), entity.getOrderStatus())) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		System.out
				.println(("2016-04-22 10:11:48|2016042210065820001|2016042200000371|00000100002003|deposit|1000.00|F|账户已冻结"
						.split("|")).length);
		AccountCheckEntity entity = AccountCheckEntity
				.fromRow("2016-04-22 10:11:48|2016042210065820001|2016042200000371|00000100002003|deposit|1000.00|F|账户已冻结");
		System.out.println(entity);
	}
}
