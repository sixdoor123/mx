package com.baiyi.cmall.activities.main.provider.pop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.pop.BasePopupWindow;
import com.baiyi.core.util.ContextUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 多级菜单的Pop
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-3 上午10:39:03
 */
@SuppressLint("UseSparseArrays")
public class MultiLevelPop extends BasePopupWindow {

	private ArrayList<SelectedInfo> firstInfos = null;
	private Context context = null;

	private Map<Integer, ScrollView> maps = new HashMap<Integer, ScrollView>();
	@SuppressLint("UseSparseArrays")
	private Map<Integer, MutlPopoupWindowAdapter> mapAdapters = //
			new HashMap<Integer, MutlPopoupWindowAdapter>();
	@SuppressLint("UseSparseArrays")
	private Map<Integer, String> codes = //
			new HashMap<Integer, String>();
	// 数据源
	Map<Integer, ArrayList<SelectedInfo>> datas = new HashMap<Integer, ArrayList<SelectedInfo>>();

	// 点击的当前的实体映射
	private Map<Integer, SelectedInfo> mapInfo = new HashMap<Integer, SelectedInfo>();

	public MultiLevelPop(View contentView, Context context, int width, int height, ArrayList<SelectedInfo> firstInfos,
			String searchInfo) {
		super(contentView, context, width, height);
		this.context = context;
		this.firstInfos = firstInfos;
		if (Utils.isStringEmpty(this.firstInfos)) {
			((BaseActivity) context).displayToast("数据null");
			dismiss();
			return;
		}
		datas.put(0, firstInfos);
		initView(contentView);
	}

	private LinearLayout rootView = null;

	private MutlPopoupWindowAdapter firstAdapter;

	private void initView(View contentView) {
		if (null == contentView) {
			if (this.rootView == null) {
				this.rootView = new LinearLayout(context);
				this.rootView.setOrientation(LinearLayout.HORIZONTAL);
				this.rootView.setLayoutParams(getParams(1));
			}
		} else {
			this.rootView = (LinearLayout) contentView;
			this.rootView.setOrientation(LinearLayout.HORIZONTAL);
			this.rootView.setLayoutParams(getParams(1));
		}

		createView(firstInfos);
	}

	private SelectedInfo info = null;

	@SuppressWarnings("unused")
	private void createView(ArrayList<SelectedInfo> infos) {
		ScrollView mScrollView = null;
		ListView firstLevel = null;
		//
		maps = getNewMaps();
		Log.d("TT", "maps.size()  =   " + maps.size());
		//
		View view1 = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.list_view_test, null);
		// firstLevel = new ListView(context);
		firstLevel = (ListView) view1.findViewById(R.id.listview);

		mScrollView = (ScrollView) view1.findViewById(R.id.scrollview);
		mScrollView.setLayoutParams(getParams(1));

		firstAdapter = new MutlPopoupWindowAdapter(context);

		firstLevel.setAdapter(firstAdapter);
		firstAdapter.setLeftData(infos);

		firstLevel.setLayoutParams(getListViewParams(firstLevel));

		firstLevel.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				info = (SelectedInfo) parent.getItemAtPosition(position);
				ArrayList<SelectedInfo> selectedInfos = null;
				if (null != info) {
					mapInfo.put(info.getFlag(), info);
					codes.put(info.getFlag(), info.getCm_categorycode());
					selectedInfos = info.getSonDatas();
					firstAdapter.setSelectId(info.getCm_categorycode());

					if (!Utils.isStringEmpty(selectedInfos)) {
						datas.put(info.getFlag(), selectedInfos);
					}

					createView(selectedInfos);

					if (itemOnclick != null) {
						itemOnclick.setPopListItemOnclick(position, mapInfo, info, true);
					}
				}

				if (info.getFlag() == NumEntity.FOUR || "全部".equals(info.getCm_categoryname())) {
					dismiss();
				}

				if (info.getFlag() != NumEntity.FOUR) {
				}
			}
		});

		rootView.removeAllViews();

		if (info == null) {
			mScrollView.setLayoutParams(getParams(1));
			rootView.addView(mScrollView);
			maps.put(0, mScrollView);
			mapAdapters.put(1, firstAdapter);
		} else {
			if (NumEntity.FOUR != info.getFlag()) {
				maps.put(info.getFlag(), mScrollView);
				mapAdapters.put(info.getFlag() + 1, firstAdapter);
			}
			int count = 0;
			Collection<ScrollView> v = maps.values();
			// Iterator<ScrollView> iterator = v.iterator();
			// while (iterator.hasNext()) {
			for (ScrollView view : v) {

				// ScrollView view = iterator.next();
				count++;
				if (info.getFlag() == NumEntity.FOUR) {
					view.setLayoutParams(getParams(info.getFlag()));
				} else {
					if (info.getFlag() == NumEntity.THIRD && "全部".equals(info.getCm_categoryname())) {
						view.setLayoutParams(getParams(info.getFlag()));
					} else {
						if (info.getFlag() == NumEntity.SECONED && "全部".equals(info.getCm_categoryname())) {
							view.setLayoutParams(getParams(info.getFlag()));
						} else {
							if (info.getFlag() == NumEntity.FIRST && "全部".equals(info.getCm_categoryname())) {
								view.setLayoutParams(getParams(info.getFlag()));
							} else {
								view.setLayoutParams(getParams(info.getFlag() + 1));
							}
						}
					}
				}
				if (null != info) {
					mapAdapters.get(count).setSelectId(codes.get(count));
				}
				view.setPadding(1, 0, 0, 0);
				if (view != null) {
					Log.d("TAG", "rootView = " + rootView.getChildCount());
					view.setFocusable(true);
					rootView.addView(view);
				}
			}

			count = 0;
		}
	}

	private Map<Integer, ScrollView> getNewMaps() {
		Map<Integer, ScrollView> mps = new HashMap<Integer, ScrollView>();
		Collection<ScrollView> values = maps.values();

		int count = 0;

		for (ScrollView scrollView : values) {
			if (count < info.getFlag()) {
				mps.put(count, maps.get(count));
			}
			count++;
		}
		// for (Integer integer : sets) {
		//
		// if (count < info.getFlag()) {
		// mps.put(integer, maps.get(integer));
		// }
		// count++;
		//
		// }

		return mps;
	}

	private LayoutParams getParams(int level) {
		LayoutParams params = new LayoutParams(//
				Config.getInstance().getScreenWidth(context) / level, //
				LayoutParams.MATCH_PARENT);
		return params;
	}

	private android.widget.FrameLayout.LayoutParams getListViewParams(ListView listView) {
		android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(
				//
				Config.getInstance().getScreenWidth(context), //
				MobileStateUtils.getTotalHeightofListView(firstAdapter, listView));
		return params;
	}

	private MultiPopListItemOnclick itemOnclick;

	public void setItemOnclick(MultiPopListItemOnclick itemOnclick) {
		this.itemOnclick = itemOnclick;
	}

	public interface MultiPopListItemOnclick {
		void setPopListItemOnclick(int position, Map<Integer, SelectedInfo> mapInfo, SelectedInfo childInfo,
				boolean parent);
	}

}
