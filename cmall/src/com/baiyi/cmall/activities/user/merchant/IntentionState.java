package com.baiyi.cmall.activities.user.merchant;

/**
 * ��Ӧ����״̬�ж�
 * 
 * @author sunxy
 *
 */
public class IntentionState {
	private IntentionState(){}
	/**
	 * ����
	 */
	public static final int Created=1;	
	/**
	 * ������
	 */
	public static final int Communicating=2;
	/**
	 * �ܾ�
	 */
	public static final int Refused=3;	
	/**
	 * ɾ��
	 */
	public static final int Deleted=-9;	
	/**
	 * ���µ�
	 */
	public static final int Ordered=5;
    /**
     * �û��Ѿܾ�
     */
    public static final int RefusedUser=-3;
    /**
     * �̼��Ѿܾ�
     */
    public static final int RefusedCompany=-4;
    /**
     * �û���ȡ��
     */
    public static final int CancelUser=-5;
    /**
     * �̼���ȡ��
     */
    public static final int CancelCompany=-6;

    
    /**
	 * ���ؾ����״̬
	 * 
	 * @param state
	 * @return
	 */
	public static CharSequence getStateName(int state) {
		
		if (state == IntentionState.Created) {
			return "����";
		} else if (state == IntentionState.Communicating) {
			return "������";
		} else if (state == IntentionState.Refused) {
			return "�Ѿܾ�";
		} else if (state == IntentionState.Ordered) {
			return "���µ�";
		} else if (state == IntentionState.RefusedUser) {
			return "�û��Ѿܾ�";
		}else if (state == IntentionState.RefusedCompany) {
			return "�̼��Ѿܾ�";
		} else if (state == IntentionState.CancelUser) {
			return "�û���ȡ��";
		} else if (state == IntentionState.CancelCompany) {
			return "�̼���ȡ��";
		}else if (state == IntentionState.Deleted) {
			return "��ɾ��";
		}
		return "����";
	}

}
