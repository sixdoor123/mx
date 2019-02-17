package com.baiyi.cmall.activities.user.login.utils;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.core.file.SimpleNormalFileIO;

import android.os.Environment;

/**
 * �����Ա���ĵ�·��
 * 
 * @author sunxy
 */
public final class BaseAddressUtils {

	private static String path = null;
	private static String fileName = null;
	private static SimpleNormalFileIO fileIO = null;

	static {
		if (isExistPhoneStore()) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath() + Config.Root_File_Path;
			fileName = XmlName.Base_Address + ".txt";
		}
	}

	private BaseAddressUtils() {
	}

	/**
	 * ����
	 * 
	 * @param address
	 */
	public static boolean saveBaseUrl(String address) {
		if (address == null || "".equals(address)) {
			return false;
		}
		if (!address.endsWith("/")) {
			address += "/";
		}
		fileIO = new SimpleNormalFileIO();
		boolean b = fileIO.create(path, fileName);
		if (b) {
			fileIO.write(path, fileName, address.getBytes(), false);
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ
	 * 
	 * @return
	 */
	public static String readBaseUrl() {
		byte[] bytes = fileIO.read(path, fileName);
		String address = "";
		if (null != bytes && bytes.length > 0) {
			address = new String(bytes, 0, bytes.length);
			return address;
		}
		return null;
	}

	/**
	 * �ж��ֻ�λ�ô洢�豸�Ƿ����
	 * 
	 * @return
	 */
	private static boolean isExistPhoneStore() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
}
