package com.baiyi.cmall.activities.user.merchant.provider;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.views.MyLoadingBar;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

/**
 * 我是供应商-我的供应-全部
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-18 上午10:59:40
 */
public class AllMerchantProviderViewPage extends BaseMerchantProviderViewPager {
	//
	// // 状态
	// public String orderState;
	// // 商家Id
	// public String companyId;

	public AllMerchantProviderViewPage(Context context, String orderState,
			String companyId) {
		super(context, orderState, companyId);

	}

	/**
	 * 得到访问路径
	 */
	@Override
	public String getMerchantProUrl(MyLoadingBar loadingBar) {
		return AppNetUrl.getMyProviderGoodSUrl(orderState,
				pageIndex, Config.LIST_ITEM_COUNT);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
	}
}
