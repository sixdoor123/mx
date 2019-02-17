package com.baiyi.jj.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.skin.util.StringUtils;
import com.turbo.turbo.mexico.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 获取头像工具类
 * 作者：lizl on 2016/11/23 16:50
 */
public class UserHeadUtils {

    private Context context;
    private static UserHeadUtils ourInstance;

    /**
     * 内存不足时候会自动回收bitmap，先进先回收，K-->（String）是uri路径
     */
    private HashMap<String, SoftReference<Bitmap>> mReferenceHashMap;


    public static UserHeadUtils getInstance(Context context) {
        if (null == ourInstance) {
            ourInstance = new UserHeadUtils(context);
        }
        return ourInstance;
    }

    private UserHeadUtils(Context context) {
        this.context = context;
        mReferenceHashMap = new HashMap<String, SoftReference<Bitmap>>();
    }

    /**
     * 加载图片
     *
     * @param url 图片的url路径
     * @return 返回一个bitmap图片
     */
    public synchronized void loadImage(ImageView view, String url) {

        if (Utils.isStringEmpty(url)){
            view.setImageResource(R.drawable.default_head);
            return;
        }

        String md5Url = getMd5Url(url);
        //从内存中取出来
        if (null == loadFormMem(view, md5Url)) {
            //内存中没有，就在储存卡中取图片
            if (null == loadFormSDCard(view, md5Url)) {
                //储存卡中没有，就在网络中取图片
                loadFormNet(view, url);
            }
        }
    }

    /**
     * 从内存中加载图片
     *
     * @param url 图片的url路径
     * @return 返回一个bitmap图片
     */
    private Bitmap loadFormMem(ImageView view, String url) {

        //首先要看在内存中还有没有这个图片
        if (mReferenceHashMap.containsKey(url)) {
            SoftReference<Bitmap> softReference = mReferenceHashMap.get(url);
            view.setImageBitmap(softReference.get());
            //从软引用中拿到图片
            return softReference.get();
        }
        return null;
    }

    /**
     * 从储存卡加载图片
     *
     * @param url 图片的url路径
     * @return 返回一个bitmap图片
     */
    private Bitmap loadFormSDCard(ImageView view, String url) {
        //在chche这个缓存的目录里面查找
        File file = new File(context.getCacheDir(), url);

        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            view.setImageBitmap(bitmap);
            //如果能在这里来，那么内存中一定没有图片，所以给内存中缓存一张
            saveToMem(url, bitmap);
            return bitmap;
        }
        return null;
    }

    /**
     * 从网络上加载图片
     */
    private void loadFormNet(final ImageView view, final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                try {
                    if (Utils.isStringEmpty(url))
                    {
                        return;
                    }
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    InputStream is = response.body().byteStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    saveToMem(getMd5Url(url), bm);
                    saveToSDCard(getMd5Url(url), bm);
                    ((BaseActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    ((BaseActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageResource(R.drawable.default_head);
                        }
                    });

                }
            }
        }).start();
    }

    /**
     * 保存到内存
     *
     * @param url    图片的url地址
     * @param bitmap 图片
     */
    private void saveToMem(String url, Bitmap bitmap) {
        if (null == bitmap) {
            return;
        }
        //保存到软引用
        mReferenceHashMap.put(url, new SoftReference<Bitmap>(bitmap));
    }

    /**
     * 保存到Sd卡
     *
     * @param url    图片的url地址
     * @param bitmap 图片
     */
    private void saveToSDCard(String url, Bitmap bitmap) {
        if (null == bitmap) {
            return;
        }
        FileOutputStream outputStream = null;

        try {
            //文件夹是程序在Sd卡里面的缓存的文件夹
            File dir = context.getCacheDir();
            //把叫什么名字的文件保存在dir里面
            File file = new File(dir, url);
            //通过file文件打开一个文件输出流
            outputStream = new FileOutputStream(file);
            //将bitmap以JPEG的图片类型，100%的质量，保存到了outputStream流里面
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 清空指定的图片
     *
     * @param url
     */
    public void clearBitmap(String url) {
        if (Utils.isStringEmpty(url)){
            return;
        }
        String md5Url = getMd5Url(url);
        if (null != mReferenceHashMap) {
            mReferenceHashMap.remove(md5Url);
        }
        File file = new File(context.getCacheDir(), md5Url);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 根据url地址通过MD5加密方式获得这个文件的名字
     *
     * @param url 图片的url路径
     * @return 返回一个bitmap图片
     */
    private String getMd5Url(String url) {
        // 确定计算方法
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] bytes = digest.digest(url.getBytes());

        char str[] = new char[bytes.length * 2];
        int k = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte byte0 = bytes[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
