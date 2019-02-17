package com.baiyi.cmall.activities.user.merchant.order;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.order.product.detail.ProductOrderDetailActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
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
 * 产品列表
 * 
 * @author sunxy
 */
public class ProductInformationItemView extends LinearLayout implements OnClickListener {

	private Context context;

	public ProductInformationItemView(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public ProductInformationItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	// 产品图片
	private ImageView mImgProductPic;
	// 产品名称
	private TextView mTxtProductName;
	// 产品价格
	private TextView mTxtProductPrice;
	// 产品数量
	private TextView mTxtProductNum;

	/**
	 * 初始化界面
	 */
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.product_info_item_view, null);
		addView(view);

		mImgProductPic = (ImageView) view.findViewById(R.id.img_product_pic);
		mTxtProductName = (TextView) view.findViewById(R.id.txt_product_name);
		mTxtProductPrice = (TextView) view.findViewById(R.id.txt_product_price);
		mTxtProductNum = (TextView) view.findViewById(R.id.txt_product_num);

		this.setOnClickListener(this);
	}

	private GoodsSourceInfo info = null;

	/**
	 * 设置数据
	 * 
	 * @param info
	 */
	public void displayView(GoodsSourceInfo info) {
		this.info = info;
		loadImg(info.getImageurl(), mImgProductPic);
		mTxtProductName.setText(info.getGoodSName());
		mTxtProductPrice.setText(Utils.twoDecimals(info.getPrice()));
		mTxtProductNum.setText(info.getInventory() + "吨");

		getPayMoney();

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

	private double totalMoney;

	public String getTotalMoney() {
		return Utils.twoDecimals(String.valueOf(totalMoney));
	}

	/**
	 * 计算价格
	 * 
	 * @return
	 */
	public void getPayMoney() {
		String price = Utils.getNumberOfString(mTxtProductPrice.getText().toString().trim());
		String number = Utils.getNumberOfString(mTxtProductNum.getText().toString().trim());
		if (TextViewUtil.isStringEmpty(price)) {
			price = "0";
		}
		if (TextViewUtil.isStringEmpty(number)) {
			number = "0";
		}
		double d = Double.parseDouble(number) * Double.parseDouble(price);
		totalMoney += d;
	}

	@Override
	public void onClick(View v) {
		((BaseActivity) context).displayToast("你点了---ProductInformationItemView");
		String idString = info.getId();
		Log.d("TAG", "id-------"+idString);
		((BaseActivity) context).goActivity( ProductOrderDetailActivity.class,idString,"");
	}
}
