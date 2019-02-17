package com.baiyi.cmall.activities.user.buyer.form;

import android.view.View;

/**
 * 我是
 * 
 * @author Administrator
 * 
 */
public class FormStateUtils {

	/**
	 * 判断订单的状态按钮显示
	 */
	// 第0位：代表删除按钮
	public static final int DELETE_STATE = 0;
	// 第1位：代表确认收货按钮
	public static final int SHOU_HUO_STATE = 1;
	// 第2位：代表取消按钮
	public static final int CANCEL_STATE = 2;
	// 第3位：代表申请退款按钮
	public static final int TUI_KUAN_STATE = 3;
	// 第4位：代表申诉按钮
	public static final int APPEAL_STATE = 4;

	/**
	 * 以下状态值用于判断意向状态显示与否
	 */
	// 第0位：代表编辑按钮
	public static final int STATE_EDIT = 0;
	// 第1位：代表取消按钮
	public static final int STATE_CANCEL = 1;
	// 第2位：代表采购按钮
	public static final int STATE_BUY = 2;
	// 第3位：代表拒绝按钮
	public static final int STATE_JUJUE = 3;
	// 第4位：代表发送意向按钮
	public static final int STATE_FASONG = 4;
	// 第5位：代表删除按钮
	public static final int STATE_DELETE = 5;

	/**
	 * 判断是否显示的公共方法
	 * 
	 * @param binaryValue
	 *            二进制数（字符窜）
	 * @param state
	 *            二进制数从左到右的位置
	 * @return
	 */
	public static int isShow(String binaryValue, int state) {
		state = Integer.parseInt(binaryValue.substring(state, state + 1));
		if (1 == state) {

			return View.VISIBLE;
		} else if (0 == state) {

			return View.GONE;
		}
		return View.GONE;
	}

}
