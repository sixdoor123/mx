/**
 * 
 */
package com.baiyi.cmall.utils;

import com.baiyi.cmall.Config;

/**
 * @author tangkun
 *
 */
public class UserNetUrl {
	
	/**
	 * 用户消息列表
	 */
	public static String getUserMessageList(String mid,int state, int pageIndex, int pageSize)
	{
		return String.format(Config.NEW_URL
				+ "api/my/myMessageList/?user=%s&state=%s&page=%s&count=%s",
				mid,state, pageIndex, pageSize);
	}
	
	/**
	 * 系统消息列表
	 * @param memberID
	 * @param pageIndex
	 * @param pageSize
	 */
	public static String getMultiUserMessageList(int pageIndex, int pageSize)
	{
		return String.format(Config.NEW_URL
				+ "api/my/sysMessageList?page=%s&count=%s",
				 pageIndex, pageSize);
	}
	
	/**
	 * 设置所有消息已读-
	 */
	public static String getUserAllMarkRead(String memberID)
	{
		return String.format(Config.NEW_URL + "api/my/setAllMessageToRead?user=%s",
				memberID);
		
	}
	/**
	 * 设置单个消息已读
	 * @param messageID
	 */
	public static String getMarkRead(String messageID)
	{
		return String.format(Config.NEW_URL + "api/my/setOneMessageToRead?msgid=%s",
				messageID);
	}

}
