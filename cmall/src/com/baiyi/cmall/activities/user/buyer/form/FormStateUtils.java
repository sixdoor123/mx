package com.baiyi.cmall.activities.user.buyer.form;

import android.view.View;

/**
 * ����
 * 
 * @author Administrator
 * 
 */
public class FormStateUtils {

	/**
	 * �ж϶�����״̬��ť��ʾ
	 */
	// ��0λ������ɾ����ť
	public static final int DELETE_STATE = 0;
	// ��1λ������ȷ���ջ���ť
	public static final int SHOU_HUO_STATE = 1;
	// ��2λ������ȡ����ť
	public static final int CANCEL_STATE = 2;
	// ��3λ�����������˿ť
	public static final int TUI_KUAN_STATE = 3;
	// ��4λ���������߰�ť
	public static final int APPEAL_STATE = 4;

	/**
	 * ����״ֵ̬�����ж�����״̬��ʾ���
	 */
	// ��0λ������༭��ť
	public static final int STATE_EDIT = 0;
	// ��1λ������ȡ����ť
	public static final int STATE_CANCEL = 1;
	// ��2λ������ɹ���ť
	public static final int STATE_BUY = 2;
	// ��3λ������ܾ���ť
	public static final int STATE_JUJUE = 3;
	// ��4λ������������ť
	public static final int STATE_FASONG = 4;
	// ��5λ������ɾ����ť
	public static final int STATE_DELETE = 5;

	/**
	 * �ж��Ƿ���ʾ�Ĺ�������
	 * 
	 * @param binaryValue
	 *            �����������ַ��ܣ�
	 * @param state
	 *            �������������ҵ�λ��
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
