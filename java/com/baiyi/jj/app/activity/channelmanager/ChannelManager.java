package com.baiyi.jj.app.activity.channelmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baiyi.core.cache.Cache;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.utils.TLog;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseActivity.AnonymityLister;
import com.baiyi.jj.app.activity.main.LoadingBasePager;
import com.baiyi.jj.app.adapter.GridSpacingItemDecoration;
import com.baiyi.jj.app.adapter.MyRecyclerGridAdapter;
import com.baiyi.jj.app.adapter.MyRecyclerGridAdapter.OnItemClick;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;

import java.util.Comparator;
import java.util.List;

/**
 * Ƶ����ҳ
 *
 * @author tangkun
 *
 */
public abstract class ChannelManager extends LoadingBasePager {
	protected RelativeLayout pageChannelManagerGroup = null;

	/**
	 *  �µ�GridView
	 */
	private RecyclerView recyclerView = null;
	private MyRecyclerGridAdapter recyclerGridAdapter = null;

	//	private GridView channelGridView = null;
//	private ChannelManagerAdapter mAdapter = null;
	// ����ť
	private Button btnExit = null;
	private MyLoadingBar progressBar = null;
	// �������}
	private String tableName;
	// ��ҳ�Ƿ�Ϊ�Ƽ�����
	protected boolean isRecomment = true;
	// ��ҳ��Ƶ��
	protected OnListChanged listChanged = null;
//	// Ƶ������
//	private int channelgroup;

	// ���id
	private int TabId;

	// �������µļ�ͷ
	protected ImageView imgArrow;
	private int module;

	private LinearLayout linNoChannel;
	private Button btnAddChannel;

	/**
	 *
	 * @param context
	 * @param tableName
	 * @param TabId
	 * @param imgArrow
     */
	public ChannelManager(Context context, String tableName, int TabId,
						  ImageView imgArrow) {
		super(context);
		this.tableName = tableName;
		this.TabId = TabId;
//		this.channelgroup = DiscoveryPager.Group_ALL;
		this.imgArrow = imgArrow;
		// TODO Auto-generated constructor stub
		if (tableName.equals(ArticleHistoryUtils.Tablename_Discover)) {
			ContextUtil.getLayoutInflater(getContext()).inflate(
					R.layout.activity_discovery, this);
		} else {
			ContextUtil.getLayoutInflater(getContext()).inflate(
					R.layout.news_fragment, this);
		}
		setModule(tableName);

		pageChannelManagerGroup = (RelativeLayout) findViewById(R.id.page_channel_manager);

		progressBar = (MyLoadingBar) findViewById(R.id.channel_manager_loading);

		btnExit = (Button) findViewById(R.id.btn_exit);

		btnExit.setVisibility(View.VISIBLE);
//		edtSearch.setOnClickListener(new titleOnclick());
		btnExit.setOnClickListener(new ExitOnclick());

		initNoChannel();

		initRecycle();
	}

	private void  initNoChannel(){
		linNoChannel = (LinearLayout) findViewById(R.id.lin_shownochannel);
		btnAddChannel = (Button) findViewById(R.id.btn_addchannel);
		btnAddChannel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setVisibleChannelManager(View.GONE, false);
			}
		});
	}

	private void initRecycle() {


		recyclerView = (RecyclerView) findViewById(R.id.channer_recycler);
		recyclerGridAdapter = new MyRecyclerGridAdapter(getContext());
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

		recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, BaseActivity.getDensity_int(4), false));

		recyclerView.setAdapter(recyclerGridAdapter);
		recyclerGridAdapter.setItemClick(new OnItemClick() {

			@Override
			public void onClick(ChannelItem entity,int postion) {
				setVisibleChannelManager(View.GONE, false);
				if (entity == null) {
					setVisibleChannelManager(View.GONE, false);
				} else {
					setVisibleChannelManager(View.GONE, false);
					onItemClilck(entity);
				}
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (!isFY_FirstDayTimes()) {
//			setVisibleChannelManager(View.GONE);
			setinVisibleChannel(false, false);
			return;
		}
//		setVisibleChannelManager(View.VISIBLE);
		setinVisibleChannel(true, true);
	}

	public void setRecomment(boolean isRecomment) {
		this.isRecomment = isRecomment;
	}

	public boolean isRecomment() {
		return isRecomment;
	}

//	public void setChannelgroup(int channelgroup) {
//		this.channelgroup = channelgroup;
//	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		setModule(tableName);
	}

	public String getTableName() {
		return tableName;
	}

	// public static final String Tablename_News = "article";
	// public static final String Tablename_Read = "read";
	// public static final String Tablename_Interest = "interest";
	// public static final String Tablename_Photo = "photo";
	public void setModule(String tableName) {
		if (tableName.equals(ArticleHistoryUtils.Tablename_News)) {
			this.module = 1;
		} else if (tableName.equals(ArticleHistoryUtils.Tablename_Read)) {
			this.module = 2;
		} else if (tableName.equals(ArticleHistoryUtils.Tablename_Interest)) {
			this.module = 3;
		} else if (tableName.equals(ArticleHistoryUtils.Tablename_Video)) {
			this.module = 5;
		}else if (tableName.equals(ArticleHistoryUtils.Tablename_Photo)) {
			this.module = 4;
		} else {
			this.module = 1;
		}
	}

	public int getModule() {
		return module;
	}

	/**
	 * tab����
	 *
	 * @return
	 */
	public int getTabId() {
		return TabId;
	}

	protected void loadChannelData(final boolean isDisArtile) {
		linNoChannel.setVisibility(View.GONE);
		if(isVisibleManager())
		{
			startLoad();
		}
		((BaseActivity) getContext()).loadAnonymity(new AnonymityLister() {

			@Override
			public void setAnonymityLister(final UserInfoEntity entity) {
				if (entity == null){
					return;
				}
				ChannelDataManager.getInstance(getContext()).loadCacheChannel(ChannelDataManager.ChannelType_News, new ChannelDataManager.ChannelResultCallBack() {
					@Override
					public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
						stopLoad();

						if (Utils.isStringEmpty(userChannelList)){
							linNoChannel.setVisibility(View.VISIBLE);
							recyclerGridAdapter.clear();
							btnExit.setVisibility(View.GONE);
						}else{
							btnExit.setVisibility(View.VISIBLE);
						}

						if(isDisArtile)
						{
							loadArticleData(entity.getMID(), userChannelList);
						}
						if (recyclerGridAdapter.isDataChange(userChannelList)) {
							recyclerGridAdapter.clear();
							disChannelManger(userChannelList);
						}
					}
				});
			}
		});
	}

	private void disChannelManger(List<ChannelItem> userChannelList)
	{
		if(!isVisibleManager())
		{
			return;
		}

		stopLoad();
		if (Utils.isStringEmpty(userChannelList)) {
			return;
		}
//		Collections.sort(userChannelList, new ComparatorValues());
		// ChannelManagerEntity moreEntity = new
		// ChannelManagerEntity();
		// moreEntity.setChannel_name("����");
//		mAdapter.setData(userChannelList);
		ChannelItem item = new ChannelItem();
		item.setCid("-1");
		userChannelList.add(item);
		recyclerGridAdapter.setData(userChannelList);
	}

	public class ComparatorValues implements Comparator<ChannelItem> {

		@Override
		public int compare(ChannelItem object1,
						   ChannelItem object2) {
			int m1 = object1.getChannel_index();
			int m2 = object2.getChannel_index();
			int result = 0;
			if (m1 > m2) {
				result = 1;
			}
			if (m1 < m2) {
				result = -1;
			}
			return result;
		}

	}

	/**
	 * չ����ҳƵ��������ȡ��ҳ����
	 * �ж���Ч����
	 * @param visible
	 */
	public void setVisibleChannelManager(final int visible, boolean isDisArtile) {

		pageChannelManagerGroup.setVisibility(visible);
		if (visible == View.VISIBLE) {
//			if (channelGridView != null && mAdapter != null
//					&& !Utils.isStringEmpty(mAdapter.getData())) {
//				channelGridView.setSelection(0);
//			}
			if (recyclerView != null && recyclerGridAdapter != null
					&& !Utils.isStringEmpty(recyclerGridAdapter.getData())) {
				recyclerView.smoothScrollToPosition(0);
			}

			startLoad();
			startMove(pageChannelManagerGroup, true,isDisArtile);
//			loadChannelData(isDisArtile);
			imgArrow.setSelected(true);

		} else if (visible == View.GONE) {
			imgArrow.setSelected(false);
			startMove(pageChannelManagerGroup, false,isDisArtile);
		}

	}

	private void startLoad() {
		progressBar.start();
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgressInfo(getResources().getString(R.string.txt_progress_loading));

	}
	private void stopLoad() {
		progressBar.stop();
		progressBar.setVisibility(View.GONE);

	}

	/**
	 *  û�ж���Ч����
	 * @param isVisible
	 */
	public void setinVisibleChannel(boolean isVisible, boolean isDisArticle) {
		if (isVisible) {
			imgArrow.setSelected(true);
			pageChannelManagerGroup.setVisibility(View.VISIBLE);
			loadChannelData(isDisArticle);
			return;
		}
		imgArrow.setSelected(false);
		pageChannelManagerGroup.setVisibility(View.GONE);

	}

	/**
	 * ����Ч��
	 */
	private void startMove(View moveView, final boolean isEnter, final boolean isDisArtile) {
//		// �����ƶ�����
		float height = ((BaseActivity) getContext()).getScreenHeight();
		Animation moveAnimation = new TranslateAnimation(0, 0, -height+BaseActivity.getDensity_int(45), 0);
		if (!isEnter) {
			moveAnimation = new TranslateAnimation(0, 0, 0, -height);
		}
		// ����ʱ��
		moveAnimation.setDuration(500L);
		// ��������
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);// ����Ч��ִ����Ϻ�View����������ֹ��λ��


//		Animation mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_pop_enter);
//		if (!isEnter) {
//			mAnimation =  AnimationUtils.loadAnimation(getContext(), R.anim.anim_pop_exit);
//		}
		moveAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (isEnter) {
					loadChannelData(isDisArtile);
				}

			}
		});
//		moveView.startAnimation(mAnimation);

		moveAnimationSet.addAnimation(moveAnimation);
		moveView.startAnimation(moveAnimationSet);

	}

	/**
	 * �Ƿ���ʾ��ҳ
	 *
	 * @return
	 */
	public boolean isVisibleManager() {
		if (pageChannelManagerGroup == null) {
			return false;
		}
		return (pageChannelManagerGroup.getVisibility() == VISIBLE);
	}

//	private class titleOnclick implements View.OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			int id = v.getId();
//			if (id == R.id.edit_search) // ���������¼����������ת������ҳ��
//			{
//				IntentClassChangeUtils.startSearch(getContext());
//			}
//		}
//
//	}

//	/**
//	 * ���������
//	 */
//	private void inShowInput() {
//		InputMethodManager imm = (InputMethodManager) edtSearch.getContext()
//				.getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
//
//	}

	public abstract Cache getFeiYeCache();

	/**
	 * ��ҳ Ƶ������¼�
	 *
	 * @param entity
	 */
	public abstract void onItemClilck(ChannelItem entity);
	/**
	 * ÿ���һ�δ�ʱ����ʾ��ҳ
	 * @return
	 */
	public abstract boolean isFY_FirstDayTimes();

	//��ȡ�����б�
	public abstract void loadArticleData(String mid, List<ChannelItem> userChannelList);

	/**
	 * ����ť����¼����ر���ҳ
	 */
	private class ExitOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			setVisibleChannelManager(View.GONE, false);
		}

	}

	/**
	 * �ۺ��б�͵�һ�б����ı�Ľӿ�
	 */
	public interface OnListChanged {
		public void changeList(int pageIndex, String channelName, int visible);

	}
}