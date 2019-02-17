package com.baiyi.jj.app.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getCachePath(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取文件的大小
     */
    public static long getFileSize(File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFileSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清除文件、缓存
     *
     * @return
     */
    public static void clearFile(File dir) {

        if (dir != null && dir.isDirectory()) {

            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        clearFile(child);
                    }
                    child.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    public List<String> getAllSDImageFolder(Activity context) {

        List<String> allFolder = new ArrayList<String>();
        String[] projection = new String[]{MediaStore.Images.Media._ID,
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
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return allFolder;
    }

    public static List<String> getScreenImageFolder(Context context) {

        List<String> screenFolder = new ArrayList<String>();

        String[] projection = new String[]{MediaStore.Images.Media._ID,
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
                if (path.contains("Screen") || path.contains("��ͼ")) {
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

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
