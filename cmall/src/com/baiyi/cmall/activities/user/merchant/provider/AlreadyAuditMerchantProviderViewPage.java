package com.baiyi.cmall.activities.user.merchant.provider;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.views.MyLoadingBar;

import android.content.Context;
import android.content.Intent;

/**
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-�����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-18 ����10:59:40
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
