package com.daoshun.common;
/**
 * 订单相关状态
 * @author 卢磊
 *
 */
public class OrderState {
	/**
	 * 能取消订单
	 */
	public static int CAN_CANCEL=1;
	/**
	 * 不能取消订单
	 */
	public static int CANNOT_CANCEL=0;
	/**
	 * 可以评价
	 */
	public static int CAN_COMMENT=1;
	/**
	 * 不能评价
	 */
	public static int CANNOT_COMMENT=0;
	/**
	 * 可以投诉
	 */
	public static int CAN_COMPLAINT=1;
	/**
	 * 不能投诉
	 */
	public static int CANNOT_COMPLAINT=0;
	
}
