package com.baiyi.cmall.utils;

import android.content.Context;
import android.content.Intent;

import com.baiyi.cmall.activities.main.BaseActivity.ActivityStackControlUtil;
import com.baiyi.cmall.activities.main.header_top.PublishPurchaseActivity;
import com.baiyi.cmall.R;

public class MsgItemUtil {

	public static final String[] Pop_Default_txt = { "消息", "首页" };
	public static final int[] Pop_Default_img = { R.drawable.pop_news2x,
			R.drawable.pop_home2x };

	public static final String[] Pop_Msg_Txt = { "全部标为已读", "首页" };
	public static final int[] Pop_Msg_Img = { R.drawable.icon_new_open,
			R.drawable.pop_home2x };

	public static final String[] Pop_Main_Txt = { "委托采购", "委托供应", "委托物流" };
	public static final int[] Pop_Main_Img = { R.drawable.icon_purchase,
			R.drawable.icon_gonghuo, R.drawable.icon_logistics };

	/**
	 * 
	 * @param state
	 */
	public static void onclickPopItem(int state, Context context) {
		if (state == 0) {
			IntentClassChangeUtils.startMsg(context);
		} else if (state == 1) {
			ActivityStackControlUtil.finishProgram();
		}
	}

	/**
	 * 
	 * @param state
	 */
	public static void onclickMainPopItem(int state, Context context) {
		if (state == 0) {
			IntentClassChangeUtils.startPur(context);
		} else if (state == 1) {
			IntentClassChangeUtils.startPri(context);
		} else if (state == 2) {
			IntentClassChangeUtils.startLogist(context);
		}
	}
}
