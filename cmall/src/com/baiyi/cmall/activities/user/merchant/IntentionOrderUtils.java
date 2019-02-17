package com.baiyi.cmall.activities.user.merchant;

import android.R.integer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * ���򵥲���
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-22 ����1:59:44
 */
public class IntentionOrderUtils {

	// ��������
	public static final int Num = -9;
	public static final int One = 1;
	public static final int Two = 2;
	public static final int Three = 3;
	public static final int Four = 4;

	// ����
	public static final int[] Numbers = { -3, -4, -5, -6 };
	public static final int[] Deletes = { -3, -4, -5, -6, 5 };

	private static int[] status;

	/**
	 * �ж��Ƿ���Ӱ�ť����
	 * 
	 * @param status2
	 * @return
	 */
	public static boolean isAddView(int[] status2) {
		for (int i = 0; i < status.length; i++) {
			if (status[i] == One) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж��Ƿ񷵻���һҳ
	 * 
	 * @param status2
	 * @return
	 */
	public static boolean isFinish(int[] status) {
		for (int i = 0; i < status.length; i++) {
			if (status[i] == One) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �õ����� ���ַ���ת��Ϊ��������
	 * 
	 * @param binaryvalue
	 * @return
	 */
	public static int[] getStatus(String binaryvalue) {
		if (TextUtils.isEmpty(binaryvalue)) {
			return null;
		}
		if (binaryvalue == null) {
			return null;
		}
		status = new int[binaryvalue.length()];
		char[] chars = binaryvalue.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			status[i] = Integer.parseInt(chars[i] + "");
		}
		return status;
	}

	/**
	 * �жϰ�ťʱ��ʾ��������
	 * 
	 * @param state
	 * @return
	 */
	public static int isVisableOrGone(int state) {
		if (One == state) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * �ж���ɰ�ť�Ƿ���ʾ
	 * 
	 * @param status2
	 * @return
	 */
	public static boolean isEditDetailEnable(int[] status) {

		if (status[0] == One || status[2] == One || status[4] == One) {
			return true;
		}
		return false;
	}

	/**
	 * ����״̬Ϊ(-3||-4||-5||-6||5) && deletebyuser!=-9------ɾ��
	 * deletebyuser==-9------ɾ��
	 * 
	 * �ж�ɾ����ť�Ƿ���ʾ
	 * 
	 * @param intentionState
	 * @param deletebyuser
	 * @return
	 */
	public static int getBtnDelete(int intentionState, int deletebyuser) {
		if (deletebyuser == Num) {
			return View.VISIBLE;
		} else {
			for (int i = 0; i < Deletes.length; i++) {
				if (Deletes[i] == intentionState && deletebyuser != Num) {
					return View.VISIBLE;
				}
			}
		}
		return View.GONE;
	}

	/**
	 * ��������Ϊ2 &&������״̬Ϊ��&& deletebyuser!=-9------�༭��Ӧ
	 * 
	 * �ж�ȷ�Ϲ�Ӧ�Ƿ�ɲ���
	 * 
	 * @param intentionType
	 * @param intentionState
	 * @param deletebyuser
	 * @return
	 */
	public static int getBtnSureProEnable(int intentionType,
			int intentionState, int deletebyuser) {
		if ((intentionType == Two || intentionType == Four)
				&& intentionState == One && deletebyuser != Num) {
			return View.VISIBLE;
		} else if ((intentionType == One || intentionType == Three)
				&& intentionState == One && deletebyuser != Num) {
			return View.VISIBLE;
		} else if (intentionState == Two && deletebyuser != Num) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * ��������Ϊ2 &&������״̬Ϊ��&& deletebyuser!=-9------�༭��Ӧ
	 * 
	 * �жϸ����������Ƿ�ɲ���
	 * 
	 * @param intentionType
	 * @param intentionState
	 * @param deletebyuser
	 * @return
	 */
	public static boolean isInputProEnable(int intentionType,
			int intentionState, int deletebyuser) {
		if ((intentionType == Two || intentionType == Four)
				&& intentionState == One && deletebyuser != Num) {
			return false;
		} else if ((intentionType == One || intentionType == Three)
				&& intentionState == One && deletebyuser != Num) {
			return false;
		} else if (intentionState == Two && deletebyuser != Num) {
			return false;
		}
		return true;
	}

	/**
	 * ����״̬Ϊ(-3||-4||-5||-6) && deletebyuser����-9 - ---�鿴����(ֻ���Լ�����Ϣ�����ܱ༭�� ����״̬Ϊ5
	 * �鿴�����Ƿ񿴲���
	 * 
	 * @param intentionState
	 * @param deletebyuser
	 * @return
	 */
	public static boolean isEditDetailEnable(int intentionState,
			int deletebyuser) {
		if (intentionState == 5) {
			return false;
		} else {
			for (int i = 0; i < Numbers.length; i++) {
				if (Numbers[i] == intentionState || deletebyuser == Num) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * ��������Ϊ2 && (����״̬Ϊ��|| ����״̬Ϊ2) && deletebyuser!=-9------ȡ�� (��������Ϊ2||��������Ϊ4)
	 * && ������״̬Ϊ��|| ����״̬Ϊ2) && deletebyuser!=-9------ȡ�� �ж�ȡ���Ƿ���ʾ
	 * 
	 * @param intentionType
	 * @param intentionState
	 * @param deletebyuser
	 * @return
	 */
	public static int getBtnCancel(int intentionType, int intentionState,
			int deletebyuser) {
		if ((intentionType == Two || intentionType == Four)
				&& (intentionState == One || intentionState == Two)
				&& deletebyuser != Num) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * ��������Ϊ1 &&������״̬Ϊ�� && deletebyuser!=-9 ------��Ӧ
	 * 
	 * �жϹ�Ӧ
	 * 
	 * @param intentionState
	 * @param deletebyuser
	 * @return
	 */
	public static int isProvider(int intentionType, int intentionState,
			int deletebyuser) {
		if ((intentionType == One || intentionType == Three)
				&& intentionState == One && deletebyuser != Num) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

	/**
	 * ��������Ϊ1 && ������״̬Ϊ��|| ����״̬Ϊ2) && deletebyuser!=-9 (��������Ϊ1||��������Ϊ3) &&
	 * ������״̬Ϊ��|| ����״̬Ϊ2) && deletebyuser!=-9------�ܾ� �жϾܾ��Ƿ���ʾ
	 * 
	 * @param intentionType
	 * @param intentionState
	 * @param deletebyuser
	 * @return
	 */
	public static int getBtnRefuse(int intentionType, int intentionState,
			int deletebyuser) {
		if ((intentionType == One || intentionType == Three)
				&& (intentionState == One || intentionState == Two)
				&& deletebyuser != Num) {
			return View.VISIBLE;
		}
		return View.GONE;
	}

}
