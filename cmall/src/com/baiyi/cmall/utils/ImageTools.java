package com.baiyi.cmall.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.baiyi.cmall.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;

public class ImageTools {
	/**
	 * 根据Bitmap 得到他的字节数组
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 8;// 每次都减少10
		}
		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	/**
	 * 从字节数组得到Bitmap
	 * 
	 * @param temp
	 * @return
	 */
	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * 从字节数组得到Bitmap
	 * 
	 * @param temp
	 * @return
	 */
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

	/**
	 * 从 Drawable 得到 Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 从 Bitmap 得到 Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * 通过特定的Bitmap 得到 字节数组
	 * 
	 * @param oldPath
	 * @param bitmapMaxWidth
	 * @return
	 * @throws Exception
	 */
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
		// 在内存中创建bitmap对象，这个对象按照缩放大小创建的
		options.inSampleSize = calculateInSampleSize(options, bitmapMaxWidth,
				reqHeight);
		// System.out.println("calculateInSampleSize(options, 480, 800);==="
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

	/**
	 * 计算大小
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
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

	/**
	 * 从 Bitmap 得到 字节数组
	 * 
	 * @param image
	 * @return
	 */
	private static byte[] compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			options -= 10;// 每次都减少10
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		// ByteArrayInputStream isBm = new
		// ByteArrayInputStream(baos.toByteArray());//
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		// 把ByteArrayInputStream数据生成图片
		return baos.toByteArray();
	}

	/**
	 * 保存图片
	 * 
	 * @param b
	 * @param name
	 * @return 返回图片路径
	 * @throws Exception
	 */
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
	 * 添加图片显示渐现动画
	 * 
	 */
	public static void setImageBitmap(ImageView imageView, Bitmap bitmap,
			boolean isTran) {
		if (isTran) {
			final TransitionDrawable td = new TransitionDrawable(
					new Drawable[] {
							new ColorDrawable(android.R.color.transparent),
							new BitmapDrawable(bitmap) });
			td.setCrossFadeEnabled(true);
			imageView.setImageDrawable(td);
			td.startTransition(300);
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	/**
	 * 得到圆形图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getCicelBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 正方形的边长
		int r = 0;
		// 取最短边做边长
		if (width > height) {
			r = width;
		} else {
			r = height;
		}
		// 构建一个bitmap
		Bitmap backgroundBmp = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		// new一个Canvas，在backgroundBmp上画图
		Canvas canvas = new Canvas(backgroundBmp);
		Paint paint = new Paint();
		// 设置边缘光滑，去掉锯齿
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		Rect re = new Rect(0, 0, r, r);
		// 宽高相等，即正方形
		RectF rect = new RectF(0, 0, r, r);
		// 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
		// 且都等于r/2时，画出来的圆角矩形就是圆形
		canvas.drawRoundRect(rect, r, r, paint);
		// 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// canvas将bitmap画在backgroundBmp上
		canvas.drawBitmap(bitmap, re, rect, paint);
		// 返回已经绘画好的backgroundBmp

		return backgroundBmp;
	}

	/**
	 * 设置圆形图片
	 * 
	 * @param mBitmap
	 * @return
	 */
	public static Bitmap setCircleImage(Bitmap mBitmap) {

		if (mBitmap == null) {
			return null;
		}

		BitmapShader mBitmapShader = new BitmapShader(mBitmap, TileMode.CLAMP,
				TileMode.CLAMP);
		Paint mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);
		mBitmapPaint.setShader(mBitmapShader);

		Paint mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(Color.BLACK);
		mBorderPaint.setStrokeWidth(20);

		// int mBitmapHeight = mBitmap.getHeight();
		// int mBitmapWidth = mBitmap.getWidth();
		int mBitmapHeight = 80;
		int mBitmapWidth = 80;

		RectF mBorderRect = new RectF();
		mBorderRect.set(0, 0, mBitmapWidth, mBitmapHeight);
		// float mBorderRadius = Math.min((mBorderRect.height() - 20) / 2,
		// (mBorderRect.width() - 20) / 2);

		RectF mDrawableRect = new RectF();
		mDrawableRect.set(mBorderRect);
		// float mDrawableRadius = Math.max(mDrawableRect.height() / 2,
		// mDrawableRect.width() / 2);
		float mDrawableRadius = 40;

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
		// new一个Canvas，在backgroundBmp上画图
		Canvas canvas = new Canvas(backgroundBmp);
		// canvas.drawBitmap(backgroundBmp, mBitmapWidth / 2, mBitmapHeight / 2,
		// mBitmapPaint);
		canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight / 2, mDrawableRadius,
				mBitmapPaint);
		return backgroundBmp;
		// if (mBorderWidth != 0) {
		// canvas.drawCircle(mBitmapWidth / 2, mBitmapHeight / 2, mBorderRadius,
		// mBorderPaint);
		// }
		// updateShaderMatrix();
	}

	/**
	 * 边缘画圆
	 * 
	 * @param defaultWidth
	 * @param defaultHeight
	 * @param canvas
	 * @param radius
	 * @param color
	 * @param mBorderThickness
	 */
	public static void drawCircleBorder(int defaultWidth, int defaultHeight,
			Canvas canvas, int radius, int color, float mBorderThickness) {
		Paint paint = new Paint();
		/* 去锯齿 */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(color);
		/* 设置paint的 style 为STROKE：空心 */
		paint.setStyle(Paint.Style.STROKE);
		/* 设置paint的外框宽度 */
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
	}

	/**
	 * 对图片进行180度的翻转
	 * 
	 * @param resId
	 * @return
	 */
	private Drawable overTurn(Bitmap bitmap) {

		Matrix matrix = new Matrix();
		// 设置图像的旋转角度
		matrix.setRotate(180);
		// 旋转图像，并生成新的Bitmap对像
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		// 重新在ImageView组件中显示旋转后的图像
		return new BitmapDrawable(bitmap);
	}

	/**
	 * 通过资源ID 转换成图片
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap resIdToBitmap(Context context, int resId) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
		return bitmap;
	}

	/**
	 * 获取圆角位图的方法
	 * 
	 * @param bitmap
	 *            需要转化成圆角的位图
	 * @param pixels
	 *            圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 获取bitmap的大小
	 * 
	 * @param bitmap
	 * @return
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
																			// 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	/**
	 * 根据Bitmap 得到他的字节数组
	 * 
	 * @param bitmap
	 * @return
	 */
	public static ByteArrayOutputStream getBitmapByteStream(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 8;// 每次都减少10
		}
		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos;
	}

	/**
	 * bit
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String bitmapToBase64(byte[] bitmapBytes) {

		String result = null;
		if (bitmapBytes != null) {
			result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
		}
		return result;
	}

	/**
	 * 获取图片显示
	 * @param context
	 * @param view
	 * @param imageUrl
	 */
	public static void getNormalImage(Context context, ImageView view,
			String imageUrl) {
		if (TextViewUtil.isStringEmpty(imageUrl)) {
			return;
		}
		Drawable drawable = context.getResources().getDrawable(
				R.drawable.icon_no);
		Picasso.with(context).load(imageUrl)
				.error(R.drawable.icon_no).placeholder(drawable).fit()
				.centerCrop().into(view);
	}
	
	/**
	 * 放置圆形的图片
	 * @param context
	 * @param iv
	 * @param imgUrl
	 * @param resource
	 */
    public static void getCircleImage(Context context,ImageView iv,String imgUrl,int Resource) {
    	
        if (Utils.isStringEmpty(imgUrl)) {
            iv.setImageResource(Resource);
            return;
        }
        Drawable drawable = context.getResources().getDrawable(Resource);
        Picasso.with(context).load(imgUrl)
                .transform(new CircleTransform())
                .error(Resource)
                .placeholder(drawable)
                .fit().centerCrop().into(iv);
    }
}
