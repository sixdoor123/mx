package com.baiyi.jj.app.activity.user;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.login.BaseLoginActivity;
import com.baiyi.jj.app.adapter.WelcomeAdapter;
import com.baiyi.jj.app.utils.TLog;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;

/**
 * ��ӭ����
 */
public class WelcomeActivity extends BaseLoginActivity {

    private ViewPager mVpGuide;
    private WelcomeAdapter startAdapter;
    private int[] bitmaps;
    private ImageView[] mImgGuides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initData();
        initView();
    }

    private void initData() {
        bitmaps = new int[]{R.drawable.bj_place_1, R.drawable.bj_place_2};
        mImgGuides = new ImageView[3];
    }

    private void initView() {
        mVpGuide = (ViewPager) findViewById(R.id.vp_start);
        mVpGuide.setOffscreenPageLimit(2);
        startAdapter = new WelcomeAdapter(this, bitmaps);
        mVpGuide.setAdapter(startAdapter);

        mImgGuides[0] = (ImageView) findViewById(R.id.img_quide_1);
        mImgGuides[1] = (ImageView) findViewById(R.id.img_quide_2);
        mImgGuides[2] = (ImageView) findViewById(R.id.img_quide_3);

        mVpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                TLog.d("onPageSelected", "onPageSelected" + position);
                for (int i = 0; i < mImgGuides.length; i++) {
                    if (position == i) {
                        //�ڲ����ļ������õ�src����������Activity������ʱҪ��֮��Ӧ
                        mImgGuides[i].setImageResource(R.drawable.adware_style_selected);
                    } else {
                        mImgGuides[i].setImageResource(R.drawable.adware_style_default);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmaps = null;
        mImgGuides = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.WELCOME_REGISTER_OK){
//            Intent intent = new Intent(this, ChannelInitActi.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            setExitSwichLayout();
        }
    }
}
