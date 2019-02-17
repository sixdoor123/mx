package com.baiyi.jj.app.imgtools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.baiyi.jj.app.utils.TLog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Environment;
import android.widget.ImageView;

/**
 * @author tangkun
 * 
 */
public abstract class ImageTools {

	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // ????§Ø?????????????????100kb,??????????
			baos.reset();// ????baos?????baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// ???????options%?????????????????baos??
			options -= 8;// ??¦Æ?????10
		}
		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	public static byte[] getSDByte(String path) {
		FileInputStream fis;
		ByteArrayOutputStream baos = null;
		try {
			fis = new FileInputStream(new File(path));
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) > 0) {
				baos.write(buffer, 0, count);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	public static Bitmap getBitmapToByte(byte[] data) {
		if (data != null) {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inSampleSize = 1;

			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length, opt);
			int bitmapSize = opt.outHeight * opt.outWidth * 4;// pixels*3 if
																// it's RGB and
																// pixels*4
																// if it's ARGB
			if (bitmapSize > 1000 * 1200)
				opt.inSampleSize = 2;
			opt.inJustDecodeBounds = false;
			return BitmapFactory.decodeByteArray(data, 0, data.length, opt);
		} else {
			return null;
		}
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	public static byte[] getThumbUploadPath(String oldPath, int bitmapMaxWidth)
			throws Exception {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(oldPath, options);
		int height = options.outHeight;
		int width = options.outWidth;
		int reqHeight = 0;
		int reqWidth = bitmapMaxWidth;
		reqHeight = (reqWidth * height) / width;
		// ??????§Õ???bitmap??????????????????§³??????
		options.inSampleSize = calculateInSampleSize(options, bitmapMaxWidth,
				reqHeight);
		// TLog.d("calculateInSampleSize(options, 480, 800);==="
		// + calculateInSampleSize(options, 480, 800));
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(oldPath, options);
		// Log.e("asdasdas", "reqWidth->"+reqWidth+"---reqHeight->"+reqHeight);
		// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new
		// Date());
		return compressImage(Bitmap.createScaledBitmap(bitmap, bitmapMaxWidth,
				reqHeight, false));
		// return saveImg(bbb, MD5.getMD5(timeStamp));
	}

	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	private static byte[] compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// ?????????????????100?????????????????????????baos??
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // ????§Ø?????????????????100kb,??????????
			options -= 10;// ??¦Æ?????10
			baos.reset();// ????baos?????baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ???????options%?????????????????baos??
		}
		// ByteArrayInputStream isBm = new
		// ByteArrayInputStream(baos.toByteArray());//
		// ????????????baos????ByteArrayInputStream??
		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		// ??ByteArrayInputStream??????????
		return baos.toByteArray();
	}

	public static String saveImg(Bitmap b, String name) throws Exception {
		String path = Environment.getExternalStorageDirectory().getPath()
				+ File.separator + "chuang/imgs/";
		File mediaFile = new File(path + File.separator + name + ".jpg");
		if (mediaFile.exists()) {
			mediaFile.delete();
		}
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		mediaFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(mediaFile);
		b.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
		b.recycle();
		b = null;
		System.gc();
		return mediaFile.getPath();
	}

	/**
	 * ???????????????
	 * 
	 */
//	public static void setImageBitmap(ImageView imageView, Bitmap bitmap,
//			boolean isTran) {
//		if (isTran) {
//			final TransitionDrawable td = new TransitionDrawable(
//					new Drawable[] {
//							new ColorDrawable(android.R.color.transparent),
//							new BitmapDrawable(bitmap) });
//			td.setCrossFadeEnabled(true);
//			imageView.setImageDrawable(td);
//			td.startTransition(300);
//		} else {
//			imageView.setImageBitmap(bitmap);
//		}
//	}

	public static Bitmap getCicelBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// ?????¦Å???
		int r = 0;
		// ??????????
		if (width > height) {
			r = height;
		} else {
			r = width;
		}
		// ???????bitmap
		Bitmap backgroundBmp = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		// new???Canvas????backgroundBmp????
		Canvas canvas = new Canvas(backgroundBmp);
		Paint paint = new Paint();
		// ????????????????
		paint.setAntiAlias(true);
		// ???????????????
		RectF rect = new RectF(0, 0, r, r);
		// ????????rect??????????¦²??????X??????????Y?????????
		// ???????r/2????????????????¦Î??????
		canvas.drawRoundRect(rect, r / 2, r / 2, paint);
		// ?????????????????????SRC_IN??SRC??????????????????????
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// canvas??bitmap????backgroundBmp??
		canvas.drawBitmap(bitmap, null, rect, paint);
		// ????????œü???backgroundBmp
		return backgroundBmp;
	}

	public static Bitmap setCircleImage(Bitmap mBitmap) {

		if (mBitmap == null) {
			return null;
		}

		BitmapShader mBitmapShader = new BitmapShader(mBitmap,
				Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		Paint mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);
		mBitmapPaint.setShader(mBitmapShader);

		Paint mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(Color.BLACK);
		mBorderPaint.setStrokeWidth(20);

		int mBitmapHeight = mBitmap.getHeight();
		int mBitmapWidth = mBitmap.getWidth();

		RectF mBorderRect = new RectF();
		mBorderRect.set(0, 0, mBitmapWidth, mBitmapHeight);
		// float mBorderRadius = Math.min((mBorderRect.height() - 20) / 2,
		// (mBorderRect.width() - 20) / 2);

		RectF mDrawableRect = new RectF();
		mDrawableRect.set(mBorderRect);
		float mDrawableRadius = Math.min(mDrawableRect.height() / 2,
				mDrawableRect.width() / 2);

		float scale;
		float dx = 0;
		float dy = 0;

		Matrix mShaderMatrix = new Matrix();
		mShaderMatrix.set(null);

		if (20 * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
			scale = mDrawableRect.height() / (float) mBitmapHeight;
			dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
		} else {
			scale = mDrawableRect.width() / (float) mBitmapWidth;
			dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
		}

		mShaderMatrix.setScale(scale, scale);
		mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left,
				(int) (dy + 0.5f) + mDrawableRect.top);

		mBitmapShader.setLocalMatrix(mShaderMatrix);

		Bitmap backgroundBmp = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight,
				Config.ARGB_8888);
		// new???Canvas????backgroundBmp????
		Canvas canvas = new Canvas(backgroundBmp);
		canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight / 2, mDrawableRadius,
				mBitmapPaint);
		return backgroundBmp;
		// if (mBorderWidth != 0) {
		// canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight / 2, mBorderRadius,
		// mBorderPaint);
		// }
		// updateShaderMatrix();
	}
}
