package com.baiyi.cmall.activities.user.buyer.form;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.pageviews.MyScrollViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;

/**
 * ֧������
 * 
 * @author lizl
 * 
 */
public class PayActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	private EventTopTitleView topTitleView;

	// ѡ���ܿؼ�
	private RadioGroup mRgGroup;
	// ����֧��
	private RadioButton mRbOnlinePay;
	// ����֧��
	private RadioButton mRbOfflinePay;
	// ����֧�������ͼ��
	private ImageView mImgOnline;
	// ����֧�������ͼ��
	private ImageView mImgOffline;

	private MyScrollViewPager myViewPager = null;

	private List<PageView> pageViews = null;
	// ����֧������
	private OnlinePayView onlinePayView = null;
	// ����֧������
	private OfflinePayView offlinePayView = null;
	// ��ʾ�������ݵ��ܿռ�
	private TableLayout mTabLayout;
	// ֧�������ʾ��ͼ��
	private ImageView mImgYue;
	// ���ƶ������ݵ���ʾ���
	private LinearLayout contraLayout;
	// ����������Ϣ��Ŀ
	private TextView mTvChanPinInfo, mTvLeiBie, mTvZhongLei, mTvDanJia,
			mTvShuLiang, mTvYuFu, mTvZongJia, mTvName, mTvDianHua, mTvChengShi,
			mTvLeiXing, mTvTaiTou, mTvNeiRong;

	// ����ID
	private String orderID;
	private OrderEntity orderEntity;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);

		initTitle();
		initData();
		initContent();
		initViewPager();

	}

	/**
	 * ��ʼ������
	 */
	private void initData() {

		orderEntity = (OrderEntity) getIntent().getSerializableExtra("tag");
		orderID = getIntent().getStringExtra("id");
		Log.d("TT", orderEntity.getTitle());
	}

	/**
	 * ��Ӷ���������
	 */
	private void initTitle() {

		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("֧����ʽ");
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
				MsgItemUtil.onclickPopItem(state, PayActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initContent() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_pay,
				win_content);

		contraLayout = (LinearLayout) findViewById(R.id.lin_contral);
		contraLayout.setOnClickListener(this);

		mTabLayout = (TableLayout) findViewById(R.id.tab_layout);
		mTabLayout.setVisibility(View.GONE);
		mImgYue = (ImageView) findViewById(R.id.img_yue);
		
		mTvChanPinInfo = (TextView) findViewById(R.id.tv_chanpin_info);
		mTvLeiBie = (TextView) findViewById(R.id.tv_lei_bie);
		mTvZhongLei = (TextView) findViewById(R.id.tv_zhong_lei);
		mTvDanJia = (TextView) findViewById(R.id.tv_dan_jia);
		mTvShuLiang = (TextView) findViewById(R.id.tv_shu_liang);
		mTvYuFu = (TextView) findViewById(R.id.tv_yu_fu);
		mTvZongJia = (TextView) findViewById(R.id.tv_zong_jia);
		mTvName = (TextView) findViewById(R.id.tv_name);
		mTvDianHua = (TextView) findViewById(R.id.tv_dian_hua);
		mTvChengShi = (TextView) findViewById(R.id.tv_cheng_shi);
		mTvLeiXing = (TextView) findViewById(R.id.tv_lei_xing);
		mTvTaiTou = (TextView) findViewById(R.id.tv_tai_tou);
		mTvNeiRong = (TextView) findViewById(R.id.tv_nei_rong);

		showFormData();
		myViewPager = (MyScrollViewPager) findViewById(R.id.vp_pager_pay);

		mImgOnline = (ImageView) findViewById(R.id.img_online);
		mImgOffline = (ImageView) findViewById(R.id.img_offline);

		mRgGroup = (RadioGroup) findViewById(R.id.rg_pay_way);
		mRgGroup.setOnCheckedChangeListener(this);

		mRbOnlinePay = (RadioButton) findViewById(R.id.rb_online_pay);
		mRbOfflinePay = (RadioButton) findViewById(R.id.rb_offline_pay);
		mRbOnlinePay.setChecked(true);

	}

	/**
	 * ��ʾ�̵��������
	 */
	private void showFormData() {
		
		mTvChanPinInfo.setText("");
		mTvLeiBie.setText(orderEntity.getCategoryName());
		mTvZhongLei.setText(orderEntity.getBrandname());
		mTvDanJia.setText(Utils.twoDecimals(orderEntity.getPrice()) + "Ԫ/��");
		mTvShuLiang.setText(orderEntity.getInventory() + "��");
		mTvYuFu.setText(Utils.twoDecimals(orderEntity.getPrepayment()) + "Ԫ");
		/*
		 * �ܶ�= ����*����
		 */
		String allMoney = (Double.parseDouble(orderEntity.getPrice()) * Long
				.parseLong(orderEntity.getInventory())) + "";
		mTvZongJia.setText(Utils.twoDecimals(allMoney) + "Ԫ");
		mTvName.setText(orderEntity.getReceivername());
		mTvDianHua.setText(orderEntity.getPhone());
		mTvChengShi.setText(orderEntity.getOrderCity());
		mTvLeiXing.setText(orderEntity.getInvoicetypename());
		mTvTaiTou.setText(orderEntity.getTitle());
		mTvNeiRong.setText(orderEntity.getContext());
	}

	/**
	 * ��ʼ��ViewPager
	 */
	private void initViewPager() {

		pageViews = new ArrayList<PageView>();

		onlinePayView = new OnlinePayView(this, orderID);
		offlinePayView = new OfflinePayView(this, orderID);

		pageViews.add(onlinePayView);
		pageViews.add(offlinePayView);

		myViewPager.init(pageViews, 0);

		/**
		 * ��viewPagerҳ��仯��ʱ��RadioButtonҲһ�����仯
		 * 
		 */
		myViewPager.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {

				setButtonPerformClick(currentNum);
			}
		});
	}

	public void setButtonPerformClick(int position) {
		if (position == 0) {
			mRbOnlinePay.performClick();

		} else if (position == 1) {
			mRbOfflinePay.performClick();
		}

	}

	boolean isShow = true;

	/**
	 * ����¼�
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.lin_contral:

			if (isShow) {
				mTabLayout.setVisibility(View.VISIBLE);
				mImgYue.setBackgroundResource(R.drawable.up_arrow);
				isShow = false;
			} else {
				mTabLayout.setVisibility(View.GONE);
				mImgYue.setBackgroundResource(R.drawable.down_arrow);
				isShow = true;
			}
			break;
		}
	}

	/**
	 * RadioGroup �ļ����¼��Ļص�����
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		int pagerIndex = 0;

		if (checkedId == R.id.rb_online_pay) {
			pagerIndex = 0;// ����
			mImgOnline.setVisibility(View.VISIBLE);
			mImgOffline.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_offline_pay) {
			pagerIndex = 1;// ����
			mImgOnline.setVisibility(View.INVISIBLE);
			mImgOffline.setVisibility(View.VISIBLE);
		}
		// ����radioButton�ĵ�����õ�ǰ��ҳ��λ��
		myViewPager.setPageIndex(pagerIndex);
		myViewPager.setCurrentItem(pagerIndex);

	}

}
