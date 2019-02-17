package com.baiyi.cmall.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

/**
 * ��ȡAsset�ļ����µ��ļ�
 * @author Administrator
 *
 */
public class AssetsUtil {

	public static String getFromAssets(String fileName, Context context) {
		String result = "";
		InputStream in = null;
		try {
			in = context.getResources().getAssets().open(fileName);
			// ��ȡ�ļ����ֽ���
			int lenght = in.available();
			// ����byte����
			byte[] buffer = new byte[lenght];
			// ���ļ��е����ݶ���byte������
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "GBK");// ����ļ��ı���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

}
