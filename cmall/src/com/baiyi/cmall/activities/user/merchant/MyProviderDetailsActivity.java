package com.baiyi.cmall.activities.user.merchant;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.provider.viewpager.ProviderDetailsViewPager;
import com.baiyi.cmall.activities.main.provider.viewpager.ProviderStandardArgumentViewPager;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pageview.MyProviderDetailsViewPager;
import com.baiyi.cmall.views.pageview.MyProviderStandardArgumentViewPager;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-�ɹ���Ϣ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 ����9:22:03
 */
public class MyProviderDetailsActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private GoodsSourceInfo info;
	private String token;
	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initData();
		initTitle();
		initRBContent();
		loadDatas();
	}

	/**
	 * ��������
	 */
	private void loadDatas() {
		
		final MyLoadingBar loadingBar = new MyLoadingBar(this);
		loadingBar.setProgressInfo("������,���Ժ�...");
		loadingBar.start();
		loadingBar.setPadding(0, getScreenHeight()/3, 0, 0);
		win_content.addView(loadingBar);

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getUserIntentionDetailsUrl(id));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", token);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {
			
			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				win_content.removeView(loadingBar);
				loadingBar.stop();
				finish();
			}
			
			@Override
			public void onCompelete(Object arg0, Object arg1) {
				info = MerchantCenterManager.getUserPurIntentionDetails(arg1);
				initViewPager();
				win_content.removeView(loadingBar);
				loadingBar.stop();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// ����ʵ����
	private String id;

	// ��Դ �� �ҵĹ�Ӧ����
	// 1 ��Դ����û�б༭
	// private int temp;

	/**
	 * ��ʼ������
	 */
	private void initData() {
		id = getIntent().getStringExtra("temp");
		token = CmallApplication.getUserInfo().getToken();
	}

	// �Զ��������
	protected EventTopTitleView topTitleView = null;

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ɹ�����");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {
			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {
				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);
			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {
			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state,
						MyProviderDetailsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	// ��Ӧ����
	private RadioButton mRbProviderDetails;
	// ����
	private RadioButton mRbProviderStandard;
	// RadioGroup
	private RadioGroup mRgCollecteGroup;
	// ��Ӧ�����µ�����
	private ImageView mImgDetailsChose;
	// �����µ�����
	private ImageView mImgStandardChose;

	// �Զ����ViewPager
	private MyViewPager mVpProvider;

	/**
	 * ��ʼ��RadioGroup������
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_collection_list, null);
		win_title.addView(view);

		mRgCollecteGroup = (RadioGroup) view.findViewById(R.id.collect_group);
		mRbProviderDetails = (RadioButton) view
				.findViewById(R.id.provider_collection);
		mRbProviderDetails.setText("�ɹ���Ϣ");

		mRbProviderStandard = (RadioButton) view
				.findViewById(R.id.purchase_collection);
		mRbProviderStandard.setText("����");

		mImgDetailsChose = (ImageView) view.findViewById(R.id.provider_choose);
		mImgStandardChose = (ImageView) view.findViewById(R.id.purchase_choose);

		mVpProvider = (MyViewPager) view.findViewById(R.id.vp_collection);

		mRbProviderDetails.setChecked(true);

		mRgCollecteGroup.setOnCheckedChangeListener(this);
	}

	// ��Ӧ����ViewPager
	private MyProviderDetailsViewPager detailsPager;
	// ���Ե�ViewPager
	private MyProviderStandardArgumentViewPager standardViewPager;
	// ���ViewPager�ļ���
	private ArrayList<PageView> pageViews;

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();
		detailsPager = new MyProviderDetailsViewPager(this, info);
		standardViewPager = new MyProviderStandardArgumentViewPager(this, info);

		pageViews.add(detailsPager);
		pageViews.add(standardViewPager);

		mVpProvider.init(pageViews, 0);
		mVpProvider.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				setButtonPerformClick(currentNum);
			}
		});
	}

	/**
	 * ����Button�ļ���
	 * 
	 * @param position
	 */
	private void setButtonPerformClick(int position) {
		if (position == 0) {
			mRbProviderDetails.performClick();
		} else if (position == 1) {
			mRbProviderStandard.performClick();
		}
	}

	/**
	 * RadioGroup �ļ����¼��Ļٵ�����
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.provider_collection) {
			id = 0;// ��Ӧ����
			mImgDetailsChose.setVisibility(View.VISIBLE);
			mImgStandardChose.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.purchase_collection) {
			id = 1;// ����
			mImgDetailsChose.setVisibility(View.INVISIBLE);
			mImgStandardChose.setVisibility(View.VISIBLE);
		}
		mVpProvider.setPageIndex(id);
		mVpProvider.setCurrentItem(id);

	}
}
