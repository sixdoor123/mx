/**
 *
 */
package com.baiyi.jj.app.activity.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baiyi.core.cache.Cache;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.PrefUtils;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.XMLName;

/**
 * Tab-֪ʶ��ҳ
 *
 * @author tangkun
 */
public class InformationPager extends NewsBaseDataPager {
    private ThemeChangeCallBack themeChangeCallBack = null;

    /**
     * @param context
     * @param themeChangeCallBack
     * @param imgArrow
     */
    public InformationPager(Context context,
                            ThemeChangeCallBack themeChangeCallBack, ImageView imgArrow) {
        super(context, themeChangeCallBack, ArticleHistoryUtils.Tablename_Read,
                1, imgArrow, true);
        // TODO Auto-generated constructor stub
        this.themeChangeCallBack = themeChangeCallBack;
    }

    @Override
    public String getTitleName() {
        // TODO Auto-generated method stub
        return getResources().getString(R.string.title_explore);
    }

    /* (non-Javadoc)
     * @see com.baiyi.jj.app.activity.main.LoadingBasePager#onclickPopItem(int, android.view.ViewGroup)
     */
    @Override
    public void onclickPopItem(int state, ViewGroup title) {
        if (pageChannelManagerGroup.getVisibility() == View.VISIBLE) {
            pageChannelManagerGroup.setVisibility(View.GONE);
        }


    }

    @Override
    public String getFirstTimeXmlName() {
        // TODO Auto-generated method stub
        return XMLName.XML_DAY_Information_First_Times;
    }

    /* (non-Javadoc)
     * @see com.baiyi.jj.app.activity.channelmanager.ChannelManager#getFeiYeCache()
     */
    @Override
    public Cache getFeiYeCache() {
        // TODO Auto-generated method stub
        return CmsApplication.getFileFeiYeInformationCache(getContext());
    }

    /* (non-Javadoc)
     * @see com.baiyi.jj.app.activity.channelmanager.ChannelManager#isFY_FirstDayTimes()
     */
    @Override
    public boolean isFY_FirstDayTimes() {
        // TODO Auto-generated method stub
        return PrefUtils.isFirstDayTimes(XMLName.XML_DAY_knows_Times);
    }

//	/* (non-Javadoc)
//	 * @see com.baiyi.jj.app.activity.main.NewsBasePager#getPostData(boolean, boolean, java.lang.String, com.baiyi.jj.app.entity.article.ArticleEntity)
//	 */
//	@Override
//	public String getPostData(boolean isRecomment, boolean isDuanzi,
//			String mid, ArticleEntity articleEntity, List<ChannelItem> userChannelList, String channelId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
