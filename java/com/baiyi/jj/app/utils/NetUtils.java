package com.baiyi.jj.app.utils;

import android.content.Context;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.language.SwitchLanguageUtils;

public class NetUtils {


    public static String getErrorUrl() {
        return Config.getNewURL() + "api/help/crash/add/client_id/error";
    }

    /**
     * 文章详情URL
     */
    public static String getNewsDetailWeb(String ArticleId, boolean isWifi, boolean isShow, boolean isIndia) {
//		return String.format(Config.getNewURL() +"article/%s?wifi=%s",ArticleId,isWifi?"1":"0");
        if (!isIndia) {
            return String.format(Config.getNewURL() + "article/?id=%s&wifi=%s", ArticleId, "1");
        }
        if (isShow) {
//			return String.format(Config.getNewURL() +"article4/?id=%s&wifi=%s",ArticleId,isWifi?"1":"0");
            return String.format(Config.getDetailUrl() + "/%s/?&wifi=%s", ArticleId, isWifi ? "1" : "0");
//			http://stories.cdn.in.turboapp.xyz/$id/?wifi=0或1
        } else {
//			return String.format(Config.getNewURL() +"article4/?id=%s&wifi=%s",ArticleId,"1");
            return String.format(Config.getDetailUrl() + "/%s/?&wifi=%s", ArticleId, "1");
        }
//		return String.format(Config.getNewURL() +"article4/?id=%s&wifi=%s",ArticleId,"0");
//		return String.format("http://turboapp.xyz:8010/article/%s/",ArticleId);
    }

    public static String getMorningData() {
        return Config.getNewURL() + "api/v1/morningpaper/";
    }
    public static String getMorningDataList() {
        return Config.getNewURL() + "api/v1/morningpaper/articles/";
    }

    /**
     * 文章相关阅读
     */
    public static String getNewsReading(String ArticleId) {
        return String.format(Config.getNewURL() + "api/v1/search/related_reading/?id=%s", ArticleId);
    }

    /**
     * 文章相关阅读
     */
    public static String getPleaseAttention(String ArticleId) {
        return String.format(Config.getNewURL() + "article/hotwords/%s", ArticleId);
    }

    /**
     * ���¹�עƵ��
     *
     * @return
     */
    public static String getUpdateChannel() {
        //return Config.getNewURL() + "api/my/channel/";
//		return String.format(Config.getNewURL() + "api/v1/channel/subscribe/%s/",tablename);
        return Config.getNewURL() + "api/v2/channel/subscribe/";
    }

    /**
     * �ۺ�Ƶ��
     *
     * @param tableName
     * @return
     */
    public static String getHomeNewsList(String tableName) {
//		return String.format(Config.getNewURL() + "api/news/index/?user=%s&page=%s", userId,page);
//		return Config.getNewURL() + "api/push/module/" + tableName;
//		return Config.getNewURL() + "api/v1/article/push/all/";
//		return Config.getNewURL() + "api/v2/article/push/";
        return Config.getNewURL() + "api/v3/article/push/";
//		return Config.getNewURL() + "api/v4/article/push/";

    }
    public static String getHomeNewsListv5() {
        return Config.getNewURL() + "api/v5/article/push/";
    }

    public static String getLocalNewslist() {
        return Config.getNewURL() + "api/v1/article/local/";
    }

    /**
     * �ۺ�Ƶ��--����
     */
    public static String getDiscoverList(String Mid, int page) {
//		return String.format(Config.getNewURL() + "api/news/index/?user=%s&page=%s", userId,page);
        return String.format(Config.getNewURL() + "api/photo/index/%s/%s", Mid, page);
    }

    /**
     * ��ȡ��ҳ Ƶ���б�
     *
     * @param userId
     * @param table_name
     * @return
     */
    public static String getChannelCoverPage(String userId, String table_name, String group) {
//		return String.format(Config.getNewURL() + "api/my/channelCoverPage/?user=%s&table_name=%s&channel_group=%s",
//				userId,table_name,group);
        return String.format(Config.getNewURL() + "api/channels/coverPage/?user=%s&table_name=%s&channel_group=%s",
                userId, table_name, group);
    }

    /**
     * ����Ƶ��id��ȡ��ѯ�б�
     *
     * @param tableName
     * @return
     */
    public static String getNewsToChannelId(String tableName) {
//		return String.format(Config.getNewURL() + "api/news/channel/?user=%s&seq=%s&page=%s",Mid, channelseq, page);
//		return Config.getNewURL() + "api/push/channel/" + tableName;
//		return Config.getNewURL() + "api/v1/article/push/channel/";
        return Config.getNewURL() + "api/v3/article/push/";
//		return Config.getNewURL() + "api/v4/article/push/";
    }

    /**
     * ����Ƶ��id��ȡ���ֵ�һ�б�
     */
    public static String getDiscoverToChannelId(String channelseq, int page) {
        return String.format(Config.getNewURL() + "api/photo/channel/%s/%s", channelseq, page);
    }

    /**
     * ����Ƶ��id��ȡ֪ʶ�б�
     *
     * @param Mid
     * @param channelseq
     * @param page
     * @return
     */
    public static String getReadToChannelId(String Mid, String channelseq, int page) {
        return String.format(Config.getNewURL() + "api/read/channel?user=%s&seq=%s&page=%s", Mid, channelseq, page);
//		return String.format(Config.getNewURL() + "api/read/?channelid=%s&page=%s", channelId, page);
    }

    /**
     * ����Ƶ��id��ȡȤζ�б�
     *
     * @param channelId
     * @param page
     * @return
     */
    public static String getinterestToChannelId(String channelId, int page, String mid) {
//		return String.format(Config.getNewURL() + "api/interest/?channelid=%s&page=%s", channelId, page);
        return String.format(Config.getNewURL() + "api/interest/channel?user=%s&page=%s&seq=%s", mid, page, channelId);
//		http://121.201.58.72:8000/api/interest/channel?user=1&page=1&seq=26 
    }

    /**
     * ���ӵ������к�������
     */
    public static String getInterestByDate(String channelId, String dateType, int page, int pageSize) {
        return String.format(Config.getNewURL() + "api/interest/channel_by_date/?channelid=%s&datetype=%s&page=%s&pagesize=%s", channelId, dateType, page, pageSize);
    }

    /**
     * ����Ƶ��id��ȡ�����б�
     *
     * @param channelId
     * @param page
     * @return
     */
    public static String getphotoToChannelId(String channelId, int page, String Mid) {
//		return String.format(Config.getNewURL() + "api/photo/?channelid=%s&page=%s", channelId, page);
        return String.format(Config.getNewURL() + "api/photo/channel?user=%s&page=%s&seq=%s", Mid, page, channelId);
//		http://121.201.58.72:8000/api/photo/channel?user=1&page=1&seq=26
    }


    /**
     * ��ȡ��Ѷ�б�
     *
     * @param channelId
     * @param page
     * @return
     */
    public static String getNewsList(String channelId, int page) {
        return String.format(Config.getNewURL() + "api/news/articleslist/?channelid=%s&page=%s", channelId, page);
    }

    /**
     * ��ȡ֪ʶ�б�
     *
     * @param userId
     * @param page
     * @return
     */
    public static String getReadList(String userId, int page) {
        return String.format(Config.getNewURL() + "api/read/index?user=%s&page=%s", userId, page);
    }

    /**
     * ��ȡȤζ�б�
     *
     * @param userId
     * @param page
     * @return
     */
    public static String getInterstList(String userId, int page) {
        return String.format(Config.getNewURL() + "api/interest/index/?user=%s&page=%s", userId, page);
    }

    /**
     * ��ȡ�����б�
     *
     * @param userId
     * @param group
     * @param page
     * @return
     */
    public static String getPhototList(String userId, int group, int page) {
        return String.format(Config.getNewURL() + "api/photo/index?user=%s&group=%s&page=%s", userId, group, page);
    }

    /**
     * ��������id��ȡ��������
     *
     * @param arcticleId
     * @return
     */
    public static String getNewsDetail(String arcticleId) {
        return Config.getNewURL() + "api/news/" + arcticleId + "/";
    }

    public static String getDetailImage(String ArticleId) {
        return String.format(Config.getNewURL() + "article/images/%s", ArticleId);
    }

    public static String getNewsDetailUrl() {
        return Config.getNewURL() + "api/v1/system/config/?os=android";
    }

    //    public static String defaltUrl = "http://in.turboapp.xyz/api/v3/article/detail/";
    public static String getDetailContent(String ArticleId) {
        String urlsave = AccountManager.getInstance().getNewsDetailUrl();
        if (!Utils.isStringEmpty(urlsave)) {
//            TLog.e("detailurl",urlsave);
            return urlsave + ArticleId + "/";
        }
        return String.format(Config.getNewURL() + "api/v3/article/detail/%s/", ArticleId);
    }

    public static String getDefaltDetail() {
        return Config.getNewURL() + "api/v3/article/detail/";
    }

    public static String getUpdateVer() {
        return Config.getNewURL() + "api/v1/system/version/?os=android";
    }

    public static String getAreaName(double lat,double lng) {
        return String.format("https://maps.google.com/maps/api/geocode/json?latlng=%s,%s&language=es-mx&sensor=true",lat,lng);
    }


    /**
     * �ύ�û���Ϊ
     *
     * @param userid
     * @param itemId
     * @param rating
     * @return
     */
    public static String getUpdateUserRat(String userid, int itemId, String rating, String tablename, String objId) {
        return String.format(Config.getNewURL() + "api/push/update_user_rating/?user_id=%s&item_id=%s&rating=%s&tablename=%s&object_id=%s",
                userid, itemId, rating, tablename, objId);
    }

    public static String getAddRead() {
        return Config.getNewURL() + "api/v1/article/read/";
    }

    /**
     * �Ƿ��ղع�
     *
     * @return
     */
    public static String getIsCollect(String ArticleId, String Mid) {
        return String.format(Config.getNewURL() + "api/my/isCollection/?userid=%s&objectid=%s", Mid, ArticleId);
    }

    /**
     * �Ƿ�ͶƱ��
     *
     * @return
     */
    public static String getIsVote(String ArticleId, String Mid) {
        return String.format(Config.getNewURL() + "api/my/isVote/?userid=%s&objectid=%s", Mid, ArticleId);
    }


    /**
     * ��ȡread����
     *
     * @param pk
     * @return
     */
    public static String getReadDetail(String pk) {
        return Config.getNewURL() + "api/read/" + pk + "/";
    }

    /**
     * ��ȡȤζ����
     */
    public static String getInterestDetail(String pk) {
        return Config.getNewURL() + "api/interest/" + pk + "/";
    }

    /**
     * ��ȡ��Ƶ����
     */
    public static String getVideoDetail(String pk) {
//		http://119.254.98.65:8000/api/video/detail/571dc9d328b55643dfcc7ab5
        return Config.getNewURL() + "api/video/detail/" + pk;
    }

    /**
     * ��ȡ��������
     */
    public static String getPhotoDetail(String pk) {
        return Config.getNewURL() + "api/photo/" + pk + "/";
    }

    /**
     * ��ȡ������Ѷ�б�
     *
     * @param page
     * @return
     */
    public static String getFoucsList(int page) {
        return String.format(Config.getNewURL() + "api/news/foucs/?page=%s", page);
    }

    /**
     * ��ȡ������Ѷ�б�
     *
     * @param page
     * @return
     */
    public static String getArticlesCityList(String cityname, int page) {
        return String.format(Config.getNewURL() + "api/news/city/?city=%s&page=%s", cityname, page);
    }

    /**
     * 点赞
     *
     * @return
     */
    public static String getArticlesvote() {
        return Config.getNewURL() + "api/v2/article/vote/";
//		return Config.getNewURL() + "api/push/vote?";
    }

    /**
     * ��ȡ����б�??
     *
     * @param page
     * @return
     */
    public static String getAdList(int page) {
        return String.format(Config.getNewURL() + "api/ad/all/?page=%s", page);
    }

    /**
     * ���ݹ��id��ȡ�������??
     *
     * @return
     */
    public static String getAdDetail() {
        return Config.getNewURL() + "api/ad/pk?";
    }

    /**
     * ��ȡϵͳͼƬ
     */
    public static String getADImage() {
        return Config.getNewURL() + "api/system/getimages";
    }

    /**
     * ��ȡ����ID��ȡ�����б�
     */
    public static String getCommentListByAID(String articleId, int page, int pageSize) {
//		return String.format(Config.getNewURL() + "api/comments/?objectid=%s&page=%s", articleId,page);
        return String.format(Config.getNewURL() + "api/v1/comment/article/?article_id=%s&page=%s&pagesize=%s", articleId, page, pageSize);
    }

    /**
     * ��Ѷ��־
     */
    public static String getArticleLog() {
//		return Config.getNewURL() + "api/history/article/";
        return Config.getNewURL() + "api/my/v2/read/";
    }

    /**
     * ����Ѷ��־
     */
    public static String getReadLog() {
        return Config.getNewURL() + "api/history/nonews/";
    }

    /**
     * �ҵ��Ķ�����
     */
    public static String getMyReadNum(String userid) {
//		return String.format(Config.getNewURL() + "api/my/scan/?userid=%s", userid);
//		return String.format(Config.getNewURL() + "api/my/v2/readlist/%s/", userid);
        return String.format(Config.getNewURL() + "api/my/v2/readcount/%s/", userid);
    }

    /**
     * �ҵ���������
     */
    public static String getMyCommentNum(String userid) {
//		return String.format(Config.getNewURL() + "api/my/review/?userid=%s", userid);
//		return String.format(Config.getNewURL() + "api/my/v2/commentlist/%s/", userid);
        return String.format(Config.getNewURL() + "api/my/v2/commentcount/%s/", userid);
    }

    /**
     * �����Ķ���¼
     *
     * @param userId
     * @param tablename
     * @param page
     * @param pagesize
     * @param startdate
     * @param enddate
     * @return
     */
    public static String getMyReadNews(String userId, String tablename, int page, int pagesize, String startdate, String enddate) {
        return String.format(Config.getNewURL() + "api/my/v2/readbytablename/" + userId + "/?tablename=%s&page=%s&pagesize=%s&startdate=%s&enddate=%s", tablename, page, pagesize, startdate, enddate);
    }

    /**
     * �ҵ��Ķ�����ѯ�б�
     */
    public static String getMyReadZiXun(String userid) {
        return String.format(Config.getNewURL() + "api/my/nonewslist/?userid=%s", userid);
    }

    /**
     * �ҵ��Ķ����??
     */
    public static String getClearMyRead(String userid, String tablename) {
//		return Config.getNewURL()+"api/my/read/news/"+userid+"/";
        return String.format(Config.getNewURL() + "api/my/v2/readclear/%s/?tablename=%s",
                userid, tablename);

    }

    /**
     * ��������
     *
     * @return
     */
    public static String getSendComment() {
//		return Config.getNewURL() + "api/comments/add/";
//		return Config.getNewURL() + "api/v1/article/comment/";
        return Config.getNewURL() + "api/v2/article/comment/";

    }

    /**
     * �޸�����
     *
     * @return
     */
    public static String getUpdateComment() {
        return Config.getNewURL() + "api/comments/update/";
    }

    /**
     * ɾ������
     *
     * @return
     */
    public static String getDeleteComment(String id) {
        return String.format(Config.getNewURL() + "api/comments/delete/?id=%s", id);
    }

    /**
     * ����ͶƱ�ӿ�
     *
     * @return
     */
    public static String getcommentVote() {
//		return Config.getNewURL() + "api/comments/vote/?";
        return Config.getNewURL() + "api/v1/comment/vote/";
    }

    /**
     * �ؼ�������
     *
     * @return
     */
    public static String getSearchDetail() {
//        return Config.getNewURL() + "api/v1/search/v2/";
        return Config.getNewURL() + "api/v1/search/key/";

    }

    /**
     * ��Ѷ�۽����������棩
     *
     * @return
     */
    public static String getNewsJJ(int page) {
        return String.format(Config.getNewURL() + "api/news/jj?page=%s", page);
    }

    /**
     * ���������ȡ�ȴ�??
     *
     * @return
     */
    public static String getSearchHot(String tablename, int top) {
        return String.format(Config.getNewURL() + "api/search/gethotword?table_name=%s&top=%s", tablename, top);
    }

    /**
     * ���������ȡ�������?
     *
     * @return
     */
    public static String getSearchHistory(String tablename, String mid, int page) {
        return String.format(Config.getNewURL() + "api/search/getkeyword/?userid=%s&page=%s", mid, page);
    }

    /**
     * ������������������?
     *
     * @return
     */
    public static String getClearSearchHistory(String tablename, String mid) {
        return String.format(Config.getNewURL() + "api/search/clearkeyword/%s", mid);
    }

    /**
     * ����Ķ�??
     *
     * @return
     */
    public static String getRelaRead(String ArticleId, String tablename) {
//		return String.format(Config.getNewURL() + "api/search/relevant?id=%s&title=%s",ArticleId,keyWords);
        return String.format(Config.getNewURL() + "api/search/reading?id=%s&table_name=%s", ArticleId, tablename);
//		http://121.201.58.72:8000/api/search/reading/?id=5636473e5ff11b6bb10732b5&table_name=photo
    }


    /**
     * ��ȡȫ��Ƶ��
     *
     * @return
     */
    public static String getChannelList() {
        //return Config.getNewURL() + "api/channels/";
        return String.format(Config.getNewURL() + "api/v1/channel/");
    }

    /**
     * �����û��Ͱ���ȡƵ���б�
     *
     * @return
     */
    public static String getChannelListByUser() {

//        return String.format(Config.getNewURL() + "api/v3/channel/subscribe/?lang=%s&category=%s", SwitchLanguageUtils.getCurrentLanguage(),channetype);

        if (SwitchLanguageUtils.isHibyStr()) {
            return String.format(Config.getNewURL() + "api/v3/channel/subscribe/?category=stories&lang=" + SwitchLanguageUtils.getCurrentLanguage());
        } else {
            return String.format(Config.getNewURL() + "api/v3/channel/subscribe/?category=stories");
        }
    }
    public static String getVideoChannelListByUser() {
        return String.format(Config.getNewURL() + "api/v3/channel/subscribe/?category=video");
    }


    /**
     * ��ȡ�ҵ�����Ϣ�б�
     */
    public static String getMyNewMsg(String userId, int page) {
        return String.format(Config.getNewURL() + "api/my/newMessages/?user_id=%s", userId);
    }

    /**
     * ��ȡ�Ҳ鿴������Ϣ�б�
     */
    public static String getMyViewMsg(String userId, int page) {
        return String.format(Config.getNewURL() + "api/my/viewMessages/?user_id=%s", userId);
    }


    /**
     * ��ȡ�ҷ���������б�??
     */
    public static String getCommentListByUID(String userId, int pageIndex, int limit) {
//		return String.format(Config.getNewURL() + "api/v1/comment/limit=%s&offset=%s",
//				limit,pageIndex);
        return String.format(Config.getNewURL() + "api/v1/comment",
                limit, pageIndex);
    }

    /**
     * ��ȡ�Ҳ����ͶƱ�����б�??
     */
    public static String getMyVoteComments(String userId, int page) {
        return String.format(Config.getNewURL() + "api/my/voteComments?user_id=%s", userId);
    }

    /**
     * ��ȡ�ҵ��ղ�
     */
    public static String getMyCollectionList(int offset) {

//        return String.format(Config.getNewURL() + "api/v1/favorite/?limit=10&offset=%s", (offset - 1) * 10);
        return String.format(Config.getNewURL() + "api/v1/favorite/my/?limit=10&page=%s", offset);
    }

    /**
     * 删除收藏
     */
    public static String getDeleteCollection() {
//		return String.format(Config.getNewURL() + "api/v1/favorite/%s/",collectid);
        return String.format(Config.getNewURL() + "api/v2/favorite/delete/");
    }

    /**
     * ����ղ�??
     */
    public static String getClearCollection() {
//        return String.format(Config.getNewURL() + "api/v1/favorite/");
        return String.format(Config.getNewURL() + "api/v1/favorite/clear/");
    }

    /**
     * 添加收藏
     */
    public static String getAddCollection() {
        return Config.getNewURL() + "api/v2/article/favorite/";
    }


    /**
     * ������Ȥ
     *
     * @return
     */
    public static String getNotinterest() {
        return Config.getNewURL() + "api/v1/article/norinterest/";
    }

    public static String getFeedback() {
        return Config.getNewURL() + "api/feedback/";
    }

    /**
     * ��Ƶ�ۺ��б�
     */
    public static String getVideoList(String userid, int page) {
        return String.format(Config.getNewURL() + "api/video/index/%s/%s", userid, page);
    }

    public static String getGifList() {
        return String.format(Config.getNewURL() + "api/v3/article/images/");
    }

    public static String getUpdateAWS() {
        return String.format(Config.getNewURL() + "api/v1/auth/devicetoken/update/");
    }
    public static String getUpdatePlayId() {
        return String.format(Config.getNewURL() + "api/v1/auth/playerid/update/");
    }
    public static String getTestList() {
        return String.format(Config.getNewURL() + "api/test/article/push/");
    }

    /**
     * ��Ƶ��һ�б�
     */
    public static String getVideoToChannelList(String channelseq, int page) {
        return String.format(Config.getNewURL() + "api/video/channel/%s/%s", channelseq, page);
    }

    /**
     * 提现历史记录URL
     *
     * @return
     */
    public static String getWithdrawHistoryList(int page) {
        return String.format(Config.getNewURL() + "api/v1/withdrawals/list/?page=%s&limit=10", page);
    }

    /**
     * 积分充值记录URL
     *
     * @return
     */
    public static String getCreditsStatementList(int page) {
        return String.format(Config.getNewURL() + "api/v1/integral/list/?page=%s&limit=10", page);
    }

    /**
     * 积分规则的url
     *
     * @return
     */
    public static String getPointsPoliciesUrl() {
        return Config.getNewURL() + "rules";
    }

    /**
     * 提现URL
     *
     * @return
     */
    public static String getWithdrawUrl() {
        return Config.getNewURL() + "api/v1/withdrawals/apply/";
    }

    /**
     * 绑定银行卡发送验证码URL
     *
     * @return
     */
    public static String SendCode() {
        return Config.getNewURL() + "api/v1/member/send/email/code/";
    }

    /**
     * 绑定银行卡URL
     *
     * @return
     */
    public static String bindPayPal() {
        return Config.getNewURL() + "api/v1/member/paypal/bind/";
    }


}
