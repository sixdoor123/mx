package com.baiyi.jj.wbshare;

public class Constans {
	
	/**
	 *  ΢��
	 */
	public static final String WeiXinAPP_ID = "wx30cdaf96b4b204d3";
	public static final String WenXinAppSecret = "496ad1638d9e6927bb7f6492a63f6f57";
	public static final String WX_APP_SCOPE = "snsapi_userinfo";
	public static final String WX_APP_STATE = "login_state";
	
	/**
	 * ��Ѷ����ƽ̨
	 */
	private static final String TencentAPP_ID = "1104870066";
	private static final String TencentAPPKEY = "64ktSeVylFKxCX0c";
	
	/**
     *  ����΢��
     */
    public static final String SinaApp_Key = "3831822777";
    /**
     * ��ǰ DEMO Ӧ�õĻص�ҳ��������Ӧ�ÿ���ʹ���Լ��Ļص�ҳ��
     *
     * <p>
     * ע��������Ȩ�ص�ҳ���ƶ��ͻ���Ӧ����˵���û��ǲ��ɼ��ģ����Զ���Ϊ������ʽ������Ӱ�죬
     * ����û�ж��彫�޷�ʹ�� SDK ��֤��¼��
     * ����ʹ��Ĭ�ϻص�ҳ��https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";
    /**
     * Scope �� OAuth2.0 ��Ȩ������ authorize �ӿڵ�һ��������ͨ�� Scope��ƽ̨�����Ÿ����΢��
     * ���Ĺ��ܸ������ߣ�ͬʱҲ��ǿ�û���˽�������������û����飬�û����� OAuth2.0 ��Ȩҳ����Ȩ��
     * ѡ����Ӧ�õĹ��ܡ�
     *
     * ����ͨ������΢������ƽ̨-->��������-->�ҵ�Ӧ��-->�ӿڹ������ܿ�������Ŀǰ������Щ�ӿڵ�
     * ʹ��Ȩ�ޣ��߼�Ȩ����Ҫ�������롣
     *
     * Ŀǰ Scope ֧�ִ����� Scope Ȩ�ޣ��ö��ŷָ���
     *
     * �й���Щ OpenAPI ��ҪȨ�����룬��鿴��http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * ���� Scope ���ע�������鿴��http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public static final String WeiBoACTION = "com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY";
}
