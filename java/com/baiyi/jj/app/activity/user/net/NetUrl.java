package com.baiyi.jj.app.activity.user.net;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.manager.paypal.PayConfig;


public class NetUrl {



	public static String getUrlAbout(){
		return  Config.getNewURL()+"about?v=";
	}
	public static String getUrlTerms(){
//		return "https://www.iubenda.com/privacy-policy/7937039";
		return Config.getNewURL()+"terms";

	}
	public static String getUrlSupport(){
		return Config.getNewURL()+"support";
	}


	//https://api.paypal.com/v1/identity/openidconnect/userinfo/?schema=openid
	public static String getPaypalToken(){
		return PayConfig.PayPalUrl+"/v1/oauth2/token";
	}
	public static String getPaypalInfo(String openid){
		return PayConfig.PayPalUrl + "/v1/identity/openidconnect/userinfo/?schema=openid";
//		https://api.paypal.com/v1/identity/openidconnect/userinfo/?schema=openid
	}

	/**
	 * ������¼
	 * @return
	 */
	public static String getInitName(String mobileId){
		return String.format(Config.getNewURL() + "api/customer/initName/?mobileid=%s", mobileId);
	}

	public static String getLoginTrigger(){
		return Config.getNewURL() + "api/v1/auth/logintrigger/";
	}

	public static String getShareToFriend(String mid, String sysid ,String language)
	{
//		return String.format(Config.getNewURL() + "share/%s/%s/?language=%s", mid, sysid,language);
		return "https://goo.gl/dEqQde";
	}
	public static String getShareToFriend()
	{
		return "https://goo.gl/dEqQde";
	}

	/**
	 * ��¼
	 * @return
	 */
	public static String getPostMemberLogin(){
		//return Config.getNewURL()+"api/customer/loginByAccount/";
		return Config.getNewURL()+"o/token/";
	}
	/**
	 * ��ȡTicketKey
	 * @return
	 */
	public static String getTicketKey()
	{
		//return Config.getNewURL()+"api/customer/getTicketKey/?token="+token;
		return Config.getNewURL()+"api/v1/member/";
	}
	/**
	 * ���ٵ�¼
	 * @param mobile
	 * @param code
	 */
	public static String getDynamicLogin(String mobile, String code)
	{
		return String.format(Config.getNewURL() + "api/customer/dynamicLogin?mobile=%s&code=%s", mobile, code);
	}
	/**
	 * ע�� post
	 */
	public static String getRegisterByMobile(){
		return Config.getNewURL()+"api/customer/registerByMobileCode/?";
	}
	
	/**
	 * �޸��ǳ�
	 */
	public static String FixUserName(String token,String newName)
	{
		return String.format(Config.getNewURL()+"api/customer/updateNickName/?token=%s&nickName=%s",token,newName);//get����
	}
	/**
	 * ���ֻ�
	 */
	public static String getBindMobile() {
		return Config.getNewURL() + "api/customer/updateMobile/";
	}

	/**
	 * ֧����������
	 */
	public static String getUpdatePaymentPassword(String token,
			String payPassword) {
		return String.format(Config.getNewURL()
				+ "api/customer/updatePaymentPassword?token=%s&paymentPWD=%s",
				token, payPassword);
	}
	/**
	 * ��֤֧������
	 */
	public static String getVerificationPaymentPassword(String token,
			String payPassword) {
		return String.format(Config.getNewURL()
				+ "api/customer/checkPaymentPassword/?token=%s&paymentPWD=%s",
				token, payPassword);
	}
	
	/**
	 * ��������--��������
	 */
	public static String ResetPwd(String phone)
	{
		return Config.getNewURL()+"/api/customer/resetPassword/?";
	}
	/**
	 * �޸��û���
	 * @param token
	 */
	public static String getUpdateLoginName(String token, String newName)
	{
		return String.format(Config.getNewURL() + "api/customer/updateLoginName/?token=%s&username=%s", token, newName);
	}
	
	/**
	 * ���õ�¼����
	 */
	public static String getChangePassword(String token,String pwd,boolean isdel) {
//		return Config.getNewURL() + "api/customer/changePwd/";
		return String.format(Config.getNewURL()
				+ "api/customer/changePwd/?token=%s&newPwd=%s&isDelToken=%s",
				token,pwd,isdel);
	}
	/**
	 * �޸����� ���ӿ���get��
	 */
	public static String FixPasswd(String token,String pwd,String newPwd)
	{
//		return Config.DEBUG_NEW_URL+"Member/UpdatePwd?";
		return String.format(Config.getNewURL()
					+ "api/customer/updatepassword/?token=%s&pwd=%s&newpwd=%s",
					token,pwd,newPwd);
	}
	/**
	 * ��ȡ���
	 * @return
	 */
	public static String getBalance(String token){
		return String.format(Config.getNewURL()+"api/customer/getbalance/?token=%s",token);
	}
	/**
	 * �������ֻ���ɳ�ֵ 
	 * @return
	 */
	public static String getAddPoint(){
		return Config.getNewURL()+"api/customer/updateMember";
	}
	/**
	 * ��ȡ���ֻ���ɳ�ֵ post
	 * @return
	 */
	public static String getPoint(){
		return Config.getNewURL()+"api/customer/getMemberPoints/?";
	}
	/**
	 * ��ȡ�û��ȼ���Ϣ
	 * @return
	 */
	public static String getLevelInfo(String mid){
		return String.format(Config.getNewURL()+"api/customer/getMemberUpgradeInfo/?mid=%s",mid);
	}
	/**
	 * ����������ȡ������ϸ��Ϣ
	 * @return
	 */
	public static String getPointsRecordList()
	{
		return Config.getNewURL() + "api/customer/getRecordPointsPoint/?";
	}
	
	/**
	 * ��ȡ�û����ַ�ʽ
	 * @return
	 */
	public static String getPresentManner(String mid){
		return String.format(Config.getNewURL()+"api/finance/IsSetPresentManner?MID=%s",mid);
	}
	/**
	 * ��ȡ���ֶһ�����
	 * @return
	 */
	public static String getPointScale(){
		return Config.getNewURL()+"api/finance/PointScale";
	}
	/**
	 * ��ȡ�û����ּ�¼
	 * @return
	 */
	public static String getPresentRecord(){
		return Config.getNewURL()+"api/finance/GetPresentRecord";
	}
	/**
	 * �������ַ�ʽ
	 * @return
	 */
	public static String addPresentManner(){
		return Config.getNewURL()+"api/finance/AddPresentManner";
	}
	/**
	 * �޸����ַ�ʽ
	 * @return
	 */
	public static String updatePresentManner(){
		return Config.getNewURL()+"api/finance/UpdatePresentManner";
	}
	/**
	 * ����Ĭ�����ַ�ʽ
	 * @return
	 */
	public static String setDefaultMethods(int extraId,String mid){
		return String.format(Config.getNewURL()+"api/finance/SetDefaultExtractionMethods/%s?MID=%s",extraId,mid);
	}
	/**
	 * ��������
	 * @return
	 */
	public static String presentApplication(){
		return Config.getNewURL()+"api/finance/PresentApplication";
	}
	
	/**
	 * ��ȡͷ��
	 * @param mid
	 */
	public static String getPhoto(String mid)
	{
		return String.format(Config.getNewURL() + "api/customer/getPicture/?mid=%s", mid);
	}
	
	/**
	 * �û���Ϣ�б�
	 */
	public static String getUserMessageList(String mid,int state, int pageIndex, int pageSize)
	{
		return String.format(Config.getNewURL()
				+ "api/my/myMessageList/?user=%s&state=%s&page=%s&count=%s",
				mid,state, pageIndex, pageSize);
	}
	
	/**
	 * ϵͳ��Ϣ�б�
	 * @param
	 * @param pageIndex
	 * @param pageSize
	 */
	public static String getMultiUserMessageList(int pageIndex, int pageSize)
	{
		return String.format(Config.getNewURL()
				+ "api/my/sysMessageList?page=%s&count=%s",
				 pageIndex, pageSize);
	}
	
	/**
	 * ����������Ϣ�Ѷ�-
	 */
	public static String getUserAllMarkRead(String memberID)
	{
		return String.format(Config.getNewURL() + "api/my/setAllMessageToRead?user=%s",
				memberID);
		
	}
	/**
	 * ���õ�����Ϣ�Ѷ�
	 * @param messageID
	 */
	public static String getMarkRead(String messageID)
	{
		return String.format(Config.getNewURL() + "api/my/setOneMessageToRead?msgid=%s",
				messageID);
	}
	/**
	 *  ע�ᷢ����֤��
	 * @param mobile
	 * @return
	 */
	public static String getSendMobileReg(String mobile)
	{
		return String.format(Config.getNewURL() + "api/customer/registerMsg?mobile=%s", mobile);
	}
	/**
	 * get��ʽ
	 * ��ͨ�ķ�����֤��
	 * @param mobile
	 * @return
	 */
	public static String getSendMobile(String mobile)
	{
		return String.format(Config.getNewURL() + "api/customer/sendMsg?mobile=%s", mobile);
	}
	
	/**
	 * ��������
	 * ������֤�� 
	 * @param mobile
	 */
	public static String SendMobileCodeValidForgetPassword(String mobile)
	{
		return String.format(Config.getNewURL() + "api/customer/forgotPwdMsg/?mobile=%s", mobile);
	}

	/**
	 * ����ͷ��
	 * 
	 */
	public static String getPostPhoto(){
		return Config.getNewURL()+"api/customer/postPhoto/?";
	}
	
	
	/**
	 *  ��ȡ��������
	 * @param page
	 * @return
	 */
	public static String getFaqs(int page){
		return String.format(Config.getNewURL() + "api/help/faqs/%s",page);
	}
	
	/**
	 *  �����������
	 * @param mid
	 * @return
	 */
	public static String getAddFeed(String mid){
		return String.format(Config.getNewURL() + "api/help/feedback/add/%s",mid);
	}
	/**
	 *  ��ȡ�������
	 * @param page
	 * @return
	 */
	public static String getFeedList(String mid,int page){
		return String.format(Config.getNewURL() + "api/help/feedbacks/%s/%s ",mid,page);
	}
	
	
	// TODO  ���½ӿ��õ��Ǿɽӿ�
	// TODO  ���½ӿ��õ��Ǿɽӿ�
	// TODO  ���½ӿ��õ��Ǿɽӿ�
	
	
	
	
	/**
	 *  �������ֻ�ʱ������֤��
	 * @return
	 */
	
	public static String getMsgReadedCountUrl(String mid)
	{
		return Config.getNewURL() + "api/my/myMessage/unread/" + mid;
	}


	public static String getRegisterByNormal() {
		return Config.getNewURL() + "api/v1/auth/register/" ;
	}

	/**
	 * ��ȡ�ο�ע�����Ϣ
	 * api/v1/auth/gust/
	 * @return
     */
	public static String getVisitorRegisterInfoUrl() {
		return Config.getNewURL() + "api/v1/auth/gust/" ;
	}

	public static String getInitGustUrl() {
		return Config.getNewURL() + "api/v1/auth/gust/init/" ;
	}

	/**
	 *上传头像
	 * @return
     */
	public static String getUpHeadUrl() {
		return Config.getNewURL() + "api/v1/member/avatar/" ;
	}

	// 积分规则
	public static String getIntegal(String contry) {
		return String.format(Config.getNewURL() + "api/v1/integral/config/?country=%s",contry) ;
	}

	// 刷新积分
	public static String updateIntegral() {
		return Config.getNewURL() + "api/v1/member/integral/";
	}

	/**
	 *关注－获取未关注热词接口
	 * @return
	 */
	public static String getNoFollowUrl(String channel_id ,int page ,int limit) {
//		return  String.format(Config.getNewURL() + "api/v1/hotword/nonfollow/?channel_id=%s&page=%s&limit=%s",channel_id,page,limit) ;
		return  String.format(Config.getNewURL() + "api/v1/hotword/list/?channel_id=%s&page=%s&limit=%s",channel_id,page,limit) ;
	}
	/**
	 *关注－热词搜索接口
	 * @return
	 */
	public static String getSearchNoFollowUrl(String keyword ,int page ,int limit) {
		return  String.format(Config.getNewURL() + "api/v1/hotword/search/?keyword=%s",keyword) ;
//		return  String.format(Config.getNewURL() + "api/v1/hotword/search/?keyword=%s&page=%s&limit=%s",keyword,page,limit) ;
	}
	// you tag de Channel
	public static String getTagChannel() {
		return  Config.getNewURL() +"api/v1/hotword/channles/";
	}

	// ip get country
	public static String getIpCountry() {
		return  "http://membership.turboapp.xyz/api/v1/member/getipinfo/";
//		return  Config.NEW_URL_US +"api/v1/member/getipinfo/";
	}
	public static String getEndpointIsTrue(String arn) {
		return  String.format(Config.getNewURL()+"notify/sns/valid/?os=android&arn=%s",arn);
//		return  Config.NEW_URL_US +"api/v1/member/getipinfo/";
	}

	/**
	 *  add attention
     * @return
     */
	public static String addAttetion() {
		return  Config.getNewURL() +"api/v1/follow/hotword/add/";
	}
	/**
	 *  del attention
	 * @return
	 */
	public static String delAttetion() {
		return  Config.getNewURL() +"api/v1/follow/hotword/cancel/";
	}

	/**
	 *  my attention list
	 * @return
	 */
	public static String getAttetionList(int page,int limit) {
		return  String.format(Config.getNewURL() +"api/v1/follow/hotword/list/?page=%s&limit=%s",page,limit);
	}

	public static String getWordsList(String after,int limit,String keyword) {
		return  String.format(Config.getNewURL() +"api/v1/hotword/acticle/after/?after=%s&limit=%s&keyword=%s",after,limit,keyword);
	}

	public static String getEditionList(int page,int limit,String articl_id) {
		return  String.format(Config.getNewURL() +"api/v1/special/article/list/?page=%s&limit=%s&article_id=%s",page,limit,articl_id);
	}
}
