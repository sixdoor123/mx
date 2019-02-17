package com.baiyi.cmall.activities.user.merchant.order;

import java.io.Serializable;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.sf.json.JSONObject;

/**
 * 商家订单列表总分类
 * 
 * @author sunxy
 */
public class MainCategoryItemView extends LinearLayout implements OnClickListener {

	private Context context;
	private GoodsSourceInfo info;

	public MainCategoryItemView(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public MainCategoryItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	//分类图标
	private ImageView mImgCategoryPic;
	//分类名称
	private TextView mTxtCategoryName;
	//分类状态
	private TextView mTxtOrderState;
	
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context)
				.inflate(R.layout.main_category_item_view, null);
		addView(view);
		
		mImgCategoryPic = (ImageView) view.findViewById(R.id.img_category_pic);
		mTxtCategoryName = (TextView) view.findViewById(R.id.txt_category_name);
		mTxtOrderState = (TextView) view.findViewById(R.id.txt_oreder_state);
		
		this.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		((BaseActivity)context).displayToast("你点了---MainCategoryItemView");
	}
	
	/**
	 * 刷新数据
	 * @param info
	 */
	public void disPlayView(Serializable info){
		this.info = (GoodsSourceInfo) info;
//		loadImg(this.info.getImageurl(), mImgCategoryPic);
//		mTxtCategoryName.setText();
		mTxtOrderState.setText(this.info.getStatename());
	}
	/**
	 * 图片下载
	 * 
	 * @param urlStr
	 * @param view
	 */

	private void loadImg(String urlStr, final ImageView view) {

		ImageLoader loader = new ImageLoader(context, true);
		loader.setUrl(urlStr);
		loader.setPostData(new JSONObject().toString());
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
}
