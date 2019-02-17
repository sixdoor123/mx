package com.baiyi.cmall.activities.user.help.wechartshare;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;

public class SystemUtils {
	private static Activity mActivity = null;

	public SystemUtils(Activity activity) {
		mActivity = activity;
	}

	/** �򿪱���ͼƬ */
	public void openLocalPic() {

		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("image/*");
		mActivity.startActivityForResult(i, 0);

	}

	/** ����� */
	public void openCamera() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		mActivity.startActivityForResult(intent, 1);

	}

	/** ��΢�� */
	public void openWxx(String packageName) {

		PackageManager manager = mActivity.getPackageManager();

		Intent intent = manager.getLaunchIntentForPackage(packageName);

		mActivity.startActivity(intent);
	}

	/** �Ƿ�װ΢�� */
	public boolean isInstallWx(String packageName) {
		try {

			PackageManager manager = mActivity.getPackageManager();

			PackageInfo info = manager.getPackageInfo(packageName,
					PackageManager.GET_ACTIVITIES);

			if (info != null) {

				return true;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * �Ƿ����SDCard
	 * 
	 */
	public static final boolean hasSdCard() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		}
		return false;
	}
}
