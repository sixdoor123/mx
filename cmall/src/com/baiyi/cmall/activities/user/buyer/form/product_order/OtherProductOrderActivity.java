package com.baiyi.cmall.activities.user.buyer.form.product_order;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.user.buyer.form.FormStateUtils;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ���ջ���������
 * 
 * @author lizl
 * 
 */
public class OtherProductOrderActivity extends
		BasePurProductOrderDetailActivity implements OnClickListener {

	// ȷ���ջ�
	private TextView mBtnQueRenForm;
	// ȡ��
	private TextView mBtnCancelForm;
	// ��Ո�˿�
	private TextView mBtnTuiKuanForm;
	// ɾ��
	private TextView mBtnDeleteForm;
	// ����
	private TextView mBtnShenSuForm;
	// ����״̬�ж�ֵ
	private String binaryValue;

	@Override
	public String getStateReminder() {
		return orderState;
	}

	@Override
	public int getImage() {
		return R.drawable.icon_transport;
	}

	@Override
	public void addFoot() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_other_orther, win_content);

		mBtnQueRenForm = (TextView) findViewById(R.id.btn_shou_huo);
		mBtnCancelForm = (TextView) findViewById(R.id.btn_cancel_form);
		mBtnTuiKuanForm = (TextView) findViewById(R.id.btn_tui_kuan);
		mBtnDeleteForm = (TextView) findViewById(R.id.btn_delete_form);
		mBtnShenSuForm = (TextView) findViewById(R.id.btn_shen_su);
		mBtnQueRenForm.setOnClickListener(this);
		mBtnCancelForm.setOnClickListener(this);
		mBtnTuiKuanForm.setOnClickListener(this);
		mBtnDeleteForm.setOnClickListener(this);
		mBtnShenSuForm.setOnClickListener(this);

		showView();

	}

	/**
	 * ����ָ��������״̬�ж���Щ��ť��ʾ
	 */
	private void showView() {

		// ��ȡ������״ֵ̬
		binaryValue = entity.getBinaryvalue();

		// �ջ���ť����ʾ���
		mBtnQueRenForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.SHOU_HUO_STATE));
		// ȡ��������ť����ʾ���
		mBtnCancelForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.CANCEL_STATE));
		// �˿�o����ʾ���
		mBtnTuiKuanForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.TUI_KUAN_STATE));
		// ɾ����ť����ʾ���
		mBtnDeleteForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.DELETE_STATE));
		// ���߰�ť����ʾ���
		mBtnShenSuForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.APPEAL_STATE));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_shou_huo:// ȷ���ջ�
			formOpration(AppNetUrl.getProductSureAcceptUrl(orderID));
			break;
		case R.id.btn_cancel_form:// ȡ��
			formOpration(AppNetUrl.getProductCancelOrderUrl(orderID));
			break;
		case R.id.btn_tui_kuan:// �����˿�
			formOpration(AppNetUrl.getProductTuiKuanUrl(orderID));
			break;
		case R.id.btn_delete_form:// ɾ��
			formOpration(AppNetUrl.getDeleteOrderUrl(orderID));
			break;
		case R.id.btn_shen_su:// ����
			formOpration(AppNetUrl.getShenSuOrderUrl(orderID));
			break;
		}
	}

}
