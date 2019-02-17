package com.baiyi.cmall.activities.main.buy.detail;

import java.util.List;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.buy.JsonParseBuy;
import com.baiyi.cmall.activities.main.buy.adapter.ProductOrderListAdapter;
import com.baiyi.cmall.activities.main.buy.adapter.ProductThumbnailAdapter;
import com.baiyi.cmall.activities.main.buy.entity.OwmEntity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

/**
 * 购买的商品列表
 * 
 * @author sunxy
 */
public class OrderDetailListActivity extends BaseActivity implements OnRecycleViewItemClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);

		initData();
		initTitle();
		initContent();
	}

	// 产品列表数据
	private List<OwmEntity> entities = null;
	private String result;

	/**
	 * 初始化数据
	 */
	private void initData() {
		result = getIntent().getStringExtra(MallDefine.Result);
		entities = JsonParseBuy.getOrderList(result);
		if (Utils.isStringEmpty(entities)) {
			displayToast("解析错误");
			return;
		}
	}

	/**
	 * 标题栏
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("商品清单");
		win_title.addView(topTitleView);
	}

	private XRecyclerView mRecyclerView;
	private ProductOrderListAdapter<OwmEntity> adapter;

	private void initContent() {
		View listView = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_shop_bus, null);
		win_content.addView(listView);
		// 初始化列表
		mRecyclerView = (XRecyclerView) listView.findViewById(R.id.recycleview);
		// 设置布局管理器
		// GridLayoutManager manager = new GridLayoutManager(this, 3);
		LinearLayoutManager layout = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// 设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		adapter = new ProductOrderListAdapter(this);
		mRecyclerView.setAdapter(adapter);
		adapter.setDatas(entities);
		// 条目点击事件
		adapter.setItemClickListener(this);
	}

	/**
	 * 条目点击事件
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		
	}

}
