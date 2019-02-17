package com.baiyi.cmall.activities.main.mall;

import com.baiyi.cmall.Config;

/**
 * @author tangkun
 *
 */
public final class UrlNet {
	
	/**
	 * �̳���ҳ
	 * @param cd �������
	 * @return
	 */
	public static final String getMallMain(String cd)
	{
		return Config.ROOT_URL + "Index/Mall/" + cd;
	}
	
	/**
	 * �̳���ҳ-�˵�
	 * @return
	 */
	public static final String getMallMainMenu()
	{
		return Config.ROOT_URL + "Index/Mall/Category";
	}
	
	/**
	 * �̳���ҳ-�˵�
	 * @return
	 */
	public static final String getMallCategoryList()
	{
		return Config.ROOT_URL + "Index/Mall";
	}
	
	/**
	 * ��Ʒ����-��Ʒ
	 * @param pi
	 * @return
	 */
	public static final String getMallDetailProduct(String pi)
	{
		return Config.ROOT_URL + "Index/Product/" + pi;
	}
	
	/**
	 * ��Ʒ���-���ﳵ
	 * @param pi
	 * @return
	 */
	public static final String getShopCarData(String pi)
	{
		return Config.ROOT_URL + "Index/Product/PriceDictionary/" + pi;
	}
	
	
	/**
	 * ��Ʒ����-����
	 * @param pi
	 * @return
	 */
	public static final String getMallDetail(String pi)
	{
		return Config.ROOT_URL + "Index/Product/Detail/" + pi;
	}
	/**
	 * �̳�-ɸѡ-ȫ������
	 * @return
	 */
	public static final String getMallCategory()
	{
		return Config.ROOT_URL + "Index/Mall/Fif";
	}
	
	/**
	 * ���빺�ﳵ
	 * @return
	 */
	public static final String getAddUserCart()
	{
		return Config.ROOT_URL + "User/Cart/AddUserCart";
	}
	
	/**
	 * �жϸ��û��Ƿ��Ѿ���ע�ò�Ʒ
	 * @param pi
	 * @return
	 */
	public static final String getFollowFocus(String pi)
	{
		return Config.ROOT_URL+"User/Follow/Focus/"+pi;
	}
	
	/**��Ӳ�ƷΪ��ע
	 * 
	 * @param pi
	 * @return
	 */
	public static final String addFollowProduct(String pi){
		
		return Config.ROOT_URL+"User/Follow/Product/"+pi;
	}
	
	/**ȡ����ƷΪ��ע
	 * 
	 * @param pi
	 * @return
	 */
	public static final String FollowCancel(String pi){
		
		return Config.ROOT_URL+"User/Follow/Cancel/"+pi;
	}
	
	/**
	 * �жϸ��û��Ƿ��Ѿ���ע���̼�
	 * @param pi
	 * @return
	 */
	public static final String getFollowFocusCompany(String pi)
	{
		return Config.ROOT_URL+"User/Follow/FocusCompany/"+pi;
	}
	
	/**
	 * ����̼�Ϊ��ע
	 * @param pi
	 * @return
	 */
	public static final String addFollowCompany(){
		
		return Config.ROOT_URL+"IndexUserFollows/AddCompany";
	}
}
