package com.baiyi.cmall.activities.main.business.help;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class FileUtil {

	public static List<String> getImageList(List<String> folderPaths){
		List<String> pathList  = new ArrayList<String>();
		for (int j = 0; j < folderPaths.size(); j++) {
			File file = new File(folderPaths.get(j));
			if (file.exists()) {
				File [] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					pathList.add(files[files.length-i-1].getAbsolutePath());
				}
			}else {
				System.out.println("bucunz-------");
			}
		}
		
		return pathList;
	}
	
	public List<String> getAllSDImageFolder(Activity context) {
		
		List<String> allFolder = new ArrayList<String>();
		String[] projection = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.BUCKET_ID, // ֱ�Ӱ�����ͼƬ�ļ����ļ���ID����ֹ�ڲ�ͬ�µ��ļ�������
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // ֱ�Ӱ�����ͼƬ�ļ����ļ�����
				MediaStore.Images.Media.DISPLAY_NAME, // ͼƬ�ļ���
				MediaStore.Images.Media.DATA, // ͼƬ����·��
				"count(" + MediaStore.Images.Media._ID + ")"// ͳ�Ƶ�ǰ�ļ����¹��ж�����ͼƬ
		};
		String selection = " 0==0) group by bucket_display_name --(";
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				projection, selection, null, "");
		int i = 0;
		if (null != cursor) {
			while (cursor.moveToNext()) {
				i++;
				String folderId = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
				String folder = cursor
						.getString(cursor
								.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
				long fileId = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Images.Media._ID));
				String finaName = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
				String path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				File file = new File(path);
				allFolder.add(file.getParentFile().getAbsolutePath());
				
//				int count = cursor.getInt(5);// ���ļ�����һ���ж�����ͼƬ
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				Bitmap bitmap = Thumbnails.getThumbnail(cr, fileId,
//						Thumbnails.MICRO_KIND, options);// ��ȡָ��ͼƬ����Ƭ
				
			}
			if (!cursor.isClosed()) {
				cursor.close();
			}
		}
		return allFolder;
	}
	
	public static List<String> getScreenImageFolder(Context context) {
		
		List<String> screenFolder = new ArrayList<String>();
		
		String[] projection = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.BUCKET_ID, // ֱ�Ӱ�����ͼƬ�ļ����ļ���ID����ֹ�ڲ�ͬ�µ��ļ�������
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // ֱ�Ӱ�����ͼƬ�ļ����ļ�����
				MediaStore.Images.Media.DISPLAY_NAME, // ͼƬ�ļ���
				MediaStore.Images.Media.DATA, // ͼƬ����·��
				"count(" + MediaStore.Images.Media._ID + ")"// ͳ�Ƶ�ǰ�ļ����¹��ж�����ͼƬ
		};
		String selection = " 0==0) group by bucket_display_name --(";
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				projection, selection, null, "");
		if (null != cursor) {
			while (cursor.moveToNext()) {
				
				String path = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				if (path.contains("Screen")||path.contains("��ͼ")) {
					File file = new File(path);
					screenFolder.add(file.getParentFile().getAbsolutePath());
				}
			}
			if (!cursor.isClosed()) {
				cursor.close();
			}

		}
		return screenFolder;
	}
	
}
