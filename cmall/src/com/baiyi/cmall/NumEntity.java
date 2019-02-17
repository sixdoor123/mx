package com.baiyi.cmall;

public class NumEntity {

	// 收货人信息标志
	public static final int RESULT_CONSIGNEE = 1;
	// 发票标志
	public static final int RESULT_INVOICE = 2;

	// 订单状态查找，没找到相应的数据时，返回的数据
	public static final int RESULT_FIND_FAIL = 100;

	// 编辑状态
	public static final int STATE_EDIT = 1;
	// 新增状态
	public static final int STATE_ADD = 0;

	// 城市
	public static final int STATE_CITY = 0;
	// 交割地
	public static final int STATE_DEVALITE = 1;
	// 产地
	public static final int STATE_PLACE = 2;

	// 订单界面操作ok
	public static final int RESULT_FORM_OK = 3;
	// 返回
	public static final int RESULT_CANCEL = 4;
	// 账号过期时的提示语
	public static final String PLEASE_LOG = "请先登录";

	public static final int FIRST = 1;
	public static final int SECONED = 2;//省
	public static final int THIRD = 3;
	public static final int FOUR = 4;
}
