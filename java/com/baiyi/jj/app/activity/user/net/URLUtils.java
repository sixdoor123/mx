package com.baiyi.jj.app.activity.user.net;

import java.net.URLEncoder;

public class URLUtils {

	public static final String MemberWhat = "what";
	public static final String MemberHow = "how";
	public static final String MemberWhich = "which";
	// 什么是用户积分
//	public static final String whatUrl = "http://cms.jj365.cn:8001/mobile/store_value/?type=what";
//	// 如何使用积分
//	public static final String useUrl = "http://cms.jj365.cn:8001/mobile/store_value/?type=how";
//	// 如何获得积分
//	public static final String ruleUrl = "http://cms.jj365.cn:8001/mobile/store_value/?type=which";

	public static String getMemberUrl(String type,String theme){
		return String.format("http://cms.jj365.cn:8001/mobile/store_value/?type=%s&theme=%s", type,theme);
	}
	
	// 什么是积点
//	public static final String whatPointUrl = "http://cms.jj365.cn:8001/mobile/store_point/?type=what";
//	// 如何使用积点
//	public static final String usePointUrl = "http://cms.jj365.cn:8001/mobile/store_point/?type=how";
//	// 如何获得积点
//	public static final String rulePointUrl = "http://cms.jj365.cn:8001/mobile/store_point/?type=which";

	public static final String PointWhat = "what";
	public static final String PointHow = "how";
	public static final String PointWhich = "which";
	public static String getPointUrl(String type,String theme){
		return String.format("http://cms.jj365.cn:8001/mobile/store_point/?type=%s&theme=%s", type,theme);
	}
	
	// 什么是成长值
//	public static final String whatGrowthUrl = "http://cms.jj365.cn:8001/mobile/upgrade_value/?type=what";
//	// 如何获得成长值
//	public static final String getGrowthUrl = "http://cms.jj365.cn:8001/mobile/upgrade_value/?type=which";
	// 什么是会员等级
	public static final String whatLeverUrl = "http://www.dcshenghuo.com/phone/memberrank/what";
	// 如何晋升等级
	public static final String ruleLeverUrl = "http://www.dcshenghuo.com/phone/memberrank/rule";
	
	public static final String GrowthWhat = "what";
	public static final String GrowthWhich = "which";
	public static String getGrowthUrl(String type,String theme){
		return String.format("http://cms.jj365.cn:8001/mobile/upgrade_value/?type=%s&theme=%s", type,theme);
	}
	
	// 用户协议
	public static String getHelpUrl(String theme){
		return String.format("http://cms.jj365.cn:8001/mobile/user_protocol/?theme=%s",theme);
	}
	// 用户协议
//	public static final String helpUrl = "http://cms.jj365.cn:8001/mobile/user_protocol/";
	// 退款帮助
	public static final String refundHelpUrl = "http://www.dcshenghuo.com/phone/help/refund";
	// 支付帮助
	public static final String payHelpUrl = "http://www.dcshenghuo.com/phone/help/pay";
	
	/**
	 *  积分商城
	 */
	private static String urlMarketString = "http://117.34.95.103:8082/";
	
	
	public static String getMemberMarket(String sysid,String token,String mid)
	{
		return String.format(urlMarketString+
				"home/index.html?sysid=%s&token=%s&mid=%s",sysid, token,mid);
	}
	// token失效后重新登陆之后的url
	public static String getMemberMarketReturn(String sysid,String token,String mid,String returnUrl)
	{
		return String.format(urlMarketString+
				"home/redirecttomall.html?sysid=%s&token=%s&mid=%s&returnurl=%s",
				sysid, token,mid, URLEncoder.encode(returnUrl));
	}
	
	
	
}
