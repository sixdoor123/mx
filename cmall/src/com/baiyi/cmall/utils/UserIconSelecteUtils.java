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
 * ͷ��ѡ�񹤾���
 * 
 * @author sunxy
 * 
 */
public class UserIconSelecteUtils {

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���

	// Բ��ͼƬ
	private Bitmap circleBitmap;

	// ����һ���Ե�ǰʱ��Ϊ���Ƶ��ļ�
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

	// ��ʾ�Ի��򷽷�
	public void showDialog(final Activity activity) {
		new AlertDialog.Builder(context)
				.setTitle("ͷ������")
				.setItems(R.array.icon_item,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									dialog.dismiss();
									// ����ϵͳ�����չ���
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									// ָ������������պ���Ƭ�Ĵ���·��
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
						}).setNegativeButton("����", null).show();
	}

	public void startPhotoZoom(Uri uri, int size, Activity activity) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// cropΪtrue�������ڿ�����intent��������ʾ��view���Լ���
		intent.putExtra("crop", "true");

		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY �Ǽ���ͼƬ�Ŀ��
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
	 * ʹ��ϵͳ��ǰ���ڼ��Ե�����Ϊ��Ƭ������
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
	 * ִ�лص�����
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
