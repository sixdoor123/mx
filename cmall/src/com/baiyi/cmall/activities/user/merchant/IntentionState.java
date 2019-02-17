package com.baiyi.cmall.activities.user.merchant;

/**
 * 供应意向状态判断
 * 
 * @author sunxy
 *
 */
public class IntentionState {
	private IntentionState(){}
	/**
	 * 创建
	 */
	public static final int Created=1;	
	/**
	 * 交流中
	 */
	public static final int Communicating=2;
	/**
	 * 拒绝
	 */
	public static final int Refused=3;	
	/**
	 * 删除
	 */
	public static final int Deleted=-9;	
	/**
	 * 已下单
	 */
	public static final int Ordered=5;
    /**
     * 用户已拒绝
     */
    public static final int RefusedUser=-3;
    /**
     * 商家已拒绝
     */
    public static final int RefusedCompany=-4;
    /**
     * 用户已取消
     */
    public static final int CancelUser=-5;
    /**
     * 商家已取消
     */
    public static final int CancelCompany=-6;

    
    /**
	 * 返回具体的状态
	 * 
	 * @param state
	 * @return
	 */
	public static CharSequence getStateName(int state) {
		
		if (state == IntentionState.Created) {
			return "创建";
		} else if (state == IntentionState.Communicating) {
			return "交流中";
		} else if (state == IntentionState.Refused) {
			return "已拒绝";
		} else if (state == IntentionState.Ordered) {
			return "已下单";
		} else if (state == IntentionState.RefusedUser) {
			return "用户已拒绝";
		}else if (state == IntentionState.RefusedCompany) {
			return "商家已拒绝";
		} else if (state == IntentionState.CancelUser) {
			return "用户已取消";
		} else if (state == IntentionState.CancelCompany) {
			return "商家已取消";
		}else if (state == IntentionState.Deleted) {
			return "已删除";
		}
		return "暂无";
	}

}
