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
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.PrefUtils;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.XMLName;
import com.turbo.turbo.mexico.R;

/**
 * @author tangkun
 *
 */
public class NewsPager extends NewsBaseDataPager{
	
	private ThemeChangeCallBack themeChangeCallBack = null;

	/**
	 *
	 * @param context
	 * @param themeChangeCallBack
	 * @param imgArrow
     */
	public NewsPager(Context context, ThemeChangeCallBack themeChangeCallBack, ImageView imgArrow) {
		super(context, themeChangeCallBack,
				ArticleHistoryUtils.Tablename_News, 0, imgArrow, true);
		// TODO Auto-generated constructor stub
		this.themeChangeCallBack = themeChangeCallBack;
		updateInfo();
	}

	/**
	 *  title???????????
	 *  ??????????
	 */
	@Override
	public void onclickPopItem(int state, ViewGroup title) {
		
		if (pageChannelManagerGroup.getVisibility() == View.VISIBLE) {
			pageChannelManagerGroup.setVisibility(View.GONE);
		}
		
//		if (state == 0) {
//			UserInfoEntity user = CmsApplication.getUserInfoEntity();
//			if (user == null) {
//				AppUtils.loginRemind(getContext(), getResources()
//						.getString(R.string.tip_dialog_title), getResources()
//						.getString(R.string.tip_dialog_unlongin));
//				return;
//			}
//			IntentClassChangeUtils.startMyNews(getContext());
//		}if (state == (!isRecomment() ? 1 : -1)) {  // ????
//			IntentClassChangeUtils.startSearch(getContext());
//		}  else if (state == (isRecomment() ? 1 : 2)) { //???????
			
			setinVisibleChannel(false, false);

//			Intent intent = new Intent(getContext(), UsChannelAct.class);
//			Intent intent = new Intent(getContext(), ChannelChangeActivity.class);
//			intent.putExtra(Define.Channel_Type, ChannelDataUtils.Channel_News);
//			((BaseActivity)getContext()).startActivityForResult(intent, 1);

//		}else if(state == (isRecomment() ? 2 : 3))//???????
//		{
//			View view = ContextUtil.getLayoutInflater(getContext())
//					.inflate(R.layout.activity_page_setting, null);
//			MySetPop pop = new MySetPop(view, LayoutParams.WRAP_CONTENT,(BaseActivity)getContext());
//			pop.showPopupWindow(null, title,BaseActivity.getDensity_int(BasePopWindow.yoff));
//			pop.setThemeChangeCallBack(new PopThemeChangeCallBack() {
//
//				@Override
//				public void callback() {
//					if(themeChangeCallBack != null)
//					{
//						themeChangeCallBack.setThemeChangeCallBack();
//					}
//				}
//
//			});
//			pop.setFontChangeCallBack(new FontChangeCallBack() {
//
//				@Override
//				public void callback() {
//					refreshList();
////					ThemeUtil.setFontOrTheme(NewsPager.this, ThemeUtil.getAppTheme(), FontUtil.getFontSizeType());
//				}
//			});
//			pop.setAbstractChangeCallBack(new AbstractChangeCallBack() {
//
//				@Override
//				public void callback() {
//					refreshList();
//				}
//			});
//		}else if (state == 4) { // ????????????��???
//			// ???????��?
//			backToAllList(getTitleName());
//		}
		
		
	}

	@Override
	public String getTitleName() {
		return getResources().getString(R.string.title_news);
	}

	@Override
	public String getFirstTimeXmlName() {
		// TODO Auto-generated method stub
		return XMLName.XML_DAY_Article_First_Times;
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.activity.channelmanager.ChannelManager#getFeiYeCache()
	 */
	@Override
	public Cache getFeiYeCache() {
		// TODO Auto-generated method stub
		return CmsApplication.getFileFeiYeNewsCache(getContext());
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.activity.channelmanager.ChannelManager#isFY_FirstDayTimes()
	 */
	@Override
	public boolean isFY_FirstDayTimes() {
		// TODO Auto-generated method stub
		return PrefUtils.isFirstDayTimes(XMLName.XML_DAY_First_Times);
	}
	
	
	private void updateInfo() {

	}
}
