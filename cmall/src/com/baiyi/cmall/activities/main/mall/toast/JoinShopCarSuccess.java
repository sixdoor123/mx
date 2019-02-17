package com.baiyi.cmall.activities.main.mall.toast;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.JsonParseMall;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ���빺�ﳵ�ɹ�����ʧ��
 * 
 * @author sunxy
 */
public class JoinShopCarSuccess {

	// ���󷵻صĽ��
	private Object result = null;
	private Context context = null;
	private Toast toast = null;

	// ��ʾ
	private static final int Toast_Show = 0;
	// ȡ�� ��ʾ
	private static final int Toast_Cancel = 1;

	private RequestNetResultInfo info;

	public JoinShopCarSuccess(Context context, Object result) {
		this.context = context;
		this.result = result;

		info = JsonParseMall.getJoinShopCarResultInfo(result);
	}

	public void show() {
		handler.sendEmptyMessage(Toast_Show);
	}

	public void cancel() {
		handler.sendEmptyMessage(Toast_Cancel);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Toast_Show:
				joinShopCarSuccessDialog();
				break;
			case Toast_Cancel:
				joinShopCarCancelDialog();
				break;
			}
		}
	};

	/**
	 * ȡ��������
	 */
	private void joinShopCarCancelDialog() {
		if (null != toast) {
			toast.cancel();
		}
	}

	private ImageView mImgIcon = null;
	private TextView mTxtInfo;

	/**
	 * �ɹ�������
	 */
	protected void joinShopCarSuccessDialog() {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.join_shop_car_success, null);

		mImgIcon = (ImageView) view.findViewById(R.id.img_icon);
		mTxtInfo = (TextView) view.findViewById(R.id.txt_info);

		if (null != info) {
			if (info.getStatus() >= 1) {
				// �ɹ�
				mImgIcon.setBackgroundResource(R.drawable.complete_1);
				mTxtInfo.setText("��ϲ��,��Ʒ����������ﳵ");
			} else {
				// ʧ��
				mImgIcon.setVisibility(View.GONE);
				mTxtInfo.setText(info.getMsg());

				if ("���ȵ�¼".equals(info.getMsg())) {
					ExitLogin.getInstence(context).cleer();
				}
			}
		}

		toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(view);
		toast.show();
	}
}
