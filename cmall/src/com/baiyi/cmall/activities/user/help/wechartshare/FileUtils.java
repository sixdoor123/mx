package com.baiyi.cmall.activities.user.help.wechartshare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {

	/**
	 * ��Bitmap�����ڱ���
	 * 
	 * @param mBitmap
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public String saveBitmap(Bitmap mBitmap) {

		try {
			String sdCardPath = "";
			if (SystemUtils.hasSdCard()) {
				sdCardPath = Environment.getExternalStorageDirectory()
						.getPath();
			} else {

			}

			String filePath = sdCardPath + "/" + "myImg/";

			Date date = new Date(System.currentTimeMillis());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// ʱ���ʽ-��ʾ��ʽ

			String imgPath = filePath + sdf.format(date) + ".png";

			File file = new File(filePath);

			if (!file.exists()) {
				file.mkdirs();
			}
			File imgFile = new File(imgPath);

			if (!imgFile.exists()) {
				imgFile.createNewFile();
			}

			FileOutputStream fOut = new FileOutputStream(imgFile);

			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

			fOut.flush();

			if (fOut != null) {

				fOut.close();
			}
			return imgPath;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
