package com.baiyi.cmall.activities.main.business.page;

import org.json.JSONArray;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.BusinessUrlNet;
import com.baiyi.cmall.activities.main.business.JsonParseBusiness;
import com.baiyi.cmall.activities.main.business.entity.BusinessDetailEntity;
import com.baiyi.cmall.activities.main.business.entity.CbmEntity;
import com.baiyi.cmall.activities.main.business.pop.DialogQrcode;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 店铺详情
 * @author tangkun
 *
 */
public class ShopDetailFragment extends BaseViewPagerFragment implements
		AbsListView.OnItemClickListener {
	//公司名称
	private TextView txtShopName;//
	//公司类型
	private TextView txtShopType;//
	//公司所在地
	private TextView txtShopArea;//
	//公司注册时间
	private TextView txtShopRegisterTime;//
	//客服电话
	private TextView txtShopPhoneNum;//
	private LinearLayout btnQrCode;
	
	private DialogQrcode dialogQrcode;
	
	private int ci;
	

	public static ShopDetailFragment newInstance(int index, int ci) {
		ShopDetailFragment fragment = new ShopDetailFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_FRAGMENT_INDEX, index);
		args.putInt(MallDefine.CI, ci);
		fragment.setArguments(args);
		return fragment;
	}

	public ShopDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ci = getArguments().getInt(MallDefine.CI);
		loadDetail(ci);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.item_business_shop_info, container,
				false);
		txtShopName = (TextView)view.findViewById(R.id.txt_shop_name);
		txtShopType = (TextView)view.findViewById(R.id.txt_shop_type);
		txtShopArea = (TextView)view.findViewById(R.id.txt_area);
		txtShopRegisterTime = (TextView)view.findViewById(R.id.txt_register_time);
		txtShopPhoneNum = (TextView)view.findViewById(R.id.txt_shop_phonenum);
		btnQrCode = (LinearLayout)view.findViewById(R.id.btn_qr_code);
		btnQrCode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				displayPopQrCode();
			}
		});
		return view;
	}
	
	private void loadDetail(int ci)
	{
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(BusinessUrlNet.getCompanyDetail(ci));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {
			
			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCompelete(Object tag, Object result) {
				// TODO Auto-generated method stub
				BusinessDetailEntity entity = JsonParseBusiness.getBusinessDetail((JSONArray)result);
				cbmEntity = entity.getCbmEntity();
				displayViews(entity);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
	
	//商铺信息，需要在显示二维码的界面显示
	private CbmEntity cbmEntity = null;
	/**
	 * 显示商家二维码
	 */
	private void displayPopQrCode()
	{
		
		
		IntentClassChangeUtils.startQrCodeActivity(getActivity(),ci,cbmEntity);
		

//		if(dialogQrcode == null)
//		{
//			dialogQrcode = new DialogQrcode(getContext(), DialogBase.Win_Center,ci);
//		}
//		dialogQrcode.show();
	}
	
	
	
	
	
	private void displayViews(BusinessDetailEntity entity)
	{
		if(entity == null)
		{
			return;
		}
		txtShopName.setText(entity.getDpiEntity().getCn());
		txtShopType.setText(entity.getDpiEntity().getCt());
		txtShopArea.setText(entity.getDpiEntity().getCa());
		String ti = Utils.isStringEmpty(entity.getDpiEntity().getTi()) ? "" : entity.getDpiEntity().getTi();
		txtShopRegisterTime.setText(ti);
		String phoneNum = Utils.isStringEmpty(entity.getDpiEntity().getPh()) ? "" : entity.getDpiEntity().getPh();
		txtShopPhoneNum.setText(phoneNum);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		return true;
	}
}
