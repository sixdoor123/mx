package com.baiyi.cmall.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baiyi.cmall.R;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * 头像选择工具类
 * 
 * @author sunxy
 * 
 */
public class UserIconSelecteUtils {

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	// 圆角图片
	private Bitmap circleBitmap;

	// 创建一个以当前时间为名称的文件
	File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());
	private Context context;
	private static UserIconSelecteUtils utils;

	private UserIconSelecteUtils(Context context) {
		this.context = context;
	}

	public static UserIconSelecteUtils getInsetence(Context context) {
		if (null == utils) {
			utils = new UserIconSelecteUtils(context);
		}
		return utils;
	}

	// 提示对话框方法
	public void showDialog(final Activity activity) {
		new AlertDialog.Builder(context)
				.setTitle("头像设置")
				.setItems(R.array.icon_item,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									dialog.dismiss();
									// 调用系统的拍照功能
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									// 指定调用相机拍照后照片的储存路径
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(tempFile));
									activity.startActivityForResult(intent,
											PHOTO_REQUEST_TAKEPHOTO);
									break;
								case 1:
									dialog.dismiss();
									Intent intent1 = new Intent(
											Intent.ACTION_PICK, null);
									intent1.setDataAndType(
											MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											"image/*");
									activity.startActivityForResult(intent1,
											PHOTO_REQUEST_GALLERY);
									break;

								default:
									break;
								}
							}
						}).setNegativeButton("返回", null).show();
	}

	public void startPhotoZoom(Uri uri, int size, Activity activity) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	//
	public void setBitmap(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			Bitmap bitmap = ImageTools.drawableToBitmap(drawable);
			setCircleBitmap(ImageTools.setCircleImage(bitmap));
		}
	}

	/**
	 * 使用系统当前日期加以调整作为照片的名称
	 * 
	 * @return
	 */
	public String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 执行回调方法
	 * 
	 * @param selecte
	 */
	public void executeMethed(int selecte, Activity activity, Intent data) {
		switch (selecte) {
		case PHOTO_REQUEST_TAKEPHOTO:
			startPhotoZoom(Uri.fromFile(tempFile), 150, activity);
			break;

		case PHOTO_REQUEST_GALLERY:
			if (data != null)
				startPhotoZoom(data.getData(), 150, activity);
			break;

		case PHOTO_REQUEST_CUT:
			if (data != null)
				setBitmap(data);
			break;
		}
	}

	public Bitmap getCircleBitmap() {
		return circleBitmap;
	}

	public void setCircleBitmap(Bitmap circleBitmap) {
		this.circleBitmap = circleBitmap;
	}

}
