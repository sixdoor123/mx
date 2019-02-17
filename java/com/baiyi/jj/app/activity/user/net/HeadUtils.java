package com.baiyi.jj.app.activity.user.net;

import android.graphics.Bitmap;
import com.baiyi.jj.app.application.CmsApplication;

public class HeadUtils {

	private static HeadUtils instance = null;
	private static Bitmap headBitmap = null;

	public static final String getHeadUrl() {
		if (CmsApplication.getUserInfoEntity() == null) {
			return "";
		}
		return NetUrl.getPhoto(CmsApplication.getUserInfoEntity().getMID());
	}

	public static HeadUtils getInstance() {
		if (instance == null) {
			instance = new HeadUtils();
		}
		return instance;
	}

	public Bitmap getHeadBitmap() {
		return headBitmap;
	}

	public static void resetHeadBitmap() {
		headBitmap = null;
	}

}
