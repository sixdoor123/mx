package com.baiyi.jj.app.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.adapter.PhotoPagerAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.PicEntity;
import com.baiyi.jj.app.utils.IntentDefine;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.HackyViewPager;
import com.baiyi.jj.app.views.MyLoadingBar;

/**
 * 图片预览界面
 */
public class ImageContainerActivity extends BaseActivity {

    /**
     * ???????
     */
    private ImageView imgBack = null;
    private ImageView imgDown = null;
    private TextView txtImagePosi = null;
    private TextView txtAllCount = null;
    /**
     * ?????
     */
    private LinearLayout linAddPoint = null;

    // private ImageTouchProgressUnit progressUnit = null;
    // private ReadPageView readPage = null;
    // private ViewGroup readContainer = null;
    // private LinearLayout tranView = null;

    private LinearLayout linAddPager = null;
    private HackyViewPager viewPager = null;
    private PhotoPagerAdapter adapter = null;

    private MyLoadingBar progressBar = null;
    private int startImgIndex = 0;
    private List<String> imgList;
    private boolean isGlide = true;


    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);
        ContextUtil.getLayoutInflater(this).inflate(
                R.layout.activity_imagecontainer, win_content);

        imgList = (List<String>) getIntent().getStringArrayListExtra(
                IntentDefine.PicEntity);
        startImgIndex = getIntent()
                .getIntExtra(IntentDefine.Start_Img_Index, 0);
        isGlide = getIntent().getBooleanExtra(IntentDefine.IMAGE_TYPE, true);
        if (Utils.isStringEmpty(imgList)) {
            return;
        }
        initView();

        initImageview(imgList);

    }

    private void initView() {
        linAddPoint = (LinearLayout) findViewById(R.id.lin_addpoint);
        txtImagePosi = (TextView) findViewById(R.id.txt_imgposition);
        txtAllCount = (TextView) findViewById(R.id.txt_allcout);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgDown = (ImageView) findViewById(R.id.img_down);
        imgBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setExitSwichLayout();
            }
        });
        imgDown.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                downImage(imgList.get(viewPager.getCurrentItem()));
            }
        });
        txtImagePosi.setText(String.valueOf(startImgIndex + 1));
        txtAllCount.setText("/" + imgList.size());
    }

    private void initImageview(List<String> imgList) {
        List<PicEntity> list = new ArrayList<PicEntity>();
        for (int i = 0; i < imgList.size(); i++) {
            PicEntity e = new PicEntity();
            e.setId(i);
            e.setProjectPath(imgList.get(i));
            list.add(e);
        }
//		AddPiont(startImgIndex);

        initPagerView(imgList);

        // initPager(list);
        // readPage.setData(list);
    }

    private void initPagerView(List<String> imgList) {
        linAddPager = (LinearLayout) findViewById(R.id.lin_viewpager);
        viewPager = new HackyViewPager(this);
//		viewPager = (HackyViewPager) findViewById(R.id.hackpager);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
//				 if (itemEntity == null) {
//				 return;
//				 }
                txtImagePosi.setText(String.valueOf(arg0 + 1));
//				 AddPiont(arg0);
                // curImageIndex = position;
                // if(labelPageView != null)
                // labelPageView.setPosition(position);
                // updateMaxPosition(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        adapter = new PhotoPagerAdapter(this, imgList, isGlide);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startImgIndex);
        linAddPager.removeAllViews();
        linAddPager.addView(viewPager);

    }

    private void initPager(final List<PicEntity> itemEntity) {

        // readContainer = (ViewGroup) findViewById(R.id.img_container);
        // progressBar = (MyLoadingBar) findViewById(R.id.loading);
        // progressUnit = new ImageTouchProgressUnit(this);
        //
        // readPage = new ReadPageView(this, readContainer, startImgIndex,
        // progressUnit);
        // readPage.setOnComicDisplayListener(new
        // ImageContainerView.OnImageDisplayListener() {
        // @Override
        // public void onImageDisplay(PicEntity entity,
        // ImageParamsEntity imageParams) {
        // int index = 0;
        // for (int i = 0; i < itemEntity.size(); i++) {
        // PicEntity im = itemEntity.get(i);
        // if (im.getId() == entity.getId()) {
        // index = i;
        // break;
        // }
        // }
        // progressUnit.setNewImage(entity, index, imageParams);
        // }
        // });
        // // ??????
        // readPage.setOnViewPortListener(new
        // ImageTouchContainer.OnViewPortListener() {
        //
        // @Override
        // public void onExiteViewPort(int position) {
        //
        // }
        //
        // @Override
        // public void onEnterViewPort(int position) {
        // if (itemEntity == null) {
        // return;
        // }
        // txtImagePosi.setText(String.valueOf(position + 1));
        // AddPiont(position);
        // // curImageIndex = position;
        // // if(labelPageView != null)
        // // labelPageView.setPosition(position);
        // // updateMaxPosition(position);
        // }
        // });
        // readPage.setOnCenterTouchListener(new OnCenterTouchListener() {
        //
        // @Override
        // public void onCenterTouch() {
        // finish();
        //
        // }
        // });
        // tranView = new LinearLayout(this);
        // readContainer.addView(tranView, ViewGroup.LayoutParams.FILL_PARENT,
        // ViewGroup.LayoutParams.FILL_PARENT);
    }

    private void AddPiont(int postion) {

        final List<ImageView> imgvList = new ArrayList<ImageView>();
        linAddPoint.removeAllViews();
        // ????
        for (int j = 0; j < imgList.size(); j++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(BaseActivity.getDensity_int(5), 0, 0, 0);
            if (j == postion) {
                imageView.setImageResource(R.drawable.current_point_ico);
            } else {
                imageView.setImageResource(R.drawable.icon_noaccess_point);
            }
            imgvList.add(imageView);

            linAddPoint.addView(imageView);
        }

    }

    private void downImage(String url) {

        if (Utils.isStringEmpty(url)) {
            return;
        }
//        if (isGlide) {
            Glide.with(this).load(url).asBitmap()
                    .error(ThemeUtil.getAppThemeBgImg())
                    .placeholder(ThemeUtil.getAppThemeBgImg())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            saveImageToGallery(ImageContainerActivity.this, resource);
                        }
                    });
//        } else {
//            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                    .setProgressiveRenderingEnabled(true) //设置支持渐进式JPEG
//                    .build();
//            ImagePipeline imagePipeline = Fresco.getImagePipeline();
//            DataSource dataSource = imagePipeline.fetchDecodedImage(request, this);
//            dataSource.subscribe(new BaseBitmapDataSubscriber() {
//                @Override
//                protected void onNewResultImpl(Bitmap bitmap) {
//                    saveImageToGallery(ImageContainerActivity.this, bitmap);
//                }
//
//                @Override
//                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//
//                }
//            }, CallerThreadExecutor.getInstance());
//        }


    }

    public void saveFile(Bitmap bm, String fileName) throws IOException {
        String path = ContextUtil.getSDPath() + "/" + Config.Root_File_Path
                + "/jjimage/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Lumen");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 插入系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 更新图库
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

//        displayToast(getResources().getString(R.string.txt_save_success));
    }

}
