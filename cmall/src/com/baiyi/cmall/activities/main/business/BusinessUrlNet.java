package com.baiyi.cmall.activities.main.business;

import com.baiyi.cmall.Config;
import com.tencent.map.b.p;

/**
 * @author tangkun
 * 
 */
public final class BusinessUrlNet {

	/**
	 * �̼�-����ȫ����Ʒ�б�
	 * 
	 * @return
	 */
	public static final String getShopProductList(int ci) {
		return Config.ROOT_URL + "Index/Company/Product/" + ci;
	}

	/**
	 * �̼�-����ȫ����Ӧ�б�-------�ӿ������⣬�������¿�����
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
	 * �̼�����
	 * @param ci �̼�id
	 * @return
	 */
	public static final String getCompanyDetail(int ci)
	{
		return Config.ROOT_URL + "Index/Company/Detail/" + ci;
	}
	
	
	/**
	 * �̼Ҷ�ά��
	 * @param ci �̼�id
	 * @return
	 */
	public static final String getQRCodeApp(int ci)
	{
		return Config.ROOT_URL + "Index/Company/QRCodeApp/"+ci ;
	}
}
