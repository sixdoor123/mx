package com.baiyi.cmall.activities.base;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity.ActivityStackControlUtil;
import com.baiyi.cmall.activities.main.MainTabActivity;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.views.LoadingBar;

/**
 * ҳ����ת
 * 
 * @author sunxy
 * 
 */
public class BaseGoActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTranslucentStatus();
		super.onCreate(savedInstanceState);
	}

	/***************************** �ֻ�״̬������ **************************************/
	/**
	 * ����״̬������״̬
	 */
	@SuppressLint("InlinedApi")
	private void setTranslucentStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);
		}
		SystemStatusManager tintManager = new SystemStatusManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(0);// ״̬���ޱ���
	}

	/*********************************** ������ ************************************/
	// ������
	private LoadingBar loadingBar = null;

	/**
	 * ���ؽ��ȿ�ʼ
	 */

	public void startLoading() {
		if (null == loadingBar) {
			loadingBar = new LoadingBar(this);
			loadingBar.start();
		}
	}

	/**
	 * ���ؽ��ȿ�ʼ(���޸Ľ�����ʾ��Ϣ)
	 */
	public void startLoading(String msg) {
		if (loadingBar == null) {
			loadingBar = new LoadingBar(this);
			loadingBar.start();
			loadingBar.setProgressInfo(msg);
		}
	}

	/**
	 * ����������ʾ
	 */
	public void stopLoading() {
		if (loadingBar != null) {
			loadingBar.stop();
			loadingBar = null;
		}
	}

	
	/****************************************** ������ת ********************************/

	public void goActivity(Class<?> clazz) {
		Intent intent = new Intent();
		intent.setClass(this, clazz);
		startActivityForResult(intent, 5);
	}

	public void goActivity(Class<?> clazz, int temp) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("temp", temp);
		startActivityForResult(intent, 6);
	}

	public void goActivity(Class<?> clazz, int temp, int id) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("temp", temp);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	/**
	 * �༭�ɹ���Ϣ��ת
	 * 
	 * @param clazz
	 * @param id
	 * @param isShow
	 */
	public void goActivity(Class<?> clazz, int id, boolean isShow) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("intent", id);
		intent.putExtra("show", isShow);
		startActivityForResult(intent, NumEntity.STATE_EDIT);
	}

	/**
	 * �༭����
	 * 
	 * @param clazz
	 * @param isShow
	 * @param intention
	 * @param binaryValue
	 * @param isEditPurNAme
	 */
	public void goToActivity(Class<?> clazz, boolean isShow, int intention, String binaryValue, boolean isEditPurNAme) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("show", isShow);
		intent.putExtra("intent", intention);
		intent.putExtra("state", binaryValue);
		intent.putExtra("is_edit", isEditPurNAme);
		startActivityForResult(intent, 10);
	}

	public void goActivity(Class<?> clazz, String temp, int id) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("temp", temp);
		intent.putExtra("id", id);
		startActivity(intent);
	}

	public void goActivity(String temp, Class<?> clazz) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("temp", temp);
		startActivityForResult(intent, 5);
	}

	public void goActivity(Class<?> clazz, String code, String inString) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("temp", code);
		intent.putExtra("arg", inString);
		startActivityForResult(intent, 5);
	}

	public void goActivity(IntentionProviderDetailInfo info, Class<?> clazz, int inString) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("key", info);
		intent.putExtra("state", inString);
		startActivity(intent);
	}

	public void goActivity(Class<?> clazz, String code, String inString, int state) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("temp", code);
		intent.putExtra("arg", inString);
		intent.putExtra("sta", state);
		startActivity(intent);
	}

	public void goToActivity(Class<?> clazz, String code, String inString) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("temp", code);
		intent.putExtra("arg", inString);
		startActivityForResult(intent, 1);
	}

	public void goActivity(Class<?> clazz, String inString) {

		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("arg", inString);
		startActivity(intent);
	}

	/**
	 * Я�����ݽ�����ת
	 * 
	 * @param position
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	public void goActivity(GoodsSourceInfo info, Class clazz) {
		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("key", info);
		startActivityForResult(intent, 1);
	}

	/**
	 * Я�����ݽ�����ת
	 * 
	 * @param position
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	public void goActivity(GoodsSourceInfo info, Class clazz, int temp) {
		Intent intent = new Intent();

		intent.setClass(this, clazz);
		intent.putExtra("key", info);
		intent.putExtra("temp", temp);
		intent.putExtra("state", temp);
		startActivityForResult(intent, 2);
	}

	/**
	 * Я�����ݽ�����ת
	 * 
	 * @param position
	 * @param clazz
	 */
	public void goActivity(String id, @SuppressWarnings("rawtypes") Class clazz, int state) {
		Intent intent = new Intent();
		// ��ת����Ӧ����
		intent.setClass(this, clazz);
		intent.putExtra("temp", id);
		intent.putExtra("state", state);
		startActivityForResult(intent, 5);
	}

	public void goActivity(String IntentionID, String id, @SuppressWarnings("rawtypes") Class clazz, int state,
			int idState, boolean isEditDetailEnable, boolean orderState) {
		Intent intent = new Intent();
		// ��ת����Ӧ����
		intent.setClass(this, clazz);
		intent.putExtra("IntentionID", IntentionID);
		intent.putExtra("temp", id);
		intent.putExtra("idState", idState);
		intent.putExtra("state", state);
		intent.putExtra("orderState", orderState);
		intent.putExtra("isEditDetailEnable", isEditDetailEnable);
		startActivityForResult(intent, 5);
	}

	/**
	 * Я�����ݽ�����ת
	 * 
	 * @param position
	 * @param clazz
	 */
	public void goActivity(ArrayList<GoodsSourceInfo> infos, @SuppressWarnings("rawtypes") Class clazz) {
		Intent intent = new Intent();
		// ��ת����Ӧ����
		intent.setClass(this, clazz);
		intent.putExtra("data", infos);
		startActivity(intent);
	}

	/**
	 * Я�����ݽ�����ת
	 * 
	 * @param position
	 * @param clazz
	 */
	public void goActivity(GoodsSourceInfo info, Context context, @SuppressWarnings("rawtypes") Class clazz, int temp) {
		Intent intent = new Intent();
		// ��ת����Ӧ����
		intent.setClass(context, clazz);
		intent.putExtra("key", info);
		intent.putExtra("temp", temp);
		startActivityForResult(intent, 1);
	}

	public void goActivity(GoodSOriginInfo info, @SuppressWarnings("rawtypes") Class clazz) {
		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("key", info);
		startActivityForResult(intent, 1);
	}

	public void goActivity(IntentionProviderDetailInfo info, @SuppressWarnings("rawtypes") Class clazz) {
		Intent intent = new Intent();
		// ��ת����Ӧ����
		intent.setClass(this, clazz);
		intent.putExtra("key", info);
		startActivity(intent);
	}

	/**
	 * ��ת��ָ����Tab
	 * 
	 * @param pagIndex
	 *            �Ǳ�
	 */
	public void backHomePage(String pagIndex) {
		Intent intent = new Intent();
		intent.setClass(this, MainTabActivity.class);
		intent.putExtra("key", pagIndex);
		startActivity(intent);
		ActivityStackControlUtil.finishProgram();
		finish();
	}

	/**
	 * Я�����ݽ�����ת
	 * 
	 * @param position
	 * @param clazz
	 */
	public void goActivitys(ArrayList<?> infos, Class clazz) {
		Intent intent = new Intent();
		intent.setClass(this, clazz);
		intent.putExtra("data", infos);
		startActivity(intent);
	}
}
