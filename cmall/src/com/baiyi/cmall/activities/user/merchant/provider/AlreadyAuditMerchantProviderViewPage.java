package com.baiyi.cmall.activities.user.merchant.provider;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.views.MyLoadingBar;

import android.content.Context;
import android.content.Intent;

/**
 * 我是供应商-我的供应-已审核
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-18 上午10:59:40
 */
public class AlreadyAuditMerchantProviderViewPage extends
		BaseMerchantProviderViewPager {
	
	public AlreadyAuditMerchantProviderViewPage(Context context, String orderState,
			String companyId) {
		super(context,orderState,companyId);
	}

	@Override
	public String getMerchantProUrl(MyLoadingBar loadingBar) {
		return AppNetUrl.getMyProviderGoodSUrl(orderState, 
				pageIndex, Config.LIST_ITEM_COUNT);
	}


}
