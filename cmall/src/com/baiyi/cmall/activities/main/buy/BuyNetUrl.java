package com.baiyi.cmall.activities.main.buy;

import com.baiyi.cmall.Config;

public class BuyNetUrl {

	/**
	 * ���ﳵ�б�
	 * 
	 * @return
	 */
	public static final String getShopCar() {
		return Config.ROOT_URL + "User/Cart";
	}

	/**
	 * ���ɶ���
	 * 
	 * @return
	 */
	public static final String getOrderSubmit() {
		return Config.ROOT_URL + "User/ProductOrder/ProductSubmit";
	}

	/**
	 * ����֧���б�
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
	 * ȥ֧��
	 * 
	 * @param oi
	 *            ����id
	 * @return
	 */
	public static final String getOrderPay(String oi) {
		return Config.ROOT_URL + "User/ProductOrder/Pay/" + oi;
	}

	public static final String getOrder() {
		return Config.ROOT_URL + "User/Order/Product";
	}

	/**
	 * �����ע
	 * 
	 * @return http://u1q4567516.iask.in/cmallwebservice/User/Cart/Follow
	 */
	public static String getJoinFollow() {
		String url = Config.ROOT_URL + "User/Cart/Follow";
		return url;
	}

}
