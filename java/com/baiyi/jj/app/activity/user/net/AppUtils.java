/**
 * 
 */
package com.baiyi.jj.app.activity.user.net;

import android.content.Context;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.dialog.NoLoginDialog;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

/**
 * @author tangkun
 *
 */
public class AppUtils {

	public static void loginRemind(final Context context, String title, String message){
		if (!ContextUtil.isNetWorking(context)) {
			message = ((BaseActivity)context).getResources().getString(R.string.tip_net_fault);
			((BaseActivity)context).displayMsgBox(context.getResources().getString(R.string.tip_dialog_title), message);
			return;
		}else {
			message = ((BaseActivity)context).getResources().getString(R.string.tip_dialog_longin);
		}
		NoLoginDialog dialog = new NoLoginDialog(context,message);
		dialog.showDialog(DialogBase.AnimalTop);
	}


	public static String getMid(boolean isAninymity)
	{
		UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
		if(isAninymity)
		{
			
			if (infoEntity == null) {
				String mid = AccountManager.getInstance().getMid();
				if(!Utils.isStringEmpty(mid))
				{
					return mid;
				}
				String key = Preference.getInstance().Get(XMLName.Xml_CustomerKey);
				if(!Utils.isStringEmpty(key))
				{
					return key;
				}
				return null;
			}
		}else {
			if(infoEntity == null)
			{
				return null;
			}
		}
		String mid = infoEntity.getMID();
		return mid;
	}

}
