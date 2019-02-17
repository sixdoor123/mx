package com.baiyi.jj.app.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtils {

	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}

	public static Bitmap getBitmapToByte(byte[] data) {
		if (data != null) {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inSampleSize = 1;

			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length, opt);
			int bitmapSize = opt.outHeight * opt.outWidth * 4;// pixels*3 if it's RGB and pixels*4
																// if it's ARGB
			if (bitmapSize > 1000 * 1200)
				opt.inSampleSize = 2;
			opt.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
			return bm;
		} else {
			return null;
		}
	}
}
