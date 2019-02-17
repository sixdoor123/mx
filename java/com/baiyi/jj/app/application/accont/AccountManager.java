/**
 *
 */
package com.baiyi.jj.app.application.accont;

import android.util.Log;

import com.baiyi.core.file.Preference;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;

/**
 * 账号设置管理
 * @author tangkun
 *
 */
public class AccountManager {

	private int Offline_Size;
	//要闻是否推送
	private int News_Is_Push;
	//非WIFI下不下载大图 0.hide 1.sd,2.hd
	private int Wifi_Is_Display_Img;
//	是否显示摘要 0.无 1.少 2.多
//	是否显示摘要 0.无 1.少
	private int Summary_Is_Display;
	//新闻内容是否显示评论
	private boolean Detail_Comment_Is_Display;
	//段子是周排行还是月排行
	private String Interest_Rank;
	// 收藏管理 0.30天 1.365天 2.长期
	private int Manage_Collect;
	// 评论管理 0.30天 1.365天 2.长期
	private int Manage_Comment;
	// 阅读管理 0.30天 1.365天 2.长期
	private int Manage_Read;
	//字体设置
	private int currentSize;
	//未读消息数量
	private int msgReadedCount;
	//字体大小是否改变
	private static boolean isFontChange = false;
	private String account;
	private String pwd;
	private String mid;
	private String isStayLogin; //是否保持登陆
	private Preference pref = null;

	private String NewsDetailUrl;

	private static AccountManager instance = null;

	private AccountManager()
	{
		init(false);
	}

	public static AccountManager getInstance()
	{
		if(instance == null)
		{
			instance = new AccountManager();
		}
		return instance;
	}

	private void init(boolean isReset)
	{
		initPreference();

		if (isReset){
			mid = "";
			setIsStayLogin("false");
		}else {
			//页面设置 分账号
			mid = getMid();
			isStayLogin = pref.Get(PageSet.Xml_Stay_Login,"false");
		}
		News_Is_Push = pref.getInt(PageSet.Xml_News_Is_Push,0);
		Offline_Size = pref.getInt(PageSet.Xml_Offline_Size,0);
		Wifi_Is_Display_Img = pref.getInt(PageSet.Xml_Wifi_Is_Display_Img, 1);
		Summary_Is_Display = pref.getInt(PageSet.Xml_Summary_Is_Display, 0);
		Detail_Comment_Is_Display = pref.getBoolean(PageSet.Xml_Detail_Comment_Is_Display + mid, true)/* ? 0 : 1*/;
		Manage_Collect = pref.getInt(PageSet.Xml_Manage_Collect + mid, 0);
		Manage_Comment = pref.getInt(PageSet.Xml_Manage_Comment + mid, 0);
		Manage_Read = pref.getInt(PageSet.Xml_Manage_Read + mid, 0);
		currentSize = pref.getInt(PageSet.currentFontSizeName, FontUtil.Font_middle);
		account = pref.Get(PageSet.NAME_user_account, null);
		pwd = pref.Get(PageSet.NAME_user_mm, null);

		NewsDetailUrl = pref.Get(PageSet.XML_NEWSDETAIL, NetUtils.getDefaltDetail());
//		TLog.e("account",account+"=========="+pwd);
	}

	public void initPreference()
	{
		if(pref == null)
		{
			pref = Preference.getInstance();
		}
	}

	/**
	 * 是否保持登陆状态
     */
	public boolean isAutoLogin() {
		if (Utils.isStringEmpty(isStayLogin)){
			return false;
		}
		if (IsStayLogin().equals("false"))
			return false;
		if (Utils.isStringEmpty(getPwd())) {
			return false;
		}
		return true;
	}

	/**
	 *  是否去执行用户登陆
	 * @return
     */
	public boolean isLogin() {

		if (CmsApplication.getUserInfoEntity() != null && CmsApplication.getUserInfoEntity().getUserType()!=UserInfoEntity.UserType_Gust)
			return false;
		if (Utils.isStringEmpty(getPwd())) {
			return false;
		}
		return true;
	}
//		UserInfoEntity entity = CmsApplication.getUserInfoEntity();
//		if (entity != null) {
//			if (entity.getAccount() != null)
//			{
//				if (entity.getAccount().equals(entity.getPwd()))
//				{
//					return  false ;
//				}
//				else
//				{
//					return true;
//				}
//			}
//			return false;
//		}
//		else
//		{
//			if (!Utils.isStringEmpty(getAccount()))
//			{
//				if (getAccount().equals(getPwd()))
//				{
//					return  false ;
//				}
//				else
//				{
//					return true;
//				}
//			}
//			return false ;
//		}
//
//		return true;


	public String getNewsDetailUrl() {
		return NewsDetailUrl;
	}

	public void setNewsDetailUrl(String newsDetailUrl) {
		NewsDetailUrl = newsDetailUrl;
		pref.Set(PageSet.XML_NEWSDETAIL, newsDetailUrl);
		pref.saveConfig();
	}

	public int getWifi_Is_Display_Img() {
		return Wifi_Is_Display_Img;
	}

	public int getNews_Is_Push() {
		return News_Is_Push;
	}
	public void setNews_Is_Push(int news_Is_Push) {
		News_Is_Push = news_Is_Push;
		pref.Set(PageSet.Xml_News_Is_Push + mid, String.valueOf(news_Is_Push));
		pref.saveConfig();
	}

	public int getOffline_Size() {
		return Offline_Size;
	}

	public void setOffline_Size(int offline_Size) {
		Offline_Size = offline_Size;
		pref.Set(PageSet.Xml_Offline_Size, String.valueOf(offline_Size));
		pref.saveConfig();
	}

	public void setWifi_Is_Display_Img(int wifi_Is_Display_Img) {
		Wifi_Is_Display_Img = wifi_Is_Display_Img;
		pref.Set(PageSet.Xml_Wifi_Is_Display_Img, String.valueOf(wifi_Is_Display_Img));
		pref.saveConfig();
	}

	public int getSummary_Is_Display() {
		return Summary_Is_Display;
	}

	public void setSummary_Is_Display(int summary_Is_Display) {
		Summary_Is_Display = summary_Is_Display;
		pref.Set(PageSet.Xml_Summary_Is_Display, String.valueOf(summary_Is_Display));
		pref.saveConfig();
	}

	public boolean getDetail_Comment_Is_Display() {
		return Detail_Comment_Is_Display;
	}

	public void setDetail_Comment_Is_Display(boolean detail_Comment_Is_Display) {
		Detail_Comment_Is_Display = detail_Comment_Is_Display;
		pref.Set(PageSet.Xml_Detail_Comment_Is_Display + mid, String.valueOf(detail_Comment_Is_Display));
		pref.saveConfig();
	}

	public String getInterest_Rank() {
		return Interest_Rank;
	}

	public void setInterest_Rank(String interest_Rank) {
		Interest_Rank = interest_Rank;
		pref.Set(PageSet.Xml_Interest_Rank + mid, interest_Rank);
		pref.saveConfig();
	}

	public int getManage_Collect() {
		return Manage_Collect;
	}

	public void setManage_Collect(int manage_Collect) {
		Manage_Collect = manage_Collect;
		pref.Set(PageSet.Xml_Manage_Collect + mid, String.valueOf(manage_Collect));
		pref.saveConfig();
	}

	public int getManage_Comment() {
		return Manage_Comment;
	}

	public void setManage_Comment(int manage_Comment) {
		Manage_Comment = manage_Comment;
		pref.Set(PageSet.Xml_Manage_Comment + mid, String.valueOf(manage_Comment));
		pref.saveConfig();
	}

	public int getManage_Read() {
		return Manage_Read;
	}

	public void setManage_Read(int manage_Read) {
		Manage_Read = manage_Read;
		pref.Set(PageSet.Xml_Manage_Read + mid, String.valueOf(manage_Read));
		pref.saveConfig();
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int fontSize) {
		if(fontSize == currentSize)
		{
			return;
		}
		this.currentSize = fontSize;
		setFontChange(true);
		pref.Set(PageSet.currentFontSizeName, String.valueOf(currentSize));
		pref.saveConfig();
	}
	public static boolean isFontChange() {
		return isFontChange;
	}
	public static void setFontChange(boolean isFontChange) {
		AccountManager.isFontChange = isFontChange;
	}

	public String getAccount() {

		if (Utils.isStringEmpty(account))
		{
			account = pref.Get(PageSet.NAME_user_account);
		}
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
		pref.Set(PageSet.NAME_user_account, account);
		pref.saveConfig();
	}

	public String getPwd() {
		if (Utils.isStringEmpty(pwd))
		{
			pwd = pref.Get(PageSet.NAME_user_mm);
		}
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
		pref.Set(PageSet.NAME_user_mm, pwd);
		pref.saveConfig();
	}

	public String IsStayLogin(){
		return isStayLogin;
	}
	public void setIsStayLogin(String isStayLogin){
		this.isStayLogin = isStayLogin;
		pref.Set(PageSet.Xml_Stay_Login, isStayLogin);
		pref.saveConfig();
	}

	public void setMid(String mid)
	{
		this.mid = mid;
		pref.Set(PageSet.User_Mid, mid);
		pref.saveConfig();
	}

	public void setGustMid(String mid)
	{
		this.mid = mid;
		pref.Set(XMLName.Xml_CustomerKey, mid);
		pref.saveConfig();
	}

	public int getMsgReadedCount() {
		return msgReadedCount;
	}

	public void setMsgReadedCount(int msgReadedCount) {
		this.msgReadedCount = msgReadedCount;
		pref.Set(PageSet.Msg_Readed_Count + mid, String.valueOf(msgReadedCount));
		pref.saveConfig();
	}

	public String getMid()
	{
		UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
		if (infoEntity != null){
			return infoEntity.getMID();
		}else {
			return pref.Get(PageSet.User_Mid, "");
		}
//		if(isLogin())
//		{
//			if(infoEntity != null)
//			{
//				return infoEntity.getMID();
//			}
//			return pref.Get(PageSet.User_Mid, "");
//		}else
//		{
//			if(infoEntity != null)
//			{
//				return infoEntity.getMID();
//			}
//			return Preference.getInstance().Get(XMLName.Xml_CustomerKey, "");
//		}
	}

	public void reset(boolean isReset)
	{
		init(isReset);
	}
}
