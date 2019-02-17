package com.baiyi.cmall.activities.user.merchant.provider.intention.viewpager;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.AutoSelectView;
import com.baiyi.cmall.activities.main._public.AutoSelectView.AutoOnClickListener;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.ProDataUtils;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.DateSelectorView;
import com.baiyi.cmall.views.itemview.DateSelectorView.OnDateSelectedClickListener;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-�༭��Ӧ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����2:24:25
 */
public class EditProviderIntentionDetailViewPager extends
		BeseEditIntentionProviderViewPager implements OnClickListener {

	// ״̬ ��Ϊ1 �ǣ����ǹ�Ӧ��-�ҵĹ�Ӧ����
	// ״̬ Ϊ1 �ǣ� ���ǹ�Ӧ��-��Ӧ�������
	// Ӱ��
	private boolean isProNameEdit;
	
	public EditProviderIntentionDetailViewPager(Context context, String id,
			int state, boolean isEditDetailEnable, boolean isProNameEdit) {
		super(context, id);
		this.state = state;
		this.isProNameEdit = isProNameEdit;
		this.isEditDetailEnable = isEditDetailEnable;
	}

	/**
	 * ����
	 */
	@Override
	public void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_provider_details, null);
		layout.addView(view);

		findViewById(view);
		updateEdtView();
	}

	// ����
	private EditText mEdtGoodSNam;
	// ���Ʒ������Ŀ
	// private TableRow mLlGoodSCategory;
	// ��ʾ�����
	private TextView mTxtCategory;
	// ����Ʒ���ֵ���Ŀ
	// private TableRow mLlGoodSBrand;
	// ��ʾƷ��
	private TextView mTxtBrand;
	// ���Ʋ��ص���Ŀ
	// private TableRow mLlConductPlace;
	// ��ʾ����
	private TextView mTxtPlace;
	// ���� ���
	private EditText mEdtGoodSInventory;
	// �۸�
	private EditText mEdtGoodSPric;
	// Ԥ�����
	private EditText mEdtGoodSPrepayment;
	// ����ʱ��
	private TableRow mLlGoodSPutTime;
	// ��ʾ����ʱ��
	private TextView mTxtPutTime;
	// ����ʱ��
	private TableRow mLlGoodSEndTime;
	// ��ʾ����ʱ��
	private TextView mTxtEndTime;
	// ���Ƶ����Զ���
	private TableRow mLlAutoStart;
	// ��ʾ�Ƿ��ƶ���
	private TextView mTxtStart;
	// ���Ƶ����Զ�����
	private TableRow mLlAutoEnd;
	// ��ʾ�Ƿ��Զ���
	private TextView mTxtOver;
	// ���ƽ���ص���Ŀ
	private TableRow mLlGoodSDelivery;
	// ��ʾ�����
	private TextView mTxtDelivery;
	// ��ϸ����
	private EditText mTxtDetails;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {

		// ����
		mEdtGoodSNam = (EditText) view.findViewById(R.id.edt_goods_name_01);
		// ��ʾ�����
		mTxtCategory = (TextView) view.findViewById(R.id.txt_catagory);
		// ��ʾƷ��
		mTxtBrand = (TextView) view.findViewById(R.id.txt_brand);
		// ��ʾ����
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		// ���� ���
		mEdtGoodSInventory = (EditText) view
				.findViewById(R.id.edt_goods_inventory);
		// �۸�
		mEdtGoodSPric = (EditText) view.findViewById(R.id.edt_goods_price);
		// Ԥ�����
		mEdtGoodSPrepayment = (EditText) view
				.findViewById(R.id.edt_goods_prepayment);
		// ����ʱ��
		mLlGoodSPutTime = (TableRow) view
				.findViewById(R.id.ll_conduct_put_time);
		// ��ʾ����ʱ��
		mTxtPutTime = (TextView) view.findViewById(R.id.txt_put_time);
		// ����ʱ��
		mLlGoodSEndTime = (TableRow) view
				.findViewById(R.id.ll_conduct_end_time);
		// ��ʾ����ʱ��
		mTxtEndTime = (TextView) view.findViewById(R.id.txt_end_time);
		// ���Ƶ����Զ���
		mLlAutoStart = (TableRow) view.findViewById(R.id.ll_conduct_auto_start);
		// ��ʾ�Ƿ��ƶ���
		mTxtStart = (TextView) view.findViewById(R.id.txt_auto_start);
		// ���Ƶ����Զ�����
		mLlAutoEnd = (TableRow) view.findViewById(R.id.ll_conduct_auto_over);
		// ��ʾ�Ƿ��Զ���
		mTxtOver = (TextView) view.findViewById(R.id.txt_auto_over);
		// ���ƽ���ص���Ŀ
		if (1 == state) {
			mLlGoodSPutTime.setVisibility(View.GONE);
			mLlGoodSEndTime.setVisibility(View.GONE);
			mLlAutoStart.setVisibility(View.GONE);
			mLlAutoEnd.setVisibility(View.GONE);
			view.findViewById(R.id.line_01).setVisibility(View.GONE);
			view.findViewById(R.id.line_02).setVisibility(View.GONE);
			view.findViewById(R.id.line_03).setVisibility(View.GONE);
			view.findViewById(R.id.line_04).setVisibility(View.GONE);
		}

		mLlGoodSDelivery = (TableRow) view
				.findViewById(R.id.ll_conduct_delivery);
		// ��ʾ�����
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		// ��ϸ����
		mTxtDetails = (EditText) view.findViewById(R.id.txt_details_01);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		mLlGoodSPutTime.setOnClickListener(this);
		mLlGoodSEndTime.setOnClickListener(this);
		mLlAutoStart.setOnClickListener(this);
		mLlAutoEnd.setOnClickListener(this);
		mLlGoodSDelivery.setOnClickListener(this);

		TextViewUtil.setText(R.id.pro_name, "����", view);
		TextViewUtil.setText(R.id.invo, "���", view);
		TextViewUtil.setText(R.id.price, "�۸�", view);
		TextViewUtil.setText(R.id.prepayment, "Ԥ�����", view);
		TextViewUtil.setText(R.id.delivery, "�����", view);
		TextViewUtil.setText(R.id.put_time, "����ʱ��", view);
		TextViewUtil.setText(R.id.end_time, "����ʱ��", view);
		TextViewUtil.setText(R.id.atuo_start, "�����Զ���", view);
		TextViewUtil.setText(R.id.auto_end, "�����Զ���", view);

		// �̵��Ƿ�ɲ���
		if (!isEditDetailEnable) {
			orperationInput();
		}

		if (state != 9) {
			if (!isProNameEdit) {
				mEdtGoodSNam.setEnabled(false);
			}
		}

		mTxtDetails.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// ��仰˵����˼���߸�View���Լ����¼����Լ�����
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
	}

	/**
	 * �ж��Ƿ�ɲ��� ���ɲ������
	 */
	private void orperationInput() {
		mEdtGoodSInventory.setEnabled(false);
		mEdtGoodSNam.setEnabled(false);
		mEdtGoodSPrepayment.setEnabled(false);
		mEdtGoodSPric.setEnabled(false);
		mTxtDetails.setEnabled(false);

		mLlGoodSDelivery.setEnabled(false);
		mLlAutoEnd.setEnabled(false);
		mLlAutoStart.setEnabled(false);

	}

	// ��¼��ʼʱ��
	private String startTime;
	// ��¼����ʱ��
	private String endTime;

	/**
	 * ����������е�����
	 */
	private void updateEdtView() {
		if (null != info) {
			endTime = info.getGoodSEndTime();
			startTime = info.getGoodSPutTime();
			// ����
			mEdtGoodSNam.setText(info.getGoodSName());
			// ��ʾ�����
			mTxtCategory.setText(info.getGoodSCategory());
			// ��ʾƷ��
			mTxtBrand.setText(info.getGoodSBrand());
			// ��ʾ����
			mTxtPlace.setText(info.getGoodSPlace());
			// ���� ���
			mEdtGoodSInventory.setText(info.getGoodSInventory());
			// �۸�
			String prioce = Utils.twoDecimals(info.getGoodSPrePrice());
			if (prioce.startsWith(".")) {
				prioce = "0" + prioce;
			}
			mEdtGoodSPric.setText(prioce);
			// Ԥ�����
			String prepayMent = Utils.twoDecimals(info.getPrepayment().equals(
					"null") ? String.valueOf(0) : String.valueOf(info
					.getPrepayment()));
			if (prepayMent.startsWith(".")) {
				prepayMent = "0" + prepayMent;
			}
			mEdtGoodSPrepayment.setText(prepayMent);
			// ��ʾ����ʱ��
			mTxtPutTime.setText(info.getGoodSPutTime());
			// ��ʾ����ʱ��
			mTxtEndTime.setText(info.getGoodSEndTime());
			// ��ʾ�Ƿ��ƶ���
			mTxtStart.setText(info.isAutoStart() ? "��" : "��");
			// ��ʾ�Ƿ��Զ���
			mTxtOver.setText(info.isAutoEnd() ? "��" : "��");
			// ��ʾ�����
			mTxtDelivery.setText(info.getGoodSDelivery());
			// ��ϸ����
			mTxtDetails.setText(info.getGoodSDetails());
		}
	}

	// �޸ĵ���Ϣ
	// private GoodsSourceInfo sourceInfo;

	/**
	 * ��ȡ�������Ʒ����Ϣ ��װ��ʵ����
	 * 
	 * @return
	 */
	@Override
	public boolean getInputGoodSInfo() {
		String proName = mEdtGoodSNam.getText().toString().trim();
		if (TextUtils.isEmpty(proName)) {
			((BaseActivity)(context)).displayToast("���Ʋ���Ϊ��");
			return false;
		}
		if (!Utils.isExceed_30(proName)) {
			((BaseActivity)(context)).displayToast("������д����");
			return false;
		}
		
		String inventory = mEdtGoodSInventory.getText().toString().trim();
		if (!"OK".equals(HintUtils.weightHint(inventory))) {
			((BaseActivity) context).displayToast(HintUtils
					.weightHint(inventory));
			return false;
		}
		if (!Utils.isExceed_7(inventory)) {
			((BaseActivity) context).displayToast("�����д����");
			return false;
		}
		String price = mEdtGoodSPric.getText().toString().trim();

		if (!"OK".equals(HintUtils.priceHint(price))) {
			((BaseActivity) context).displayToast(HintUtils.priceHint(price));
			return false;
		}
		if (!Utils.isExceed_8(price)) {
			((BaseActivity) context).displayToast("�۸���д����");
			return false;
		}

		String prepayment = mEdtGoodSPrepayment.getText().toString().trim();
		if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
			((BaseActivity) context).displayToast(HintUtils
					.prePriceHint(prepayment));
			return false;
		}
		if (!Utils.isExceed_10(prepayment)) {
			((BaseActivity) context).displayToast("�۸���д����");
			return false;
		}

		if (!Utils.isExceed_1000(mTxtDetails.getText().toString().trim())) {
			((BaseActivity) context).displayToast("��ϸ������д����");
			return false;
		}

		ProDataUtils.setIndex(0);
		ProDataUtils.setDetails(mEdtGoodSNam);
		ProDataUtils.setDetails(mTxtCategory);
		ProDataUtils.setDetails(mTxtBrand);
		ProDataUtils.setDetails(mTxtPlace);
		ProDataUtils.setDetails(mEdtGoodSInventory);
		ProDataUtils.setDetails(mEdtGoodSPric);
		ProDataUtils.setDetails(mEdtGoodSPrepayment);
		ProDataUtils.setDetails(mTxtPutTime);
		ProDataUtils.setDetails(mTxtEndTime);
		ProDataUtils.setDetails(mTxtStart);
		ProDataUtils.setDetails(mTxtOver);
		ProDataUtils.setDetails(mTxtDelivery);
		ProDataUtils.setDetails(mTxtDetails);
		// �̼�����
		String merchaseName = mEdtGoodSNam.getText().toString().trim();
		if (TextUtils.isEmpty(merchaseName)) {
			((BaseActivity) context).displayToast("���Ʋ���Ϊ��");
			return false;
		}

		/*
		 * �жϿ��
		 */
		// String intvetory =
		// Utils.getNumberOfString(mEdtGoodSInventory.getText()
		// .toString());
		// if (!"OK".equals(HintUtils.inventoryHint(intvetory))) {
		// ((BaseActivity) context).displayToast(HintUtils
		// .inventoryHint(intvetory));
		// return false;
		// }
		/*
		 * �жϼ۸�-----------------------------------
		 */
		// String price = Utils.getNumberOfString(mEdtGoodSPric.getText()
		// .toString());
		//
		// if (!"OK".equals(HintUtils.priceHint(price))) {
		// ((BaseActivity) context).displayToast(HintUtils.priceHint(price));
		// return false;
		// }

		/*
		 * �ж�Ԥ�����-----------------------------------
		 */
		// String prepayment = Utils.getNumberOfString(mEdtGoodSPrepayment
		// .getText().toString());
		//
		// if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
		// ((BaseActivity) context).displayToast(HintUtils
		// .prePriceHint(prepayment));
		// return false;
		// }
		if (1 != state) {
			startTime = mTxtPutTime.getText().toString().trim();
			endTime = mTxtEndTime.getText().toString().trim();
			String result = HintUtils.timeHint(startTime, endTime);
			if (!"OK".equals(result)) {
				((BaseActivity)context).displayToast(result);
				return false;
			}
		}
		// ��ϸ��Ϣ
		String detail = mTxtDetails.getText().toString().trim();

		// sourceInfo.setId(id);
		// sourceInfo = new GoodsSourceInfo();
		// sourceInfo.setId(info.getGoodSID());
		// sourceInfo.setGoodSID("123");
		// sourceInfo.setGoodSName(merchaseName);
		// sourceInfo.setGoodSWeight(intvetory);
		// sourceInfo.setGoodSPrePrice(Float.parseFloat(price));
		// sourceInfo.setGoodSPutTime(mTxtPutTime.getText().toString().trim());
		// sourceInfo.setGoodSDelivery(content);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_conduct_delivery:// �����
			((BaseActivity) context).goActivity(mTxtDelivery.getText()
					.toString().trim(), CitySelectionActivity.class);
			break;
		case R.id.ll_conduct_end_time:// ����ʱ��
			endTime();
			break;
		case R.id.ll_conduct_put_time:// ����ʱ��
			startTime();
			break;
		case R.id.ll_conduct_auto_start:// �Զ���ʼ
			String textStart = mTxtStart.getText().toString();
			autoStartOrEnd(1, textStart);
			break;
		case R.id.ll_conduct_auto_over:// �Զ�����
			String textEnd = mTxtOver.getText().toString();
			autoStartOrEnd(2, textEnd);
			break;
		}
	}

	private void autoStartOrEnd(final int state, String defaultText) {

		AutoSelectView view = null;

		if (1 == state) {
			view = new AutoSelectView(context, "�����Զ���ʼ", 2);
		} else if (2 == state) {
			view = new AutoSelectView(context, "�����Զ�����", 2);
		}

		view.setInfoOnClickListener(new AutoOnClickListener() {

			@Override
			public void onClickListener(String content) {
				if (1 == state) {
					autoStart = content;
					mTxtStart.setText(content);
				} else if (2 == state) {
					autoEnd = content;
					mTxtOver.setText(content);
				}
			}
		});
		view.show();
		view.setDefault(defaultText);
	}

	/**
	 * ��������
	 */
	private void endTime() {
		DateSelectorView view = new DateSelectorView(context, "��������");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// ((BaseActivity) context).displayToast("����ʱ��" + date);
				endTime = date;
				// long start = com.baiyi.dmall.utils.Utils
				// .getLongTime1(startTime);
				// long end = com.baiyi.dmall.utils.Utils.getLongTime1(date);
				// if (start > end) {
				// ((BaseActivity) context).displayToast("����ʱ�䲻��С�ڿ�ʼʱ��");
				// return;
				// }

				mTxtEndTime.setText(date);
			}
		});
	}

	/**
	 * ��ʼ����
	 */
	private void startTime() {
		DateSelectorView view = new DateSelectorView(context, "��ʼ����");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// ((BaseActivity) context).displayToast("��ʼʱ��" + date);
				startTime = date;

				mTxtPutTime.setText(date);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			countryInfo = (SelectedInfo) data.getSerializableExtra("key");
			content = data.getStringExtra("text");
			mTxtDelivery.setText(content);
		}
	}
}
