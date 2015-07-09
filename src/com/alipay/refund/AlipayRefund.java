/**
 * 
 */
package com.alipay.refund;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alipay.config.AlipayConfig;
import com.alipay.refund.services.AlipayService;
import com.alipay.refund.util.AlipayMd5Encrypt;
import com.daoshun.common.CommonUtils;

/**
 * @author qiuch
 * 
 */
public class AlipayRefund {

	String service = "refund_fastpay_by_platform_nopwd";
	// final String notify_url = CommonUtils.getWebRootUrl() + "alipay_refund";
	// final String notify_url ="" + "alipay_refund";
	final String batch_num = "1";
	final String return_type = "xml";
	String batch_no;
	String refund_date;
	String detail_data;
	String params;
	Map<String, String> paramsMap;

	public AlipayRefund(String userid, String out_trade_no, String money, String reason) {
		Date date = new Date();
		refund_date = CommonUtils.getTimeFormat(date, "yyyy-MM-dd HH:mm:ss");
		batch_no = CommonUtils.getTimeFormat(date, "yyyyMMddHHmmss") + userid + (int) (Math.random() * 100);
		// 原付款支付宝交易号^退款总金额^退款理由
		detail_data = out_trade_no + "^" + money + "^" + reason;
		StringBuffer sb = new StringBuffer();
		sb.append("_input_charset=" + AlipayConfig.input_charset);
		sb.append("&batch_no=" + batch_no);
		sb.append("&batch_num=" + batch_num);
		sb.append("&detail_data=" + detail_data);
		sb.append("&notify_url=" + AlipayConfig.notify_url);
		if ("1".equals(CommonUtils.getAliSet())) {
			sb.append("&partner=" + AlipayConfig.partner);
		} else {
			sb.append("&partner=" + AlipayConfig.partner_formal);
		}

		sb.append("&refund_date=" + refund_date);
		sb.append("&return_type=" + return_type);
		sb.append("&service=" + service);
		String sign;
		if ("1".equals(CommonUtils.getAliSet())) {
			sign = AlipayMd5Encrypt.md5(sb.toString() + AlipayConfig.private_key);
		} else {
			sign = AlipayMd5Encrypt.md5(sb.toString() + AlipayConfig.private_key_formal);
		}

		// sb.append("&sign=" + sign);
		// sb.append("&sign_type=" + AlipayConfig.sign_type);
		paramsMap = new HashMap<String, String>();
		// paramsMap.put("_input_charset", AlipayConfig.input_charset);
		paramsMap.put("batch_no", batch_no);
		paramsMap.put("batch_num", batch_num);
		paramsMap.put("detail_data", detail_data);
		// paramsMap.put("notify_url",AlipayConfig.notify_url);
		// paramsMap.put("partner",AlipayConfig.partner);
		paramsMap.put("refund_date", refund_date);
		paramsMap.put("return_type", return_type);
		// paramsMap.put("service",service);
		// paramsMap.put("sign", sign);
		// paramsMap.put("sign_type", AlipayConfig.sign_type);
	}

	public String executeRefund() {
		String result = "fail";
		try {
			result = AlipayService.refund_fastpay_by_platform_nopwd(paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.indexOf("<is_success>T</is_success>") >= 0) {
			result = "success";
		}
		return result;
	}
}
