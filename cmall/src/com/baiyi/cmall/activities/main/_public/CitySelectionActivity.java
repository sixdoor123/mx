package com.baiyi.cmall.activities.main._public;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.adapter._public.CitySelectedAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.core.util.ContextUtil;

/**
 * 城市选择
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-25 下午4:07:59
 */
public class CitySelectionActivity extends BasePublicActivity implements OnItemClickListener {
	// 存放供应交割地查询条件的数据(省)
	private ArrayList<SelectedInfo> deliveryDatas;

	private Map<Integer, ArrayList<SelectedInfo>> maps = null;
	private int mapIndex = 0;

	@Override
	public CharSequence setTitleName() {
		return "城市选择";
	}

	// 传过来的字符串
	private String content;

	@SuppressLint("UseSparseArrays")
	@Override
	public void initData() {
		state = getIntent().getIntExtra("state", 1);
		content = getIntent().getStringExtra("temp");
		// 请选择在作怪
		if ("请选择".equals(content)) {
			content = "";
		}

		maps = new HashMap<Integer, ArrayList<SelectedInfo>>();
	}

	@SuppressWarnings("unused")
	private BaseCacheUtil cityCacheUtil = null;

	@Override
	protected void onDestroy() {
		cityCacheUtil = null;
		super.onDestroy();
		if (!Utils.isStringEmpty(deliveryDatas)) {
			deliveryDatas.clear();
			deliveryDatas = null;
			sourceInfo = null;
		}
	}

	/**
	 * 缓冲中有数据走此方法
	 */
	@Override
	public void initSubData(GoodsSourceInfo sourceInfo) {

		deliveryDatas = sourceInfo.getDeliveryDatas();
		maps.put(++mapIndex, deliveryDatas);
	}

	@SuppressWarnings("unused")
	private static final String ID = "1";

	/**
	 * 缓存中无数据走此方方法
	 */
	@Override
	public void jsonParse(Object result) {

		deliveryDatas = PublicActivityManager.getDeliveryData(result);

		maps.put(++mapIndex, deliveryDatas);
	}

	private PullToRefreshListView mLstProvince;
	// 适配器
	private CitySelectedAdapter adapter;
	// 显示地区的
	private TextView mTxtName;
	// 返回上一级
	private TextView tView;

	/**
	 * 内容
	 */
	@SuppressLint("InflateParams")
	@Override
	public void initView() {
		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_city, null);

		win_content.addView(view);

		mTxtName = (TextView) view.findViewById(R.id.txt_name);
		mTxtName.setText(content);
		
		LayoutParams params = new LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

		mLstProvince = new PullToRefreshListView(this);
		mLstProvince.setLayoutParams(params);
		
		// 设置不能下拉刷新
		mLstProvince.setMode(Mode.DISABLED);

		adapter = new CitySelectedAdapter(deliveryDatas, this);
		mLstProvince.setAdapter(adapter);
		mLstProvince.setOnItemClickListener(this);
		tView = new TextView(this);
		tView.setText("返回上一级");
		tView.setPadding(10, 30, 0, 30);
		tView.setEnabled(false);

		mLstProvince.getRefreshableView().addHeaderView(tView);

		win_content.addView(mLstProvince);

		tView.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
		
				mapIndex--;
				adapter.setData(maps.get(mapIndex));
				String[] strings = text.split(" ");
				List<String> arrayList = Arrays.asList(strings);
				List<String> list = new ArrayList(arrayList);

				String temp = "";

				if (list.size() - 1 > -1) {
					list.remove(list.size() - 1);
					for (int i = 0; i < list.size(); i++) {
						temp += list.get(i) + " ";
					}
				}
				mTxtName.setText(temp);
				text = temp;

				if (mapIndex <= 1) {
					tView.setEnabled(false);
					text = "";
					mTxtName.setText(text);
				}
			}
		});
	}

	/**
	 * 1:产地 2:交个地
	 */
	private int state = 1;

	/**
	 * 选择以后回去
	 * 
	 * @param info
	 */
	protected void callback() {

		Intent intent = new Intent();
		if (!TextUtils.isEmpty(text)) {
			intent.putExtra("text", text.trim());
		} else {
			intent.putExtra("text", content);
		}
		intent.putExtra("key", info);
		setResult(state, intent);
		finish();
	}

	private SelectedInfo info;
	// 当前控件要显示的文字
	private String text = "";
	// 保存上一次的集合
	@SuppressWarnings("unused")
	private ArrayList<SelectedInfo> lastInfos;
	@SuppressWarnings("unused")
	private boolean isFirst = true;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		++mapIndex;

		info = (SelectedInfo) parent.getItemAtPosition(position);
		if (null != info) {
			text += info.getCm_categoryname() + " ";
			mTxtName.setText(text);
			ArrayList<SelectedInfo> infos = info.getSonDatas();
			maps.put(mapIndex, infos);
			if (infos != null && infos.size() > 0) {
				adapter.setData(infos);
			} else {
				callback();
			}
		} else {
			callback();
		}
		tView.setEnabled(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_sure:// 去顶按钮按钮
			mTxtSure.setTextColor(Color.RED);
			callback();
			break;
		case R.id.ll_:
			finish();
			break;
		default:
			break;
		}
	}

}
