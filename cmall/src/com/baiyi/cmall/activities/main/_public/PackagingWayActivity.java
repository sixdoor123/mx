package com.baiyi.cmall.activities.main._public;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyi.cmall.R;
import com.baiyi.cmall.adapter._public.PackFreightAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.core.util.ContextUtil;

/**
 * 包装方式
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-14 下午5:04:56
 */
public class PackagingWayActivity extends BasePublicActivity implements
		OnItemClickListener {
	@Override
	public void initData() {
	}

	@Override
	public CharSequence setTitleName() {
		// TODO Auto-generated method stub
		return "包装方式";
	}

	// 数据源
	private ArrayList<SelectedInfo> pachInfos;

	@Override
	public void jsonParse(Object result) {
//		sourceInfo = PublicActivityManager.getSelectedData(this, result);
//		pachInfos = sourceInfo.getPackageInfos();
		
		pachInfos = PublicActivityManager.getPackWayData(result);
	}

	@Override
	public void initSubData(GoodsSourceInfo sourceInfo) {
		pachInfos = sourceInfo.getYunInfos();
//		if (null != result) {
//			pachInfos = PublicActivityManager.getPackWayData(result);
//		}
	}

	private PullToRefreshListView listView;
	// 适配器
	private PackFreightAdapter adapter;

	@Override
	public void initView() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.list_have_divider1, null);

		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);
		adapter = new PackFreightAdapter(pachInfos, this);
		listView.setAdapter(adapter);
		listView.setMode(Mode.DISABLED);
		win_content.addView(view);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SelectedInfo entities = (SelectedInfo) parent
				.getItemAtPosition(position);
		Intent intent = new Intent();
		intent.putExtra("key", entities);
		setResult(9, intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_:
			finish();
			break;
		}
	}
}
