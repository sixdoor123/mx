package com.baiyi.cmall.activities.main.mall;

import com.baiyi.cmall.Config;

/**
 * @author tangkun
 *
 */
public final class UrlNet {
	
	/**
	 * 商城主页
	 * @param cd 分类号码
	 * @return
	 */
	public static final String getMallMain(String cd)
	{
		return Config.ROOT_URL + "Index/Mall/" + cd;
	}
	
	/**
	 * 商城主页-菜单
	 * @return
	 */
	public static final String getMallMainMenu()
	{
		return Config.ROOT_URL + "Index/Mall/Category";
	}
	
	/**
	 * 商城主页-菜单
	 * @return
	 */
	public static final String getMallCategoryList()
	{
		return Config.ROOT_URL + "Index/Mall";
	}
	
	/**
	 * 产品详情-商品
	 * @param pi
	 * @return
	 */
	public static final String getMallDetailProduct(String pi)
	{
		return Config.ROOT_URL + "Index/Product/" + pi;
	}
	
	/**
	 * 商品规格-购物车
	 * @param pi
	 * @return
	 */
	public static final String getShopCarData(String pi)
	{
		return Config.ROOT_URL + "Index/Product/PriceDictionary/" + pi;
	}
	
	
	/**
	 * 产品详情-详情
	 * @param pi
	 * @return
	 */
	public static final String getMallDetail(String pi)
	{
		return Config.ROOT_URL + "Index/Product/Detail/" + pi;
	}
	/**
	 * 商城-筛选-全部分类
	 * @return
	 */
	public static final String getMallCategory()
	{
		return Config.ROOT_URL + "Index/Mall/Fif";
	}
	
	/**
	 * 加入购物车
	 * @return
	 */
	public static final String getAddUserCart()
	{
		return Config.ROOT_URL + "User/Cart/AddUserCart";
	}
	
	/**
	 * 判断该用户是否已经关注该产品
	 * @param pi
	 * @return
	 */
	public static final String getFollowFocus(String pi)
	{
		return Config.ROOT_URL+"User/Follow/Focus/"+pi;
	}
	
	/**添加产品为关注
	 * 
	 * @param pi
	 * @return
	 */
	public static final String addFollowProduct(String pi){
		
		return Config.ROOT_URL+"User/Follow/Product/"+pi;
	}
	
	/**取消产品为关注
	 * 
	 * @param pi
	 * @return
	 */
	public static final String FollowCancel(String pi){
		
		return Config.ROOT_URL+"User/Follow/Cancel/"+pi;
	}
	
	/**
	 * 判断该用户是否已经关注该商家
	 * @param pi
	 * @return
	 */
	public static final String getFollowFocusCompany(String pi)
	{
		return Config.ROOT_URL+"User/Follow/FocusCompany/"+pi;
	}
	
	/**
	 * 添加商家为关注
	 * @param pi
	 * @return
	 */
	public static final String addFollowCompany(){
		
		return Config.ROOT_URL+"IndexUserFollows/AddCompany";
	}
}
