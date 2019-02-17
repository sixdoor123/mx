package com.baiyi.cmall.activities.main.buy;

import com.baiyi.cmall.Config;

public class BuyNetUrl {

	/**
	 * 购物车列表
	 * 
	 * @return
	 */
	public static final String getShopCar() {
		return Config.ROOT_URL + "User/Cart";
	}

	/**
	 * 生成订单
	 * 
	 * @return
	 */
	public static final String getOrderSubmit() {
		return Config.ROOT_URL + "User/ProductOrder/ProductSubmit";
	}

	/**
	 * 订单支付列表
	 * 
	 * @return
	 */
	public static final String getSendOrder() {
		return Config.ROOT_URL + "User/ProductOrder/Product";
	}

	public static final String deleteCart() {
		return Config.ROOT_URL + "User/Cart/Delete";
	}

	/**
	 * 去支付
	 * 
	 * @param oi
	 *            订单id
	 * @return
	 */
	public static final String getOrderPay(String oi) {
		return Config.ROOT_URL + "User/ProductOrder/Pay/" + oi;
	}

	public static final String getOrder() {
		return Config.ROOT_URL + "User/Order/Product";
	}

	/**
	 * 移入关注
	 * 
	 * @return http://u1q4567516.iask.in/cmallwebservice/User/Cart/Follow
	 */
	public static String getJoinFollow() {
		String url = Config.ROOT_URL + "User/Cart/Follow";
		return url;
	}

}
