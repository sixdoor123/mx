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
 * 代收货订单界面
 * 
 * @author lizl
 * 
 */
public class OtherProductOrderActivity extends
		BasePurProductOrderDetailActivity implements OnClickListener {

	// 确认收货
	private TextView mBtnQueRenForm;
	// 取消
	private TextView mBtnCancelForm;
	// 申請退款
	private TextView mBtnTuiKuanForm;
	// 删除
	private TextView mBtnDeleteForm;
	// 申诉
	private TextView mBtnShenSuForm;
	// 订单状态判断值
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
	 * 根据指定的数据状态判断哪些按钮显示
	 */
	private void showView() {

		// 获取二进制状态值
		binaryValue = entity.getBinaryvalue();

		// 收货按钮的显示与否
		mBtnQueRenForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.SHOU_HUO_STATE));
		// 取消订单按钮的显示与否
		mBtnCancelForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.CANCEL_STATE));
		// 退款按鈕的显示与否
		mBtnTuiKuanForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.TUI_KUAN_STATE));
		// 删除按钮的显示与否
		mBtnDeleteForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.DELETE_STATE));
		// 申诉按钮的显示与否
		mBtnShenSuForm.setVisibility(FormStateUtils.isShow(binaryValue,
				FormStateUtils.APPEAL_STATE));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_shou_huo:// 确认收货
			formOpration(AppNetUrl.getProductSureAcceptUrl(orderID));
			break;
		case R.id.btn_cancel_form:// 取消
			formOpration(AppNetUrl.getProductCancelOrderUrl(orderID));
			break;
		case R.id.btn_tui_kuan:// 申请退款
			formOpration(AppNetUrl.getProductTuiKuanUrl(orderID));
			break;
		case R.id.btn_delete_form:// 删除
			formOpration(AppNetUrl.getDeleteOrderUrl(orderID));
			break;
		case R.id.btn_shen_su:// 申诉
			formOpration(AppNetUrl.getShenSuOrderUrl(orderID));
			break;
		}
	}

}
