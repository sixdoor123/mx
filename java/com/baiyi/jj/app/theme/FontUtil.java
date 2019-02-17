/**
 * 
 */
package com.baiyi.jj.app.theme;

import android.accounts.Account;
import android.content.Context;

import com.baiyi.core.file.Preference;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.accont.AccountManager;

/**
 * @author tangkun
 * 
 */
public class FontUtil {
	// С����
	public static final int Font_Min = 0;
	// �����壨Ĭ�ϣ�
	public static final int Font_middle = 1;
	// ������
	public static final int Font_Max = 2;
	// ��������
	public static final int Font_Big_Max = 3;

	private static boolean isFontChange = false;

	// ƫ����С�ĺʹ��
	public static int marginMin = 5;
	public static int marginMidd = 5;
	public static int marginMax = 6;
	public static int marginMaxMore = 6;

	// ƫ����С�ĺʹ��
	public static int lineMarginMin = 13;
	public static int lineMarginMax = 15;

	/**
	 * ժҪ��ͼժҪ�����������С�ı�ı�
	 */
	public static int getAbcLines() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 2;
		} else if (type == Font_middle) {
			return 2;
		} else if (type == Font_Max) {
			return 1;
		} else if (type == Font_Big_Max) {
			return 1;
		}
		return 2;
	}

	public static int getImageMaigrn() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 2;
		} else if (type == Font_middle) {
			return 2;
		} else if (type == Font_Max) {
			return 3;
		} else if (type == Font_Big_Max) {
			return 3;
		}
		return 2;
	}

	
	/**
	 * ժҪƫ�����������С�ı�ı�
	 */
	public static int getLineMaigrn5_6() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 5;
		} else if (type == Font_middle) {
			return 5;
		} else if (type == Font_Max) {
			return 6;
		} else if (type == Font_Big_Max) {
			return 6;
		}
		return 5;
	}
	public static int getLineMaigrn6_7() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 6;
		} else if (type == Font_middle) {
			return 6;
		} else if (type == Font_Max) {
			return 7;
		} else if (type == Font_Big_Max) {
			return 7;
		}
		return 6;
	}
	/**
	 * ժҪƫ�����������С�ı�ı�
	 */
	public static int getLineMaigrn4_5() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 4;
		} else if (type == Font_middle) {
			return 4;
		} else if (type == Font_Max) {
			return 5;
		} else if (type == Font_Big_Max) {
			return 5;
		}
		return 4;
	}
	public static int getLineMaigrn3_4() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 3;
		} else if (type == Font_middle) {
			return 3;
		} else if (type == Font_Max) {
			return 4;
		} else if (type == Font_Big_Max) {
			return 4;
		}
		return 3;
	}
	public static int getLineMaigrn5_7() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 5;
		} else if (type == Font_middle) {
			return 5;
		} else if (type == Font_Max) {
			return 7;
		} else if (type == Font_Big_Max) {
			return 7;
		}
		return 5;
	}
	
	/**
	 * ժҪƫ�����������С�ı�ı�
	 */
	public static int getTitleMaigrn3line() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 5;
		} else if (type == Font_middle) {
			return 5;
		} else if (type == Font_Max) {
			return 5;
		} else if (type == Font_Big_Max) {
			return 3;
		}
		return 5;
	}
	/**
	 * ժҪƫ�����������С�ı�ı�
	 */
	public static int getTitleMaigrn3_4() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 3;
		} else if (type == Font_middle) {
			return 3;
		} else if (type == Font_Max) {
			return 4;
		} else if (type == Font_Big_Max) {
			return 4;
		}
		return 3;
	}
	public static int getTitleMaigrn2_3() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return 2;
		} else if (type == Font_middle) {
			return 2;
		} else if (type == Font_Max) {
			return 3;
		} else if (type == Font_Big_Max) {
			return 3;
		}
		return 2;
	}
	/**
	 * ���е�ƫ����
	 */
	public static int getTWOMaigrn() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return BaseActivity.getDensity_int(20);
		} else if (type == Font_middle) {
			return BaseActivity.getDensity_int(16);
		} else if (type == Font_Max) {
			return BaseActivity.getDensity_int(11);
		} else if (type == Font_Big_Max) {
			return BaseActivity.getDensity_int(10);
		}
		return BaseActivity.getDensity_int(16);
	}
	/**
	 * һ�е�ƫ����
	 */
	public static int getONEMaigrn() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return BaseActivity.getDensity_int(39);
		} else if (type == Font_middle) {
			return BaseActivity.getDensity_int(37);
		} else if (type == Font_Max) {
			return BaseActivity.getDensity_int(35);
		} else if (type == Font_Big_Max) {
			return BaseActivity.getDensity_int(34);
		}
		return BaseActivity.getDensity_int(37);
	}

	/**
	 * ���Ͼ��ƫ�����������С�ı�ı�
	 */
	public static int getTopMaigrn() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return BaseActivity.getDensity_int(lineMarginMin);
		} else if (type == Font_middle) {
			return BaseActivity.getDensity_int(lineMarginMin);
		} else if (type == Font_Max) {
			return BaseActivity.getDensity_int(lineMarginMax);
		} else if (type == Font_Big_Max) {
			return BaseActivity.getDensity_int(lineMarginMax);
		}
		return BaseActivity.getDensity_int(lineMarginMin);
	}

	private static final float[] ReadTextSize = {16, 18 };

	public static float geReadTextSize(Context context) {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return ReadTextSize[0];
		} else if (type == Font_middle) {
			return ReadTextSize[0];
		} else if (type == Font_Max) {
			return ReadTextSize[1];
		} else if (type == Font_Big_Max) {
			return ReadTextSize[1];
		} else {
			return ReadTextSize[0];
		}
	}

	private static final float[] CommentNameTextSize = {14, 15 };

	public static float getCommentNameTextSize(Context context) {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return CommentNameTextSize[0];
		} else if (type == Font_middle) {
			return CommentNameTextSize[0];
		} else if (type == Font_Max) {
			return CommentNameTextSize[1];
		} else if (type == Font_Big_Max) {
			return CommentNameTextSize[1];
		} else {
			return CommentNameTextSize[0];
		}
	}

	private static final float[] CommentContentTextSize = { 15, 17 };

	public static float getCommentContentTextSize(Context context) {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return CommentContentTextSize[0];
		} else if (type == Font_middle) {
			return CommentContentTextSize[0];
		} else if (type == Font_Max) {
			return CommentContentTextSize[1];
		} else if (type == Font_Big_Max) {
			return CommentContentTextSize[1];
		} else {
			return CommentContentTextSize[0];
		}
	}

	// 38 34 30
//	private static final float[] AbstratTextSize = { 15, 15, 17, 17 };
	private static final float[] AbstratTextSize = { 15, 15, 18, 18 };

	/**
	 * 文章摘要的字体大小
	 * @param context
	 * @return
     */
	public static float getAbstratTextSize(Context context) {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return AbstratTextSize[0];
		} else if (type == Font_middle) {
			return AbstratTextSize[1];
		} else if (type == Font_Max) {
			return AbstratTextSize[2];
		} else if (type == Font_Big_Max) {
			return AbstratTextSize[3];
		} else {
			return AbstratTextSize[1];
		}
	}
	
	public static final float[] SearchTitleSize = { 18, 20};

	public static float getSearchTitleTextSize(Context context) {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return SearchTitleSize[0];
		} else if (type == Font_middle) {
			return SearchTitleSize[0];
		} else if (type == Font_Max) {
			return SearchTitleSize[1];
		} else if (type == Font_Big_Max) {
			return SearchTitleSize[1];
		} else {
			return SearchTitleSize[0];
		}
	}

//	public static final float[] NewsTitleSize = { 16, 18, 20, 22 };
	public static final float[] NewsTitleSize = { 15, 17, 19, 22 };

	public static float getNewsTitleTextSize(Context context) {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return NewsTitleSize[0];
		} else if (type == Font_middle) {
			return NewsTitleSize[1];
		} else if (type == Font_Max) {
			return NewsTitleSize[2];
		} else if (type == Font_Big_Max) {
			return NewsTitleSize[3];
		} else {
			return NewsTitleSize[1];
		}
	}

	public static float getNewsTitleSize(Context context) {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return context.getResources().getDimension(R.dimen.text_size_16);
		} else if (type == Font_middle) {
			return context.getResources().getDimension(R.dimen.text_size_18);
		} else if (type == Font_Max) {
			return context.getResources().getDimension(R.dimen.text_size_20);
		} else if (type == Font_Big_Max) {
			return context.getResources().getDimension(R.dimen.text_size_22);
		} else {
			return context.getResources().getDimension(R.dimen.text_size_18);
		}
	}


	public static final float[] RelatedReadingSize = { 15, 17, 19, 21 };

	/**
	 * 相关阅读字体大小
	 * @return
     */
	public static float getRelatedReadingTextSize() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return RelatedReadingSize[0];
		} else if (type == Font_middle) {
			return RelatedReadingSize[1];
		} else if (type == Font_Max) {
			return RelatedReadingSize[2];
		} else if (type == Font_Big_Max) {
			return RelatedReadingSize[3];
		} else {
			return RelatedReadingSize[1];
		}
	}

	public static final float[] LabelSize = { 16, 18, 20, 22 };

	/**
	 * 文章详情中的标签字体大小
	 * @return
     */
	public static float getLabelTextSize() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return LabelSize[0];
		} else if (type == Font_middle) {
			return LabelSize[1];
		} else if (type == Font_Max) {
			return LabelSize[2];
		} else if (type == Font_Big_Max) {
			return LabelSize[3];
		} else {
			return LabelSize[1];
		}
	}

	public static final float[] GifTitleSize = { 20, 22, 24, 26 };

	/**
	 * 文章详情中的标题字体大小
	 * @return
	 */
	public static float getGifTitleSize() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return GifTitleSize[0];
		} else if (type == Font_middle) {
			return GifTitleSize[1];
		} else if (type == Font_Max) {
			return GifTitleSize[2];
		} else if (type == Font_Big_Max) {
			return GifTitleSize[3];
		} else {
			return GifTitleSize[1];
		}
	}


	public static final float[] NewslineSpace = { 6, 7, 7, 8 };
	/**
	 * @return
	 */
	public static float getLineSpaceNews() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return NewslineSpace[0];
		} else if (type == Font_middle) {
			return NewslineSpace[1];
		} else if (type == Font_Max) {
			return NewslineSpace[2];
		} else if (type == Font_Big_Max) {
			return NewslineSpace[3];
		} else {
			return NewslineSpace[1];
		}
	}

	public static final float[] NewsContentSize = { 15, 17, 19, 22 };

	/**
	 * 文章详情内容大小
	 * @return
	 */
	public static float getNewsContentSize() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return NewsContentSize[0];
		} else if (type == Font_middle) {
			return NewsContentSize[1];
		} else if (type == Font_Max) {
			return NewsContentSize[2];
		} else if (type == Font_Big_Max) {
			return NewsContentSize[3];
		} else {
			return NewsContentSize[1];
		}
	}
	public static final float[] NewsDetailTitleSize = { 15, 17, 19, 22 };

	/**
	 * 文章详情标题大小
	 * @return
	 */
	public static float getNewsDetailTitleSize() {
		int type = AccountManager.getInstance().getCurrentSize();
		if (type == Font_Min) {
			return NewsDetailTitleSize[0];
		} else if (type == Font_middle) {
			return NewsDetailTitleSize[1];
		} else if (type == Font_Max) {
			return NewsDetailTitleSize[2];
		} else if (type == Font_Big_Max) {
			return NewsDetailTitleSize[3];
		} else {
			return NewsDetailTitleSize[1];
		}
	}

}
