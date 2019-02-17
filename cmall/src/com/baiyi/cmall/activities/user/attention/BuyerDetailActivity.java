package com.baiyi.cmall.activities.user.attention;

import java.util.ArrayList;

import org.json.JSONObject;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.DelegationManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * �ɹ�������
 * 
 * @author sunxy
 */
public class BuyerDetailActivity extends BaseActivity implements OnClickListener {

	private GoodsSourceInfo info;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		loadData();
	}

	private String id;
private String floowId ;
	/**
	 * ��ʼ������
	 */
	private void initData() {
		floowId = getIntent().getStringExtra("temp");
		id = getIntent().getStringExtra("arg");
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

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, BuyerDetailActivity.this);
			}
		});
		topTitleView.setEventName("��ע�ɹ�������");

		win_title.addView(topTitleView);
	}

	/**
	 * ����������������
	 */
	private void loadData() {

		final LoadingBar loadingBar = new LoadingBar(this);
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany);
		loader.setUrl(AppNetUrl.getAttentionBuyerDetailsUrl(id));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.stop();
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				Log.d("TAG", "��ע�ɹ�������--" + arg1);
				info = DelegationManager.getAttentionBuyerDetailInfo(arg1);
				// displayToast(arg1.toString());
				loadingBar.stop();
				if (null!= info) {
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
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
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
		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.attention_logistics_details, null);
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
			/*
			 * if (!"����ͼƬ".equals(info.getImageurl())) {
			 * mImgLogo.setVisibility(View.VISIBLE); // ����ͼƬ����С�߶�
			 * mImgLogo.setMinimumHeight(250);
			 * mTxtNoLogo.setVisibility(View.GONE); }else
			 */ if (TextViewUtil.isStringEmpty(info.getImageurl())) {
				mImgLogo.setVisibility(View.GONE);
				// ����ͼƬ����С�߶�
//				mImgLogo.setMinimumHeight(250);
				mTxtNoLogo.setVisibility(View.VISIBLE);
			}else {
				mImgLogo.setVisibility(View.VISIBLE);
				// ����ͼƬ����С�߶�
				//����ͼƬ����С�߶�
				mImgLogo.setMinimumHeight(getScreenHeight()*2/7);
				mImgLogo.setMinimumWidth(getScreenHeight()*2/7);
				mTxtNoLogo.setVisibility(View.GONE);
			}
			ArrayList<IndutryArgumentInfo> infos = DataUtils.getAttentionBuyerDetail(info);
			for (int i = 0; i < infos.size(); i++) {
				line = ContextUtil.getLayoutInflater(this).inflate(R.layout.view_line_1, null);

				TableRow row = (TableRow) ContextUtil.getLayoutInflater(this).inflate(R.layout.tab_row_hang, null);
				TextView name = (TextView) row.findViewById(R.id.tv_jian);
				TextView value = (TextView) row.findViewById(R.id.tv_zhi);
				name.setText(infos.get(i).getArgNmae());
				value.setText(infos.get(i).getArgValue());
				proLayout.addView(line, Config.getInstance().getScreenWidth(this), 1);
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
		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_button, null);
		win_content.addView(view);

		TextView mTextView = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mTextView.setVisibility(View.GONE);

		mBtnCancelAttention = (TextView) view.findViewById(R.id.btn_commit_modify);
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
	 * ȡ����ע��Ӧ�̺�ȡ����ע�ɹ�
	 */
	private void cancelAttention() {

		final LoadingBar loadingBar = new LoadingBar(this);
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setUrl(AppNetUrl.getCancelAttentionBuyerUrl(floowId));
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
				loadingBar.stop();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = DelegationManager.getCancelAttentionResultInfo(arg1, 0);
				// displayToast(arg1.toString());
				// ���½���
				loadingBar.stop();
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
