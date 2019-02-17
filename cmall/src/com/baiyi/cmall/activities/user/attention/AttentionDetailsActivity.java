package com.baiyi.cmall.activities.user.attention;

import java.util.ArrayList;

import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.delegation.EditDelegationPurchaseDetailsActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.DelegationManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.manager.UserLogisticsManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ��ע�ɹ�����ע��Ӧ-����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-20 ����1:58:02
 */
public class AttentionDetailsActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initData();
		initTitle();
		loadData();
	}

	// ����
	private GoodsSourceInfo info;
	// ����
	private MyLoadingBar loadingBar;

	/**
	 * ����������������
	 */
	private void loadData() {

		loadingBar = new MyLoadingBar(this);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.setPadding(0, getScreenHeight() / 3, 0, 0);
		loadingBar.start();
		win_content.addView(loadingBar);

		JsonLoader loader = new JsonLoader(this);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setUrl(AppNetUrl.getAttentionPurchaseProDetailsUrl(info, state));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				win_content.removeView(loadingBar);
				loadingBar.stop();
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				info = DelegationManager.getAttentionProPurDetailInfo(arg1,
						state);
				win_content.removeView(loadingBar);
				loadingBar.stop();
				// displayToast(arg1.toString());
				RequestNetResultInfo resultInfo = info.getResultInfo();
				if (1 == resultInfo.getStatus()) {
					// ���½���
					initProviderContent();
					// info.setImageurl("http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg");
					// "http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg"
					loadImg(info.getImageurl(), mImgLogo);
					updataViewData();
					initButton();
				} else {
					displayToast(resultInfo.getMsg());
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ͼƬ����
	 * 
	 * @param urlStr
	 * @param view
	 */

	private void loadImg(String urlStr, final ImageView view) {

		ImageLoader loader = new ImageLoader(this, true);
		loader.setUrl(urlStr);
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				Log.d("TT", "errorMessage");
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				BitmapDrawable drawable = (BitmapDrawable) result;
				view.setBackground(drawable);
				Log.d("TT", "onCompelete");

			}
		});

		CmallApplication.getImageStrategy().startLoader(loader);

	}

	// id
	private String id;
	// ״ֵ̬
	// 1����ע��Ӧ����
	// 2����ע�ɹ�����
	private int state;

	/**
	 * ���ܴ��������
	 */
	private void initData() {
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		id = info.getGoodSID();
		state = getIntent().getIntExtra("temp", 0);
	}

	private EventTopTitleView topTitleView;

	/**
	 * ��ʼ��������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
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
				MsgItemUtil
						.onclickPopItem(state, AttentionDetailsActivity.this);
			}
		});
		if (1 == state) {
			topTitleView.setEventName("��ע��Ӧ����");
		} else if (2 == state) {
			topTitleView.setEventName("��ע�̼�����");
		} else {
			topTitleView.setEventName("��ע�ɹ�����");
		}

		win_title.addView(topTitleView);
	}

	// ��Ӧ���������
	private TableLayout proLayout;
	// ��Ӧ����
	private LinearLayout proControl;

	// ��ʾ��˾Logo
	private ImageView mImgLogo;

	// û�й�˾logo����ʾ����
	private TextView mTxtNoLogo;

	/**
	 * ��Ӧ��Ϣ
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.attention_logistics_details, null);
		mImgLogo = (ImageView) view.findViewById(R.id.com_logo);
		proLayout = (TableLayout) view.findViewById(R.id.tab_pro_detail);
		proControl = (LinearLayout) view.findViewById(R.id.lin_pro_control);
		mTxtNoLogo = (TextView) view.findViewById(R.id.txt_no_logo);
		proControl.setVisibility(View.GONE);
		win_content.addView(view);
	}

	private View line;

	/**
	 * ���½�������
	 */
	private void updataViewData() {

		if (null != info) {
			if (!"����ͼƬ".equals(info.getImageurl())) {
				mImgLogo.setVisibility(View.VISIBLE);
				//����ͼƬ����С�߶�
				mImgLogo.setMinimumHeight(getScreenHeight()*2/7);
				mImgLogo.setMinimumWidth(getScreenHeight()*2/7);
				
				mTxtNoLogo.setVisibility(View.GONE);
			}
			ArrayList<IndutryArgumentInfo> infos = DataUtils
					.getAttentionPurProDetail(info, state);
			for (int i = 0; i < infos.size(); i++) {
				line = ContextUtil.getLayoutInflater(this).inflate(
						R.layout.view_line_1, null);

				TableRow row = (TableRow) ContextUtil.getLayoutInflater(this)
						.inflate(R.layout.tab_row_hang, null);
				TextView name = (TextView) row.findViewById(R.id.tv_jian);
				TextView value = (TextView) row.findViewById(R.id.tv_zhi);
				name.setText(infos.get(i).getArgNmae());
				value.setText(infos.get(i).getArgValue());
				proLayout.addView(line,
						Config.getInstance().getScreenWidth(this), 1);
				proLayout.addView(row);
			}
		}
	}

	// ȡ����ע
	private TextView mBtnCancelAttention;

	/**
	 * ���ذ�ť
	 */
	protected void initButton() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_button, null);
		win_content.addView(view);

		TextView mTextView = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mTextView.setVisibility(View.GONE);

		mBtnCancelAttention = (TextView) view
				.findViewById(R.id.btn_commit_modify);
		mBtnCancelAttention.setText("ȡ����ע");
		mBtnCancelAttention.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// ȡ����ע
			cancelAttention();
			break;
		default:
			break;
		}
	}

	/**
	 * ȡ����ע�̼Һ�ȡ����ע�ɹ�
	 */
	private void cancelAttention() {
		JsonLoader loader = new JsonLoader(this);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setUrl(AppNetUrl.getCancelAttentionPurchaseUrl(id, state));
		loader.setPostData(new JSONObject().toString());
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = DelegationManager
						.getCancelAttentionResultInfo(arg1, state);
				// displayToast(arg1.toString());
				// ���½���
				displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					finish();
				} else {
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
