package com.baiyi.cmall.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

/**
 * 获取Asset文件夹下的文件
 * @author Administrator
 *
 */
public class AssetsUtil {

	public static String getFromAssets(String fileName, Context context) {
		String result = "";
		InputStream in = null;
		try {
			in = context.getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "GBK");// 你的文件的编码
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
