package com.baiyi.cmall.activities.main.business;

import com.baiyi.cmall.Config;
import com.tencent.map.b.p;

/**
 * @author tangkun
 * 
 */
public final class BusinessUrlNet {

	/**
	 * 商家-店铺全部商品列表
	 * 
	 * @return
	 */
	public static final String getShopProductList(int ci) {
		return Config.ROOT_URL + "Index/Company/Product/" + ci;
	}

	/**
	 * 商家-店铺全部供应列表-------接口有问题，正在重新开发中
	 * 
	 * @return
	 */
	public static final String getShopSupplyList(int ci,int pageIndex,
			int pageSize) {
		String s = Config.ROOT_URL + "Index/Company/Offer/%s/%s/%s";
		s = String.format(s,ci,pageIndex, pageSize);
		return s;
	}
	
	/**
	 * 商家详情
	 * @param ci 商家id
	 * @return
	 */
	public static final String getCompanyDetail(int ci)
	{
		return Config.ROOT_URL + "Index/Company/Detail/" + ci;
	}
	
	
	/**
	 * 商家二维码
	 * @param ci 商家id
	 * @return
	 */
	public static final String getQRCodeApp(int ci)
	{
		return Config.ROOT_URL + "Index/Company/QRCodeApp/"+ci ;
	}
}
