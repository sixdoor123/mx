package com.baiyi.cmall.activities.main.buy.dialog;

import org.json.JSONArray;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.buy.BuyNetUrl;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 余额支付
 * @author tangkun
 *
 */
public class DialogYuePay extends DialogBase{
	//帐户余额
	@SuppressWarnings("unused")
	private TextView txtYue = null;
	//需要金额
	private TextView txtUnprice = null;
	//支付
	private Button btnPay = null;
	//订单id
	private String oi = null;
	//单价金额
	private String unPrice = null;

	public DialogYuePay(Context context, int winType, String oi, String unPrice) {
		super(context, winType);
		this.oi = oi;
		this.unPrice = unPrice;
	}

	@Override
	public void setTitleContent() {
	}

	@SuppressLint("InflateParams")
	@Override
	public void setContainer() {
		View contentView = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.dialog_yue_pay, null);
		addView(contentView);
		txtYue = (TextView)contentView.findViewById(R.id.txt_yue);
		txtUnprice = (TextView)contentView.findViewById(R.id.txt_price);
		btnPay = (Button)contentView.findViewById(R.id.btn_pay);
		btnPay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendYuePay(oi);
			}
		});
		
		txtUnprice.setText(unPrice);
	}
	
	/**
	 * 
	 * @param oi 订单id
	 */
	private void sendYuePay(String oi)
	{
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(BuyNetUrl.getOrderPay(oi));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(" ");
//		loader.setContentTextList(getSendOrderPostData());
		loader.addRequestHeader("token", ((BaseActivity)getContext()).token);
		loader.setLoaderListener(new LoaderListener() {
			
			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}
			
			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
			}
			
			@SuppressWarnings("unused")
			@Override
			public void onCompelete(Object tag, Object result) {
				String getResult=JsonParseBase.getMsg((JSONArray)result);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
		
	}

	@Override
	public void OnClickListenEvent(View v) {
	}
}
