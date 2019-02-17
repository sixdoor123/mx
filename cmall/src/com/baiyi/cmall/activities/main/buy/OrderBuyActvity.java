package com.baiyi.cmall.activities.main.buy;

import java.util.List;

import org.json.JSONArray;

import com.baiyi.cmall.R;
import com.baiyi.cmall.TLog;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.buy.dialog.DialogYuePay;
import com.baiyi.cmall.activities.main.buy.req.ReqOrder;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.CommonSearchView;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 订单支付
 * @author tangkun
 *
 */
public class OrderBuyActvity extends BaseActivity{
	
	private List<ReqOrder> postReqOrderList = null;
	private ReqOrder reqOrder = null;
	
	private TextView txtTotalPrice = null;
	private ImageView btnArrow = null;
//	//产品封面
//	private ImageView imgPro = null;
	private LinearLayout infoGroup = null;
	//产品列表 容器
	private LinearLayout proGroup = null;
	//收货地址
	private TextView txtAccept = null;
	//发票信息
	private TextView txtInvoice = null;
	
	private PaymentMethodView paymentMethodView = null;
	
	private String totalPrice = null;
	private String orderId = null;
	
	private DialogYuePay dialogYuePay = null;
	
	private EventTopTitleView topTitleView = null;
	
	private boolean isOpen = false;
	
	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_order_buy, win_content);
		postReqOrderList = (List<ReqOrder>)getIntent().getSerializableExtra(MallDefine.ReqOrder);
		totalPrice = getIntent().getStringExtra(MallDefine.OrderTotalPrice);
		orderId = getIntent().getStringExtra(MallDefine.OrderId);
		
		if (!Utils.isStringEmpty(postReqOrderList)) {
			reqOrder = postReqOrderList.get(0);
			
			initTitle();
			initInfoViews();
			initPayment();
			
			displayInfos();
			displayPros();
			setInfoVisible();
		}
	}
	
	private void initTitle()
	{
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("支付方式");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, OrderBuyActvity.this);
			}
		});
		win_title.addView(topTitleView);
	}
	
	private void initInfoViews()
	{
		txtTotalPrice = (TextView)findViewById(R.id.txt_total_price);
		btnArrow = (ImageView)findViewById(R.id.btn_arrow);
		btnArrow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				isOpen = isOpen ? false : true;
				setInfoVisible();
			}
		});
		infoGroup = (LinearLayout)findViewById(R.id.info_group);
		proGroup = (LinearLayout)findViewById(R.id.order_pro_group);
		txtAccept = (TextView)findViewById(R.id.txt_accept);
		txtInvoice = (TextView)findViewById(R.id.txt_invoice);
	}
	
	private void initPayment()
	{
		paymentMethodView = (PaymentMethodView)findViewById(R.id.view_payment_method);
		paymentMethodView.setOi(orderId);
		paymentMethodView.setUnPrice(totalPrice);
	}
	
	private void setInfoVisible()
	{
		int visible = isOpen ? View.VISIBLE : View.GONE;
		infoGroup.setVisibility(visible);
	}
	
	private void displayInfos()
	{
		txtTotalPrice.setText(totalPrice+"元");
	}
	
	private void displayPros()
	{
		for(int i = 0; i < reqOrder.getOswmList().size(); i++)
		{
			View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.item_order_pro, null);
			ImageView imgPro = (ImageView)view.findViewById(R.id.img_pro);
			TextView proName = (TextView)view.findViewById(R.id.order_pro_name);
			TextView shopName = (TextView)view.findViewById(R.id.order_shop_name);
			TextView sp = (TextView)view.findViewById(R.id.order_sp_name);
			TextView num = (TextView)view.findViewById(R.id.order_num);
			TextView unprice = (TextView)view.findViewById(R.id.order_unprice);
			proName.setText(reqOrder.getOswmList().get(i).getPn());
			shopName.setText("商家:"+reqOrder.getOpmEntity().getCn());
			sp.setText("规格:"+reqOrder.getOswmList().get(i).getSpList().get(0));
			num.setText("数量:"+reqOrder.getOswmList().get(i).getPc());
			unprice.setText("单价:"+reqOrder.getOswmList().get(i).getPr());
			proGroup.addView(view);
		}
	}

}
