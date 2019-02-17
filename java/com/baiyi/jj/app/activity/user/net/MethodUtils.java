package com.baiyi.jj.app.activity.user.net;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity.ActivityStackControlUtil;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;

import android.content.Context;

public class MethodUtils {
	
	public static void OnMoreClick(Context context,int state){
		if (state == 0) {  // ��Ϣ
			UserInfoEntity user = CmsApplication.getUserInfoEntity();
			if (user == null) {
				AppUtils.loginRemind(context, context.getResources()
						.getString(R.string.tip_dialog_title), context.getResources()
						.getString(R.string.tip_dialog_unlongin));
				return;
			}
			IntentClassChangeUtils.startMyNews(context);
		}  else if (state == 1) { //��ҳ
			
			IntentClassChangeUtils.startHome(context,0);
			ActivityStackControlUtil.finishProgram();
			 
		}
	}

}
