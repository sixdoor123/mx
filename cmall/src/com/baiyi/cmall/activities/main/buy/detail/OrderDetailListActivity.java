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
 * �������Ʒ�б�
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

	// ��Ʒ�б�����
	private List<OwmEntity> entities = null;
	private String result;

	/**
	 * ��ʼ������
	 */
	private void initData() {
		result = getIntent().getStringExtra(MallDefine.Result);
		entities = JsonParseBuy.getOrderList(result);
		if (Utils.isStringEmpty(entities)) {
			displayToast("��������");
			return;
		}
	}

	/**
	 * ������
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��Ʒ�嵥");
		win_title.addView(topTitleView);
	}

	private XRecyclerView mRecyclerView;
	private ProductOrderListAdapter<OwmEntity> adapter;

	private void initContent() {
		View listView = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_shop_bus, null);
		win_content.addView(listView);
		// ��ʼ���б�
		mRecyclerView = (XRecyclerView) listView.findViewById(R.id.recycleview);
		// ���ò��ֹ�����
		// GridLayoutManager manager = new GridLayoutManager(this, 3);
		LinearLayoutManager layout = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// ����Item���ӡ��Ƴ�����
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// ��ӷָ���
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
		adapter = new ProductOrderListAdapter(this);
		mRecyclerView.setAdapter(adapter);
		adapter.setDatas(entities);
		// ��Ŀ����¼�
		adapter.setItemClickListener(this);
	}

	/**
	 * ��Ŀ����¼�
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		
	}

}
