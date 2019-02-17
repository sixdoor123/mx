package com.baiyi.cmall.activities.main._public;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyi.cmall.adapter._public.BrandAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 品牌界面
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-17 下午2:47:44
 */
@SuppressLint("InflateParams")
public class GoodSBrandActivity extends BasePublicActivity implements
		OnItemClickListener {

	private ArrayList<BrandEntities> datas;

	@Override
	public CharSequence setTitleName() {
		return "品牌选择";
	}

	/**
	 * 缓存中无数据执行此方法
	 */
	@Override
	public void jsonParse(Object result) {
		datas = PublicActivityManager.getBrandData(result);
		// displayToast(datas.size() + "");
	}

	/**
	 * 缓冲中有数据走此方法
	 */
	@Override
	public void initSubData(GoodsSourceInfo sourceInfo) {
		datas = sourceInfo.getBrands();
	}

	private PullToRefreshListView listView;
	// 适配器
	private BrandAdapter adapter;

	/**
	 * 初始化界面
	 */
	@Override
	public void initView() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.list_have_divider, null);

		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);
		adapter = new BrandAdapter(datas, this);
		listView.setAdapter(adapter);
		listView.setMode(Mode.DISABLED);
		win_content.addView(view);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		BrandEntities entities = (BrandEntities) parent
				.getItemAtPosition(position);
		if (null != listener) {
			listener.onBtnPress(entities);
		}
		Intent intent = new Intent();
		intent.putExtra("key", entities);
		setResult(5, intent);
		finish();
	}

	private OnBtnPressClickListener listener;

	public void setListener(OnBtnPressClickListener listener) {
		this.listener = listener;
	}

	public interface OnBtnPressClickListener {
		void onBtnPress(BrandEntities entities);
	}

	@Override
	public void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_:
			finish();
			break;
		default:
			break;
		}
	}
}
