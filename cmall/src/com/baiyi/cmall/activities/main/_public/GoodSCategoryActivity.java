package com.baiyi.cmall.activities.main._public;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyi.cmall.R;
import com.baiyi.cmall.adapter._public.CitySelectedAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.core.util.ContextUtil;

/**
 * 商品分类界面
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-17 下午2:47:44
 */
public class GoodSCategoryActivity extends BasePublicActivity implements
		OnItemClickListener {

	private String content;

	@Override
	public void initData() {
		content = getIntent().getStringExtra("temp");
		if ("请选择".equals(content)) {
			content = "";
		}
	}

	@Override
	public CharSequence setTitleName() {
		mLlSure.setVisibility(View.VISIBLE);
		return "分类选择";
	}

	private ArrayList<SelectedInfo> categoryDatas;

	/**
	 * 缓存中无数据
	 */
	@Override
	public void jsonParse(Object result) {
		categoryDatas = PublicActivityManager.getCategoryData(result);
	}

	/**
	 * 缓存中有数据执行
	 */
	@Override
	public void initSubData(GoodsSourceInfo sourceInfo) {
		categoryDatas = sourceInfo.getCategoryDatas();
		
//		if (null != result) {
//			categoryDatas = PublicActivityManager.getCategoryData(result);
//		}
	}

	private PullToRefreshListView mLstProvince;
	// 适配器
	private CitySelectedAdapter adapter;
	// 显示当前分类
	private TextView mTxtName;
	// 返回上一级
	private TextView tView;

	@SuppressLint("InflateParams")
	@Override
	public void initView() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_city, null);

		win_content.addView(view);

		mTxtName = (TextView) view.findViewById(R.id.txt_name);
		mTxtName.setText(content);

		mLstProvince = new PullToRefreshListView(this);
		LayoutParams params = new LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		mLstProvince.setLayoutParams(params);
		// 设置不能下拉刷新
		mLstProvince.setMode(Mode.DISABLED);

		adapter = new CitySelectedAdapter(categoryDatas, this);
		mLstProvince.setAdapter(adapter);
		mLstProvince.setOnItemClickListener(this);
		tView = new TextView(this);
		tView.setText("返回上一级");
		tView.setPadding(10, 30, 0, 30);
		tView.setEnabled(false);

		mLstProvince.getRefreshableView().addHeaderView(tView);

		win_content.addView(mLstProvince);

		tView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				adapter.setData(categoryDatas);
				tView.setEnabled(false);
				text = "";
				mTxtName.setText(content);

			}
		});
	}

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
			if (null != content) {
				intent.putExtra("text", content.trim());
			}
		}
		intent.putExtra("key", info);
		setResult(4, intent);
		finish();
	}

	private SelectedInfo info;
	// 当前控件要显示的文字
	private String text = "";
	// 保存上一次的集合
	@SuppressWarnings("unused")
	private ArrayList<SelectedInfo> lastInfos;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		info = (SelectedInfo) parent.getItemAtPosition(position);
		if (null != info) {
			text += info.getCm_categoryname() + " ";
			mTxtName.setText(text);
			ArrayList<SelectedInfo> infos = info.getSonDatas();

			// lastInfos.clear();
			// lastInfos = new ArrayList<SelectedInfo>();
			if (infos != null && infos.size() > 0) {
				// lastInfos.clear();
				// lastInfos.addAll(infos);
//				infos.remove(0);
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
