package com.baiyi.cmall.utils;

import java.util.Random;

import com.baiyi.cmall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * 随机生成验证码
 * 
 * @author lizl
 * 
 */
public class Code {

	private Context context;
	/**
	 * 验证码所显示的数据源
	 */
	private static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
			'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static Code bmpCode;

	public Code(Context context) {
		this.context = context;
	}

	public static Code getInstance(Context context) {
		if (bmpCode == null)
			bmpCode = new Code(context);
		return bmpCode;
	}

	// default settings
	private static final int DEFAULT_CODE_LENGTH = 4;
	private static final int DEFAULT_FONT_SIZE = 40;
	private static final int DEFAULT_LINE_NUMBER = 2;
	private static final int BASE_PADDING_LEFT = 30;
	private static final int RANGE_PADDING_LEFT = 150;
	private static final int BASE_PADDING_TOP = 30;
	private static final int RANGE_PADDING_TOP = 40;
	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT = 80;

	// settings decided by the layout xml
	// canvas width and height
	private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;

	// random word space and pading_top
	private int base_padding_left = BASE_PADDING_LEFT,
			range_padding_left = RANGE_PADDING_LEFT,
			base_padding_top = BASE_PADDING_TOP,
			range_padding_top = RANGE_PADDING_TOP;

	// number of chars, lines; font size
	private int codeLength = DEFAULT_CODE_LENGTH,
			line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

	// variables
	private String code;
	private int padding_left, padding_top;
	private Random random = new Random();

	private int[] bitmaps = new int[] { R.drawable.aaaa, R.drawable.bbbb,
			R.drawable.cccc, R.drawable.dddd };

	// 验证码图片
	public Bitmap createBitmap() {
		padding_left = 0;
		Paint paint = new Paint();
		Bitmap bp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas c = new Canvas(bp);

		code = createCode();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.outWidth = width;
		options.outHeight = height;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				bitmaps[random.nextInt(4)],options);
		
		Matrix matrix = new Matrix();
		matrix.setScale(width, height);
		c.drawBitmap(bitmap, matrix, paint);

		paint.setTextSize(font_size);

		for (int i = 0; i < code.length(); i++) {
			randomTextStyle(paint);
			randomPadding();
			c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
		}

		for (int i = 0; i < line_number; i++) {
			drawLine(c, paint);
		}

		c.save(Canvas.ALL_SAVE_FLAG);// 保存
		c.restore();//
		return bp;
	}

	/**
	 * 获取验证码字符窜 变成小写code.toLowerCase();变成大写code.toUpperCase();
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 创建验证码
	 * 
	 * @return
	 */
	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	/**
	 * 画辅助线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = random.nextInt(width);
		int startY = random.nextInt(height);
		int stopX = random.nextInt(width);
		int stopY = random.nextInt(height);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}

	private int randomColor() {
		return randomColor(1);
	}

	/**
	 * 随机生成颜色
	 * 
	 * @param rate
	 * @return
	 */
	private int randomColor(int rate) {
		int red = random.nextInt(256) / rate;
		int green = random.nextInt(256) / rate;
		int blue = random.nextInt(256) / rate;
		return Color.rgb(red, green, blue);
	}

	/**
	 * 随机生成字体类型
	 * 
	 * @param paint
	 */
	private void randomTextStyle(Paint paint) {
		int color = randomColor();
		paint.setColor(color);
		paint.setFakeBoldText(random.nextBoolean()); // true为粗体，false为非粗体
		float skewX = random.nextInt(11) / 10;
		skewX = random.nextBoolean() ? skewX : -skewX;
		paint.setTextSkewX(skewX); // float类型参数，负数表示右斜，整数左斜
		// paint.setUnderlineText(true); //true为下划线，false为非下划线
		// paint.setStrikeThruText(true); //true为删除线，false为非删除线
	}

	/**
	 * 设置边距控制显示的位置
	 */
	private void randomPadding() {
		padding_left += base_padding_left;
		padding_top = base_padding_top + random.nextInt(range_padding_top);
	}

}
