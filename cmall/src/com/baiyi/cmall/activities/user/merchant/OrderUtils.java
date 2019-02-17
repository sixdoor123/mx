package com.baiyi.cmall.activities.user.merchant;

import android.view.View;

/**
 * ������ť״̬�ж�
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-15 ����4:34:21
 */
public class OrderUtils {

	/**
	 * ɾ�����������������
	 */
	public static final int[] Deletes = { 5, 16, 1, 15, -9, 4, 8, 9, 14, 10, 12 };
	// ��ǰ�̼Ҷ���״ֵ̬��������״ֵ̬ʱ��ȷ�Ϸ�����ť��ʾ
	public static final int Send_GoodS = 8;
	// ��ǰ�̼Ҷ���״ֵ̬��������״ֵ̬ʱ����ȷ���˿ť��ʾ
	public static final int Refund_Aready_Sure = 9;
	// ��ǰ�̼Ҷ���״ֵ̬��������״ֵ̬ʱ��ȷ���տť��ʾ
	public static final int Receiver_Sure = 10;
	// ��ǰ�̼Ҷ���״ֵ̬��������״ֵ̬ʱ���ܾ��˿ť��ʾ
	public static final int Refuse_Refund = 15;
	// ��ǰ�̼Ҷ���״ֵ̬��������״ֵ̬ʱ��ͬ���˿ť��ʾ
	public static final int Agreen_Refund = 15;
	// ��ǰ�̼�״̬Ϊ2ʱ���ܾ���ť����ʾ
	public static final int Refuse_Order = 2;
	// ������״̬Ϊ-9ʱ������
	public static final int Finish = -9;

	/**
	 * �ж��Ƿ�ֱ�ӷ���
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
	 * �ܾ���ť�Ƿ���ʾ
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
	 * ɾ����ť�Ƿ���ʾ
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
	 * ȷ�Ϸ�����ť�Ƿ���ʾ
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
	 * ��ȷ���˿ť�Ƿ���ʾ
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
	 * ȷ���տť�Ƿ���ʾ
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
	 * �ܾ��˿ť�Ƿ���ʾ
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
	 * ͬ���˿ť�Ƿ���ʾ
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
