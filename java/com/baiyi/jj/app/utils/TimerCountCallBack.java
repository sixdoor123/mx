package com.baiyi.jj.app.utils;

import android.os.CountDownTimer;

/**
 * ����ʱ
 * @author wangf
 *
 */
public class TimerCountCallBack extends CountDownTimer {
	private CallBack callBack;
	
	//����Ϊ��ʱ���ͼ�ʱ��ʱ��������λΪ����
	public TimerCountCallBack(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	public void setCallBack(CallBack callBack){
		if(callBack!=null){
			this.callBack=callBack;
		}
	}
	
	//��ʱ������ʾ
	@Override
	public void onTick(long millisUntilFinished) {
		callBack.callBackOnTick(millisUntilFinished);
	}

	//��ʱ���ʱ����
	@Override
	public void onFinish() {
		callBack.callBackOnFinish();
	}
	
	
	public interface CallBack
	{
		public void callBackOnTick(long millisUntilFinished);
		public void callBackOnFinish();
	}

}
