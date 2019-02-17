//package com.baiyi.jj.app.activity.main.fragment;
//
//import android.support.v4.app.FragmentActivity;
//
///**
// * Created by Administrator on 2017/2/22 0022.
// */
//public class AcbActivity extends FragmentActivity implements
//        FragmentEvent.OnEventListener {
//
//
//    ViewPager viewPager;
//    FragmentPagerAdapter adapter;
//
//
//    MainTab01 tab01 = new MainTab01();
//    MainTab02 tab02 = new MainTab02();
//    MainTab03 tab03 = new MainTab03();
//    MainTab04 tab04 = new MainTab04();
//    MainTab05 tab05 = new MainTab05();
//    Fragment[] fragments = {tab01, tab02, tab03, tab04};
//    boolean[] fragmentsUpdateFlag = {false, false, false, false};
//
//
//    /**
//     * 底部四个按钮
//     */
//    private LinearLayout tabBtnWeixin;
//    private LinearLayout tabBtnFrd;
//    private LinearLayout tabBtnAddress;
//    private LinearLayout tabBtnSettings;
//
//
//    Handler mainHandler = new Handler() {
//
//
//        /*
//        * （非 Javadoc）
//        *
//        * @see android.os.Handler#handleMessage(android.os.Message)
//        */
//        @Override
//        public void handleMessage(Message msg) {
//// TODO 自动生成的方法存根
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case MSG.INTO_05:
//                    fragments[3] = tab05;
//                    fragmentsUpdateFlag[3] = true;
//                    adapter.notifyDataSetChanged();
//                    break;
//                default:
//            }
//        }
//    };
//
//
//    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
//        FragmentManager fm;
//
//
//        MyFragmentPagerAdapter(FragmentManager fm) {
//            super(fm);
//            this.fm = fm;
//        }
//
//
//        @Override
//        public int getCount() {
//            return fragments.length;
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//            Fragment fragment = fragments[position % fragments.length];
//            Log.i(Common.TAG, "getItem:position=" + position + ",fragment:"
//                    + fragment.getClass().getName() + ",fragment.tag="
//                    + fragment.getTag());
//            return fragments[position % fragments.length];
//        }
//
//
//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
//
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
////得到缓存的fragment
//            Fragment fragment = (Fragment) super.instantiateItem(container,
//                    position);
////得到tag，这点很重要
//            String fragmentTag = fragment.getTag();
//
//
//            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {
////如果这个fragment需要更新
//
//                FragmentTransaction ft = fm.beginTransaction();
////移除旧的fragment
//                ft.remove(fragment);
////换成新的fragment
//                fragment = fragments[position % fragments.length];
////添加新fragment时必须用前面获得的tag，这点很重要
//                ft.add(container.getId(), fragment, fragmentTag);
//                ft.attach(fragment);
//                ft.commit();
//
////复位更新标志
//                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
//            }
//
//
//            return fragment;
//        }
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
//
//
//        tabBtnWeixin = (LinearLayout) findViewById(R.id.id_tab_bottom_weixin);
//        tabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_friend);
//        tabBtnAddress = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);
//        tabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_setting);
//
//
//        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
//
//
//        viewPager.setAdapter(adapter);
//
//
//        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//
//            private int currentIndex;
//
//
//            @Override
//            public void onPageSelected(int position) {
//                resetTabBtn();
//                switch (position) {
//                    case 0:
//                        ((ImageButton) tabBtnWeixin
//                                .findViewById(R.id.btn_tab_bottom_weixin))
//                                .setImageResource(R.drawable.tab_weixin_pressed);
//                        break;
//                    case 1:
//                        ((ImageButton) tabBtnFrd
//                                .findViewById(R.id.btn_tab_bottom_friend))
//                                .setImageResource(R.drawable.tab_find_frd_pressed);
//                        break;
//                    case 2:
//                        ((ImageButton) tabBtnAddress
//                                .findViewById(R.id.btn_tab_bottom_contact))
//                                .setImageResource(R.drawable.tab_address_pressed);
//                        break;
//                    case 3:
//                        ((ImageButton) tabBtnSettings
//                                .findViewById(R.id.btn_tab_bottom_setting))
//                                .setImageResource(R.drawable.tab_settings_pressed);
//                        break;
//                }
//
//
//                currentIndex = position;
//            }
//
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//
//            }
//
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
//
//
//    }
//
//
//    protected void resetTabBtn() {
//        ((ImageButton) tabBtnWeixin.findViewById(R.id.btn_tab_bottom_weixin))
//                .setImageResource(R.drawable.tab_weixin_normal);
//        ((ImageButton) tabBtnFrd.findViewById(R.id.btn_tab_bottom_friend))
//                .setImageResource(R.drawable.tab_find_frd_normal);
//        ((ImageButton) tabBtnAddress.findViewById(R.id.btn_tab_bottom_contact))
//                .setImageResource(R.drawable.tab_address_normal);
//        ((ImageButton) tabBtnSettings.findViewById(R.id.btn_tab_bottom_setting))
//                .setImageResource(R.drawable.tab_settings_normal);
//    }
//
//
//    @Override
//    public void onEvent(int what, Bundle data, Object object) {
//// TODO 自动生成的方法存根
//        mainHandler.sendEmptyMessage(what);
//    }
//}
