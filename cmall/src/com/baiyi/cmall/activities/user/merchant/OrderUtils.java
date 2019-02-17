package com.baiyi.cmall.activities.user.merchant;

import android.view.View;

/**
 * 订单按钮状态判断
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-15 下午4:34:21
 */
public class OrderUtils {

	/**
	 * 删除在以下情况下隐藏
	 */
	public static final int[] Deletes = { 5, 16, 1, 15, -9, 4, 8, 9, 14, 10, 12 };
	// 当前商家订单状态值等于以下状态值时：确认发货按钮显示
	public static final int Send_GoodS = 8;
	// 当前商家订单状态值等于以下状态值时：已确认退款按钮显示
	public static final int Refund_Aready_Sure = 9;
	// 当前商家订单状态值等于以下状态值时：确认收款按钮显示
	public static final int Receiver_Sure = 10;
	// 当前商家订单状态值等于以下状态值时：拒绝退款按钮显示
	public static final int Refuse_Refund = 15;
	// 当前商家订单状态值等于以下状态值时：同意退款按钮显示
	public static final int Agreen_Refund = 15;
	// 当前商家状态为2时，拒绝按钮不显示
	public static final int Refuse_Order = 2;
	// 当返回状态为-9时，返回
	public static final int Finish = -9;

	/**
	 * 判断是否直接返回
	 * 
	 * @param orderState
	 * @return
	 */
	public static boolean isFinish(int orderState) {
		if (Finish == orderState) {
			return true;
		}
		return false;
	}

	/**
	 * 拒绝按钮是否显示
	 * 
	 * @param orderState
	 * @return
	 */
	public static int getRefuseOrder(int orderState) {
		if (Refuse_Order == orderState) {
			return View.GONE;
		}
		return View.VISIBLE;
	}

	/**
	 * 删除按钮是否显示
	 * 
	 * @param orderState
	 * @return
	 */
	public static int getDelete(int orderState) {
		for (int i = 0; i < Deletes.length; i++) {
			if (Deletes[i] == orderState) {
				return View.GONE;
			}
		}
		return View.VISIBLE;
	}

	/**
	 * 确认发货按钮是否显示
	 * 
	 * @param orderState
	 * @return
	 */
	public static int getSendGoodS(int orderState) {
		if (Send_GoodS == orderState) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * 已确认退款按钮是否显示
	 * 
	 * @param orderState
	 * @return
	 */
	public static int getRefundAreadySure(int orderState) {
		if (Refund_Aready_Sure == orderState) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * 确认收款按钮是否显示
	 * 
	 * @param orderState
	 * @return
	 */
	public static int getReceiverSure(int orderState) {
		if (Receiver_Sure == orderState) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * 拒绝退款按钮是否显示
	 * 
	 * @param orderState
	 * @return
	 */
	public static int getRefuseRefund(int orderState) {
		if (Refuse_Refund == orderState) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * 同意退款按钮是否显示
	 * 
	 * @param orderState
	 * @return
	 */
	public static int getAgreenRefund(int orderState) {
		if (Agreen_Refund == orderState) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

}
