package com.baiyi.cmall.activities.user.merchant;

import android.R.integer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * 意向单操作
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-22 下午1:59:44
 */
public class IntentionOrderUtils {

	// 基本常量
	public static final int Num = -9;
	public static final int One = 1;
	public static final int Two = 2;
	public static final int Three = 3;
	public static final int Four = 4;

	// 数组
	public static final int[] Numbers = { -3, -4, -5, -6 };
	public static final int[] Deletes = { -3, -4, -5, -6, 5 };

	private static int[] status;

	/**
	 * 判断是否添加按钮布局
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
	 * 判断是否返回上一页
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
	 * 得到数组 将字符串转化为整形数组
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
	 * 判断按钮时显示还是隐藏
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
	 * 判断完成按钮是否显示
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
	 * 意向状态为(-3||-4||-5||-6||5) && deletebyuser!=-9------删除
	 * deletebyuser==-9------删除
	 * 
	 * 判断删除按钮是否显示
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
	 * 意向类型为2 &&　意向状态为１&& deletebyuser!=-9------编辑供应
	 * 
	 * 判断确认供应是否可操作
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
	 * 意向类型为2 &&　意向状态为１&& deletebyuser!=-9------编辑供应
	 * 
	 * 判断各个输入框等是否可操作
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
	 * 意向状态为(-3||-4||-5||-6) && deletebyuser＝＝-9 - ---查看详情(只看自己的信息，不能编辑） 意向状态为5
	 * 查看详情是否看操作
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
	 * 意向类型为2 && (意向状态为１|| 意向状态为2) && deletebyuser!=-9------取消 (意向类型为2||意向类型为4)
	 * && （意向状态为１|| 意向状态为2) && deletebyuser!=-9------取消 判断取消是否显示
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
	 * 意向类型为1 &&　意向状态为１ && deletebyuser!=-9 ------供应
	 * 
	 * 判断供应
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
	 * 意向类型为1 && （意向状态为１|| 意向状态为2) && deletebyuser!=-9 (意向类型为1||意向类型为3) &&
	 * （意向状态为１|| 意向状态为2) && deletebyuser!=-9------拒绝 判断拒绝是否显示
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
