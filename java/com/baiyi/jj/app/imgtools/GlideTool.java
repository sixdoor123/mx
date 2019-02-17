package com.baiyi.jj.app.imgtools;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.baiyi.jj.app.manager.RandomManager;
import com.baiyi.jj.app.utils.FileUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.utils.Utils;
import com.bumptech.glide.signature.StringSignature;
import com.turbo.turbo.mexico.R;


public class GlideTool {


    public static final String GLIDE_CACHE = "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;

    public static final int[] placeholders = {R.drawable.bj_place_10,R.drawable.bj_place_1,R.drawable.bj_place_2,R.drawable.bj_place_3,
            R.drawable.bj_place_4,R.drawable.bj_place_5,R.drawable.bj_place_6,R.drawable.bj_place_7,R.drawable.bj_place_8,R.drawable.bj_place_9};


    public static void getSignatureImage(Context context, String imgUrl, ImageView iv) {

        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = placeholders[ranNum];

        if (Utils.isStringEmpty(imgUrl)) {
            iv.setImageResource(placeholder);
            return;
        }
        Glide.with(context).load(imgUrl).asBitmap()
                .signature(new StringSignature("1.0.0"))
                .error(placeholder)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    public static void getListImage(Context context, String imgUrl, ImageView iv) {

        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = placeholders[ranNum];

        if (Utils.isStringEmpty(imgUrl)) {
            iv.setImageResource(placeholder);
            return;
        }
        Glide.with(context).load(imgUrl).asBitmap()
                .error(placeholder)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    public static void getVideoListImage(Context context, String imgUrl, ImageView iv) {

        int placeholder = R.drawable.bj_place_10;

        if (Utils.isStringEmpty(imgUrl)) {
            iv.setImageResource(placeholder);
            return;
        }
        Glide.with(context).load(imgUrl).asBitmap()
                .error(placeholder)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    public static void getListImage_BigSrc(Context context, String imgUrl, ImageView iv) {
        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = placeholders[ranNum];
        if (Utils.isStringEmpty(imgUrl)) {
            iv.setImageResource(placeholder);
            return;
        }
        Glide.with(context).load(imgUrl).asBitmap()
                .error(placeholder)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    public static void getListGifImage_BigSrc(Context context, String imgUrl, ImageView iv) {
        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = placeholders[ranNum];
        if (Utils.isStringEmpty(imgUrl)) {
            iv.setImageResource(placeholder);
            return;
        }
        Glide.with(context).load(imgUrl).asGif()
                .error(placeholder)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * ????????????--???
     *
     * @param context
     * @param imgUrl
     * @param iv
     */
    public static void getCommentHead(Context context, String imgUrl, ImageView iv, int resource) {
        if (Utils.isStringEmpty(imgUrl)) {
            iv.setImageResource(resource);
            return;
        }
        Glide.with(context).load(imgUrl)
                .error(resource)
                .placeholder(resource)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 清楚Picasso缓存文件
     *
     * @param context
     */
    public static void clearCache(final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File glideFile = new File(FileUtil.getCachePath(context) + GLIDE_CACHE);
                FileUtil.clearFile(glideFile);

//                ImagePipeline imagePipeline = Fresco.getImagePipeline();
//                imagePipeline.clearCaches();
            }
        }).start();

    }

//    public static void loadProgressJpg(Context context,String url,final SimpleDraweeView imageView,final double rotioRate){
//        if (Utils.isStringEmpty(url)) {
//            imageView.setImageResource(R.drawable.bj_gif_9999);
//            return;
//        }
////        url = "http://192.168.1.107:8089/img_68.jpg";
//
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .setProgressiveRenderingEnabled(true) //设置支持渐进式JPEG
//                .build();
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        DraweeController progressiveJPEGController = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setOldController(imageView.getController())
//                .build();
//        imageView.setController(progressiveJPEGController);
//        DataSource dataSource = imagePipeline.fetchDecodedImage(request,context);
//        dataSource.subscribe(new BaseBitmapDataSubscriber() {
//            @Override
//            protected void onNewResultImpl(Bitmap bitmap) {
//                int width = bitmap.getWidth();
//                int height = bitmap.getHeight();
//                double percentH = (double) height/(double) width;
////              TLog.e("he",width+"====="+height+"======="+imgLocal.getLayoutParams().width+"====="+percentH+"====="+imgLocal.getWidth()*percentH);
//                if (rotioRate == 0){
//                    imageView.getLayoutParams().height = (int)(imageView.getLayoutParams().width*percentH);
//                }
//            }
//
//            @Override
//            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//
//            }
//        }, CallerThreadExecutor.getInstance());
//
//    }
//
//    public static void loadProgressJpg2(final Context context, String url, final ImageView imageView){
//        if (Utils.isStringEmpty(url)) {
//            return;
//        }
//
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .setProgressiveRenderingEnabled(true) //设置支持渐进式JPEG
//                .build();
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        DataSource dataSource = imagePipeline.fetchDecodedImage(request,context);
//        dataSource.subscribe(new BaseBitmapDataSubscriber() {
//            @Override
//            protected void onNewResultImpl(final Bitmap bitmap) {
//                ((BaseActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });
//
//            }
//            @Override
//            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//
//            }
//        }, CallerThreadExecutor.getInstance());
//
//    }
}
