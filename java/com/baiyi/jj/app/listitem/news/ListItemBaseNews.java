/**
 * 
 */
package com.baiyi.jj.app.listitem.news;

import android.content.Context;
import com.baiyi.jj.app.entity.NewsListEntity;

/**
 * @author tangkun
 *
 */
public class ListItemBaseNews extends SkinLinearLayout{
	
	public static final int Type_NoImage = 1;
	public static final int Type_ImgLeft = 2;
	public static final int Type_ImgCenter = 3;
	public static final int Type_ImgThree = 4;
	public static final int Type_ImgTwo = 7;
	public static final int Type_ImgThree_sheying = 5;
	public static final int Type_Refresh = 6;
	public static final int Type_video = 8;
	public static final int Type_GifCenter = 9;
	public static final int Type_VideoRight = 10;
	public static final int Type_videoFacebook = 11;
	public static final int Type_videoyoutubo = 12;

	public static final int Type_ads = 1001;
	


	public static final String Item_Type_NoImage = "no_image";
	public static final String Item_Type_One_Small = "one_small";
	public static final String Item_Type_One_big = "one_big";
	public static final String Item_Type_One_three = "three";
	public static final String Item_Type_One_three_spacial = "three_special";
	public static final String Item_Type_One_Two = "two";
	public static final String Item_Type_Refresh = "refresh";
	public static final String Item_Type_video = "video";
	public static final String Item_Type_video_youtube = "videoyou";
	public static final String Item_Type_video_fb = "videofb";
	public static final String Item_Type_Gif = "gif";

	//ͨ��
	public static final float Center_Img_Percent = 0.48f;
	//ͨ��
	public static final float Defult_Img_Percent = 0.68f;
	//���
	public static final float Advertisement_Img_Percent = 0.48f;
	//ժҪͼ
	public static final float Abstract_Img_Percent = 0.68f;
	//ͨ��ͼƬƫ������
	public static final int Center_Dip_Offset = 24;
	//˫��ͼ
	public static final int TwoImg_Dip_Offset = 27;
	//����ͼ
	public static final int ThreeImg_Dip_Offset = 30;
	//��Ӱ��ͼ�����ƫ��
	public static final int Sheying_LeftBig_Offset = 27;
//	//ժҪͼ
	public static final int RightImg_Dip_Offset = 34;
	
	public static final String ChannelId_DZ = "55ffdc8d78549126bfe68d81";
	public static final String ChannelId_NH = "561783a9472bf91340ceb6f9";

	public static final int Temp_Image_small = 1;
	public static final int Temp_Image_three = 3;
	public static final int Temp_Image_big = 2;
	public static final int Temp_Image_gif = 4;
	public static final int Temp_Image_video = 5;
	public static final int Temp_Image_videoyutube = 7;
	public static final int Temp_Image_videofb = 6;
	public static final int Temp_Image_none = 0;
	
	public static String getImageTypeByTemp(int template){
		if (template == Temp_Image_small){
			return Item_Type_One_Small;
		}else if (template == Temp_Image_big){
			return  Item_Type_One_big;
		}else if (template == Temp_Image_three){
			return  Item_Type_One_three;
		}else if (template == Temp_Image_gif){
			return  Item_Type_Gif;
		}else if (template == Temp_Image_video){
			return  Item_Type_video;
		}else if (template == Temp_Image_videoyutube){
			return  Item_Type_video_youtube;
		}else if (template == Temp_Image_videofb){
			return  Item_Type_video_fb;
		}else {
			return Item_Type_NoImage;
		}
	}
	
	/**
	 * @param context
	 */
	public ListItemBaseNews(Context context, String markLine, NewsListEntity t) {
		super(context);
	}




}
