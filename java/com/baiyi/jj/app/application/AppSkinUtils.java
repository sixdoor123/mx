/**
 * 
 */
package com.baiyi.jj.app.application;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author tangkun
 *
 */
public class AppSkinUtils {
	
	public static final String Tag = AppSkinUtils.class.getName();
	
	public static final int Skin_Version =45;

	public static void copyLocationDataBase(Context context,
			String dataBaseFile, int rawId)
			throws IOException {
		OutputStream myOutput = new FileOutputStream(dataBaseFile);
		byte[] buffer = new byte[2048];
		int length;
		int hasWriteLength = 0;

		InputStream myInput = context.getResources().openRawResource(rawId);
		while ((length = myInput.read(buffer)) > 0) {
			hasWriteLength += length;
			myOutput.write(buffer, 0, length);
		}
		myInput.close();
		myInput = null;


		myOutput.flush();
		myOutput.close();
	}

}
