package com.daoshun.common;

public class PayType {
	/**
	 * 余额，备注：以前历史订单记录中 余额是 0
	 */
	public static int MONEY=1;
	/**
	 * 小巴券
	 */
    public static int COUPON=2;
    /**
     * 小巴币
     */
    public static int COIN=3;
    /**
     * 小巴币+余额的混合支付
     */
    public static int COIN_MONEY=4;
}
