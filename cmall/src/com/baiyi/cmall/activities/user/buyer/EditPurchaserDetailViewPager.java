package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.AutoSelectView;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.AutoSelectView.AutoOnClickListener;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.DateSelectorView;
import com.baiyi.cmall.views.itemview.DateSelectorView.OnDateSelectedClickListener;
import com.baiyi.cmall.views.pageview.BaseEditMyPurchaseDetailViewPager;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * �༭�ɹ�����-�ɹ���ϢViewPager
 * 
 * @author lizl
 * 
 */
public class EditPurchaserDetailViewPager extends
		BaseEditMyPurchaseDetailViewPager implements OnClickListener {

	private View view;
	private EditText mEtPurName;// ��ُ����
	private TextView mTvClassify;// ����
	private TextView mTvVariety;// Ʒ��
	private TextView mTvPlace;// ����
	private EditText mEtWeight;// ����
	private EditText mEtPriceRange;// �۸�
	private EditText mEtPricePre;// Ԥ����
	private TextView mTvPubTime;// ����ʱ��
	private TextView mTvEndTime;// ����ʱ��
	private TextView mTvAutoStart;// �Զ���
	private TextView mTvAutoEnd;// �Զ���
	private TextView mTvDelivery;// �����
	private EditText mEtNeed;// �ɹ�����

	private TableLayout mTbrIsShow;// �ж��Ƿ���ʾ������ʱ��/�Զ���ʼѡ��

	/**
	 * ѡ��Ĳ���
	 */
	private TableRow mTrbPutTime;// ����ʱ��ѡ��
	private TableRow mTrbEndtime;// ����ʱ��ѡ��
	private TableRow mTrbAutoStart;// �Զ���ʼѡ��
	private TableRow mTrbAutoEnd;// �Զ�����ѡ��
	private TableRow mTrbDelivery;// �����ѡ��

	private String deliveryId;// �����ID

	public EditPurchaserDetailViewPager(Context context, int purID,
			boolean isShow) {

		super(context, purID, isShow);

		initView();
	}

	/**
	 * ��ʼ����ʾ�ؼ�
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_purchaser_details, null);
		mEtPurName = (EditText) view.findViewById(R.id.et_edit_pur_name);
		mTvClassify = (TextView) view.findViewById(R.id.tv_edit_classify);
		mTvVariety = (TextView) view.findViewById(R.id.tv_edit_variety);
		mTvPlace = (TextView) view.findViewById(R.id.tv_edit_place);
		mEtWeight = (EditText) view.findViewById(R.id.et_edit_weight);
		mEtPriceRange = (EditText) view.findViewById(R.id.tv_edit_price_range);
		mEtPricePre = (EditText) view.findViewById(R.id.tv_edit_price_pre);
		mTvPubTime = (TextView) view.findViewById(R.id.tv_edit_pub_time);
		mTvEndTime = (TextView) view.findViewById(R.id.tv_edit_end_time);
		mTvAutoStart = (TextView) view.findViewById(R.id.tv_edit_auto_start);
		mTvAutoEnd = (TextView) view.findViewById(R.id.tv_edit_auto_end);
		mTvDelivery = (TextView) view.findViewById(R.id.tv_edit_delivery_di);
		mEtNeed = (EditText) view.findViewById(R.id.et_edit_need);

		mTbrIsShow = (TableLayout) view.findViewById(R.id.lin_is_show);

		mTrbPutTime = (TableRow) view.findViewById(R.id.trb_pub_time);
		mTrbEndtime = (TableRow) view.findViewById(R.id.trb_end_time);
		mTrbAutoStart = (TableRow) view.findViewById(R.id.trb_auto_start);
		mTrbAutoEnd = (TableRow) view.findViewById(R.id.trb_auto_end);
		mTrbDelivery = (TableRow) view.findViewById(R.id.trb_delivery_di);

		mTrbPutTime.setOnClickListener(this);
		mTrbEndtime.setOnClickListener(this);
		mTrbAutoStart.setOnClickListener(this);
		mTrbAutoEnd.setOnClickListener(this);
		mTrbDelivery.setOnClickListener(this); 

		TextViewUtil.setText(R.id.purname, "����", view);
		TextViewUtil.setText(R.id.weight, "����", view);
		TextViewUtil.setText(R.id.price, "�۸�", view);
		TextViewUtil.setText(R.id.prepayment, "Ԥ�����", view);
		TextViewUtil.setText(R.id.delivery, "�����", view);
		TextViewUtil.setText(R.id.put_time, "����ʱ��", view);
		TextViewUtil.setText(R.id.end_time, "����ʱ��", view);
		TextViewUtil.setText(R.id.auto_start, "�����Զ���ʼ", view);
		TextViewUtil.setText(R.id.auto_end, "�����Զ�����", view);
	}

	/**
	 * ��ȡ������ɺ���ӡ�������
	 */
	@Override
	public void initContent() {

		layout.addView(view);

		setShow();
	}

	/**
	 * ��ʾ����
	 */
	private void setShow() {

		// ���ÿؼ�����
		mEtPurName.setText(info.getGoodSName());
		mTvClassify.setText(info.getGoodSCategory());
		mTvVariety.setText(info.getGoodSBrand());
		mTvPlace.setText(info.getGoodSPlace());
		mEtWeight.setText(info.getGoodSWeight() + "");

		String price = Utils.twoDecimals(info.getGoodSpriceInterval());
		String prepayMent = Utils.twoDecimals(info.getGoodSPrePrice());
		mEtPriceRange.setText(price);
		mEtPricePre.setText(prepayMent + "");
		mTvPubTime.setText(info.getGoodSPutTime() + "");
		mTvEndTime.setText(info.getGoodSEndTime() + "");
		mTvAutoStart.setText(info.isAutoStart() ? "��" : "��");
		mTvAutoEnd.setText(info.isAutoEnd() ? "��" : "��");
		mTvDelivery.setText(info.getGoodSDelivery());
		mEtNeed.setText(info.getGoodSPurchaseContent());
		// �״λ�ȡ����ص�ID
		deliveryId = info.getDeliverycityid();

		/*
		 * ���ݲ�ͬ״̬��ʾ���
		 */
		if (!isShow) {
			mTbrIsShow.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.trb_pub_time:
			selectTime("��ʼ����", 1);
			break;
		case R.id.trb_end_time:
			selectTime("��������", 2);
			break;
		case R.id.trb_auto_start:// �Զ���ʼʱ��
			String textStart = mTvAutoStart.getText().toString();
			autoSelect(1, textStart);
			break;
		case R.id.trb_auto_end:// �Զ�����ʱ��
			String textEnd = mTvAutoEnd.getText().toString();
			autoSelect(2, textEnd);
			break;
		case R.id.trb_delivery_di:// �����ѡ��

			Intent intent = new Intent(context, CitySelectionActivity.class);
			intent.putExtra("temp", mTvDelivery.getText().toString());
			((BaseActivity) context).startActivityForResult(intent,
					NumEntity.STATE_DEVALITE);
			break;

		}
	}

	/**
	 * �����Զ���ʼ/�Y��ѡ��
	 */
	private void autoSelect(final int state, String defaultText) {

		AutoSelectView autoSelectView = null;
		if (1 == state) {
			autoSelectView = new AutoSelectView(context, "�����Զ���ʼ", 2);
		} else {
			autoSelectView = new AutoSelectView(context, "�����Զ�����", 2);
		}
		// �ص�ѡ�������
		autoSelectView.setInfoOnClickListener(new AutoOnClickListener() {

			@Override
			public void onClickListener(String content) {

				if (1 == state) {
					mTvAutoStart.setText(content);
				} else {
					mTvAutoEnd.setText(content);
				}
			}
		});
		// ��ʾ
		autoSelectView.show();
		// ����Ĭ�ϵ�����Ϊѡ��״̬
		autoSelectView.setDefault(defaultText);

	}

	/**
	 * ��ʼ/��������
	 */
	private void selectTime(String title, final int state) {

		DateSelectorView view = new DateSelectorView(context, title);
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {

				if (1 == state) {

					mTvPubTime.setText(date);
				} else {

					mTvEndTime.setText(date);
				}

			}
		});
	}

	/**
	 * ������еĿؼ�
	 * 
	 * @return
	 */
	public ArrayList<TextView> getAllText() {

		ArrayList<TextView> textViews = new ArrayList<TextView>();
		textViews.add(mEtPurName);
		textViews.add(mEtWeight);
		textViews.add(mEtPriceRange);
		textViews.add(mEtPricePre);

		// �༭�ɹ���Ϣʱ���������ֶΣ�������༭ʱ��û���������ֶ�
		if (isShow) {
			textViews.add(mTvPubTime);
			textViews.add(mTvEndTime);
			textViews.add(mTvAutoStart);
			textViews.add(mTvAutoEnd);
		}

		textViews.add(mTvDelivery);
		textViews.add(mEtNeed);
		return textViews;
	}

	/**
	 * ���ؽ����ID��Ϣ
	 * 
	 * @return
	 */
	public String getDeliveryId() {
		return deliveryId;
	}

	/**
	 * ���ط���ʱ��
	 * 
	 * @return
	 */
	public String getPubTime() {
		return mTvPubTime.getText().toString();
	}

	/**
	 * ���ؽY��ʱ��
	 * 
	 * @return
	 */
	public String getEndTime() {
		return mTvEndTime.getText().toString();
	}

	/**
	 * ��ȡ�༭���µĽ���ص�ַ
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (null == data) {
			return;
		}
		// �����
		if (NumEntity.STATE_DEVALITE == requestCode) {

			SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");

			if (null != info) {

				deliveryId = info.getId();
				mTvDelivery.setText(data.getStringExtra("text"));
			}
		}
	}

	/**
	 * ���ñ༭�򲻿ɲ���
	 */
	public void setNoEdit() {
		mEtPurName.setEnabled(false);
		mEtWeight.setEnabled(false);
		mEtPriceRange.setEnabled(false);
		mEtPricePre.setEnabled(false);
		mTrbDelivery.setEnabled(false);
		mEtNeed.setEnabled(false);
	}

	/**
	 * ���ñ༭�򲻿ɲ���
	 */
	public void setPurNameNoEdit() {
		mEtPurName.setEnabled(false);
	}
}
