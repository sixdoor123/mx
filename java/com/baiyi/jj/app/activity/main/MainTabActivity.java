//package com.baiyi.jj.app.activity.main;
//
//import android.content.Intent;
//import android.graphics.PixelFormat;
//import android.media.Image;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.animation.LinearInterpolator;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baiyi.core.util.ContextUtil;
//import com.baiyi.jj.app.activity.attention.AllAttetionAct;
//import com.baiyi.jj.app.manager.CountryMannager;
//import com.baiyi.jj.app.views.viewpager.NoScrollViewPager;
//import com.turbo.turbo.mexico.R;
//import com.baiyi.jj.app.activity.user.channel.ChannelDataUtils;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.baiyi.jj.app.activity.SearchDetailActivity;
//import com.baiyi.jj.app.activity.channelmanager.ChannelManager;
//import com.baiyi.jj.app.activity.channelmanager.ChannelManager.OnListChanged;
//import com.baiyi.jj.app.activity.main.NewsBasePager.OnRefreshComplete;
//import com.baiyi.jj.app.activity.user.channel.UsChannelAct;
//import com.baiyi.jj.app.application.accont.AccountManager;
//import com.baiyi.jj.app.utils.Define;
//import com.baiyi.jj.app.utils.Utils;
//import com.baiyi.jj.app.views.LetterSpacingTextView;
//import com.baiyi.jj.app.views.TitleView;
//import com.baiyi.jj.app.views.TitleView.NewsPopItemOnclick;
//import com.baiyi.jj.app.views.TitleView.OnRefreshClick;
//import com.baiyi.jj.app.views.TitleView.TitleNewsOnclick;
//import com.baiyi.jj.app.views.pageview.PageView;
//import com.baiyi.jj.app.views.viewpager.ViewPagerSelected;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author weizd �ײ��˵�
// */
//public class MainTabActivity extends NewsBaseActivity implements
//        OnCheckedChangeListener, ThemeChangeCallBack {
//
//    // ������
//    private TitleView titleView = null;
//
//    private List<PageView> pageViews = null;
//    private NewsPager newsPager = null;
//    //	private InformationPager informationPager = null;
//    private MsgPager msgPager = null;
//    private SettingPager settingPager = null;
//    private AttentionPager attentionPager = null;
//    /**
//     * ���������RadioButton������page�л�
//     */
//    private RadioGroup mainRg = null;
//    private RadioButton newsRb = null;
//    //	private RadioButton informationRb = null;
//    private RadioButton msgRb = null;
//    private RadioButton userRb = null;
//    private RadioButton attenRb = null;
//
//    private TextView txtMsgNum = null;
//
//    private long exitTime = 0;
//    public static NoScrollViewPager mainViewPager = null;
//    // ���ִ�С����
//    private int fontType;
//    // �Ƿ���ʾժҪ
//    private int abstractLines;
//    // country
//    private int countryType;
//    /**
//     * �ص���ҳ���ص��ĸ�������ҳ
//     */
//    public static int tabId = 0;
//    public static final int TabNewsId = 0;
//    public static final int TabReadId = 1;
//    public static final int TabAttent = 2;
//    public static final int TabNotif = 3;
//    public static final int TabMyId = 4;
//
//    public static int groupId = 1;
//    public static final int TabInterestId = 1;
//    public static final int TabPhotoId = 2;
//
//    // ��һ�б�ҳ������һ����ť
//    private ImageView imgBackLeft = null;
//    // �б����
//    private LetterSpacingTextView txtTitle = null;
//
//    // Ĭ�ϵı���
//    public static String defaultTitleName;
//    public static String titleExp;
//    public static String titleAtten;
//    public static String titleNoti;
//    public static String titleSet;
//    private String[] titleNames;
//
//    // ������ť
//    private ImageView imgSearch = null;
//    // ���ذ�ť
//    // private ImageView imgBack = null;
//    // ��ҳ��ť
//    private ImageView imgMoreCenter = null;
//    private ImageView imgChannel = null;
//
//    private View view1 = null;
//    //	private View view2 = null;
//    private View view3 = null;
//    private View view4 = null;
//    private View view5 = null;
//    private LinearLayout linNews = null;
////	private LinearLayout linRead = null;
//
//    private boolean isAnim = false;
//
//    private int messageUnread = 0;
//
//    @Override
//    protected void initWin(boolean hasScrollView, boolean isAnimal) {
//        super.initWin(false, true);
//
//        initMain();
//    }
//
//    private void initMain() {
//        fontType = AccountManager.getInstance().getCurrentSize();
//        abstractLines = AccountManager.getInstance().getSummary_Is_Display();
//        countryType = CountryMannager.getInstance().getCurrentCountry();
//
//        ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_tab,
//                win_content);
//        initTitle();
//        tabId = getIntent().getIntExtra(Define.TabId, 0);
//        initRadioView();
//        initViewPager();
//        initSingleList();
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (null != newsPager) {
//            newsPager.onSaveInstanceState(outState);
//        }
////		if (null != informationPager)
////		{
////			informationPager.onSaveInstanceState(outState);
////		}
//
//    }
//
//    /**
//     * ��ʼ��ͷ��View
//     */
//    private void initTitle() {
//        defaultTitleName = getResources().getString(R.string.title_news);
//        titleExp = getResources().getString(R.string.title_explore);
//        titleAtten = getResources().getString(R.string.txt_attention_title);
//        titleNoti = getResources().getString(R.string.name_notification);
//        titleSet = getResources().getString(R.string.name_mine);
//        titleNames = new String[]{defaultTitleName, titleAtten, titleNoti, titleSet};
//
//        titleView = new TitleView(this, false, false, R.drawable.btn_channel_normal);
//
//        win_title.addView(titleView);
//
//        // �м����ϵļ�ͷ
//        imgMoreCenter = (ImageView) titleView
//                .findViewById(R.id.btn_center_arrow);
//        imgMoreCenter.setVisibility(View.VISIBLE);
//        // �м����ϵļ�ͷ��ť�������򿪻�ر���ҳ
//        imgMoreCenter.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // �õ���ǰ��PageView
//                Object page = pageViews.get(mainViewPager.getCurrentItem());
//                if (page instanceof ChannelManager) {
//                    if (((ChannelManager) page).isVisibleManager()) {
//                        ((ChannelManager) page).setVisibleChannelManager(
//                                View.GONE, false);
//                        imgMoreCenter.setSelected(false);
//                        return;
//                    }
//                    ((ChannelManager) page).setVisibleChannelManager(
//                            View.VISIBLE, false);
//                    imgMoreCenter.setSelected(true);
//                }
//            }
//        });
//        txtTitle = (LetterSpacingTextView) titleView
//                .findViewById(R.id.title_name);
//        txtTitle.setVisibility(View.VISIBLE);
//        txtTitle.setPadding(BaseActivity.getDensity_int(60), BaseActivity.getDensity_int(0), 0, 0);
//        txtTitle.setTextSize(20);
//        txtTitle.setSpacing(0);
////		txtTitle.setTypeface(CmsApplication.getTitleFace(this));
//        imgSearch = (ImageView) titleView.findViewById(R.id.btn_search);
//        // imgBack = (ImageView) titleView.findViewById(R.id.img_back);
//        imgSearch.setVisibility(View.VISIBLE);
//        // imgBack.setVisibility(View.GONE);
//        imgSearch.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //IntentClassChangeUtils.startSearch(MainTabActivity.this);
//                Intent intent = new Intent(MainTabActivity.this, SearchDetailActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        imgChannel = (ImageView) findViewById(R.id.btn_news);
//        imgChannel.setVisibility(View.VISIBLE);
//        imgChannel.setImageDrawable(getResources().getDrawable(R.drawable.btn_channel_normal));
//        imgChannel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PageView pv = pageViews.get(mainViewPager.getCurrentItem());
//                if (mainViewPager.getCurrentItem() == 0) {
//                    Intent intent = new Intent(MainTabActivity.this, UsChannelAct.class);
//                    if (pv instanceof NewsPager) {
//                        intent.putExtra(Define.Channel_Type, ChannelDataUtils.Channel_News);
//                    } else if (pv instanceof InformationPager) {
//                        intent.putExtra(Define.Channel_Type, ChannelDataUtils.Channel_Read);
//                    } else {
//                        return;
//                    }
//                    startActivityForResult(intent, 1);
//                } else if (mainViewPager.getCurrentItem() == 1) {
////                    Intent intent3 = new Intent(MainTabActivity.this, AttentionWordsAct.class);
////                    startActivityForResult(intent3, 1);
//
//                    Intent intent4 = new Intent(MainTabActivity.this, AllAttetionAct.class);
//                    startActivityForResult(intent4, 1);
//                }
//            }
//        });
//
//        initLoading(titleView);
////		titleView.setTitleNewsOnclick(new TitleNewsOnclick() {
////
////			@Override
////			public void setTitleNewsOnclickLister(boolean isPop) {
////				// �ڲ�ͬ�İ����ʾ��Ӧ�ĵ���˵���
////				LoadingBasePager pageView = (LoadingBasePager) pageViews
////						.get(mainViewPager.getCurrentItem());
////				pageView.onclickPopItem(0,
////						(RelativeLayout) findViewById(R.id.rela_title));
////
////			}
////		});
//        // ����˵������¼�
//        titleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {
//
//            @Override
//            public void setNewsPopItemOnclickLister(int state) {
//                LoadingBasePager pageView = (LoadingBasePager) pageViews
//                        .get(mainViewPager.getCurrentItem());
//                pageView.onclickPopItem(state,
//                        (RelativeLayout) findViewById(R.id.rela_title));
//            }
//        });
//        // �������ϵ�ˢ�°�ť������������¼������
//        titleView.setRefreshClick(new OnRefreshClick() {
//
//            @Override
//            public void onClick() {
//                if (mainViewPager.getCurrentItem() > 0) {
//                    return;
//                }
//                LoadingBasePager pageView = (LoadingBasePager) pageViews
//                        .get(mainViewPager.getCurrentItem());
//                pageView.onRefreshListView();
//
//            }
//        });
//    }
//
//    // ��ʾ���������ϵļ�ͷ
//    private void singleListVisible(boolean isVisible) {
//        if (isVisible) {
//            imgBackLeft.setVisibility(View.VISIBLE);
//            imgSearch.setVisibility(View.GONE);
//            return;
//        }
//        imgBackLeft.setVisibility(View.GONE);
//        if (mainViewPager.getCurrentItem() <= 1) {
//            imgSearch.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * ��ʼ����һ�б�Ҳ��Ҫ��ʾ��View
//     */
//    private void initSingleList() {
//
//        // discoveryPager.isVisblePhoto(true);
//        final int cuurtentItem = mainViewPager.getCurrentItem();
//        txtTitle.setText(titleNames[cuurtentItem]);
////        if (cuurtentItem > 0) {
////            imgMoreCenter.setVisibility(View.INVISIBLE);
////            imgSearch.setVisibility(View.INVISIBLE);
////        }
//        // ��һ�б���������һ��
//        imgBackLeft = (ImageView) titleView.findViewById(R.id.img_back);
//        singleListVisible(false);
//        // ���ϼ�ͷ�����������ص��ۺ��б�
//        imgBackLeft.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                singleListVisible(false);
//                PageView page = pageViews.get(mainViewPager.getCurrentItem());
//                ((NewsBasePager) page).goRecomment();
//                txtTitle.setText(titleNames[mainViewPager
//                        .getCurrentItem()]);
//                titleNames[mainViewPager.getCurrentItem()] = titleNames[mainViewPager
//                        .getCurrentItem()];
//
//            }
//        });
//        newsPager.setListChanged(new OnListChanged() {
//
//            public void changeList(int pageIndex, String channelName,
//                                   int visible) {
//                txtTitle.setText(channelName);
//                imgBackLeft.setVisibility(visible);
//                if (visible == View.VISIBLE) {
//                    imgSearch.setVisibility(View.GONE);
//                } else {
//                    if (cuurtentItem <= 1) {
//                        imgSearch.setVisibility(View.VISIBLE);
//                    }
//                }
//                titleNames[0] = channelName;
//            }
//        });
////		informationPager.setListChanged(new OnListChanged() {
////
////			public void changeList(int pageIndex, String channelName,
////								   int visible) {
////				// ���ñ�����ΪƵ�����
////				txtTitle.setText(channelName);
////				// // ��ʾ��෵�ذ�ť
////				imgBackLeft.setVisibility(visible);
////				if (visible == View.VISIBLE) {
////					imgSearch.setVisibility(View.GONE);
////				} else {
////					if (cuurtentItem <= 1)
////					{
////						imgSearch.setVisibility(View.VISIBLE);
////					}
////				}
////				titleNames[1] = channelName;
////			}
////		});
//
//        newsPager.setOnComplete(new OnRefreshComplete() {
//
//            @Override
//            public void OnClick() {
//                if (isAnim) {
//                    setShowAnim(linNews,
//                            (ImageView) findViewById(R.id.img_news), View.GONE);
//                }
//            }
//        });
////		informationPager.setOnComplete(new OnRefreshComplete() {
////
////			@Override
////			public void OnClick() {
////				if (isAnim) {
////					setShowAnim(linRead,
////							(ImageView) findViewById(R.id.img_read), View.GONE);
////				}
////			}
////		});
//
//    }
//
//    /**
//     * ��ʼ���ײ�RadioButton
//     */
//    private void initRadioView() {
//        mainRg = (RadioGroup) findViewById(R.id.tab_rg);
//        newsRb = (RadioButton) findViewById(R.id.tab_1);
////		informationRb = (RadioButton) findViewById(R.id.tab_2);
//        attenRb = (RadioButton) findViewById(R.id.tab_3);
//        msgRb = (RadioButton) findViewById(R.id.tab_4);
//        userRb = (RadioButton) findViewById(R.id.tab_5);
//        mainRg.setOnCheckedChangeListener(this);
//
//        /**
//         * �����涼��Ϊ��tabˢ�ºͶ���
//         */
//        view1 = (View) findViewById(R.id.view1);
////		view2 = (View) findViewById(R.id.view2);
//        view3 = (View) findViewById(R.id.view3);
//        view4 = (View) findViewById(R.id.view4);
//        view5 = (View) findViewById(R.id.view5);
//        view1.setOnClickListener(new OnRbClick());
////		view2.setOnClickListener(new OnRbClick());
//        view3.setOnClickListener(new OnRbClick());
//        view4.setOnClickListener(new OnRbClick());
//        view5.setOnClickListener(new OnRbClick());
//        linNews = (LinearLayout) findViewById(R.id.lin_refre_news);
////		linRead = (LinearLayout) findViewById(R.id.lin_refre_read);
//
//        int width = this.getScreenWidth() / 4;
//        newsRb.getLayoutParams().width = width;
////		informationRb.getLayoutParams().width = width;
//        msgRb.getLayoutParams().width = width;
//        userRb.getLayoutParams().width = width;
//        attenRb.getLayoutParams().width = width;
//
//    }
//
//    /**
//     * ��ʼ��ViewPager�����Pager��ViewPager��
//     */
//    private void initViewPager() {
//        mainViewPager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
//        mainViewPager.setNoScroll(true);
//        pageViews = new ArrayList<PageView>();
//
//        txtMsgNum = (TextView) findViewById(R.id.txt_msgnum);
//
//        newsPager = new NewsPager(this, this, imgMoreCenter);
////			informationPager = new InformationPager(this, this, imgMoreCenter);
//        msgPager = new MsgPager(this, txtMsgNum);
//        settingPager = new SettingPager(this);
//        attentionPager = new AttentionPager(this);
//
//        pageViews.add(newsPager);
////		pageViews.add(informationPager);
//        pageViews.add(attentionPager);
//        pageViews.add(msgPager);
//        pageViews.add(settingPager);
//        addNewsMsg();
//
//        mainViewPager.init(pageViews, tabId);
//        // if (tabId == TabPhotoId) {
//        // imgMoreCenter.setVisibility(View.GONE);
//        // txtTitle.setPadding(0, 0, 0, 0);
//        // }
//        setButtonPerformClick(tabId);
//        mainViewPager.setViewPageSelectedLister(new ViewPagerSelected() {
//
//            @Override
//            public void onPageSelected(int currentNum) {
//                // TODO Auto-generated method stub
//                setButtonPerformClick(currentNum);
//            }
//        });
//
//    }
//
//    /**
//     * �������¼� ����tab���ˢ��
//     */
//    class OnRbClick implements OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == R.id.view1) {
//                if (newsRb.isChecked()) {
//                    if (isAnim) {
//                        return;
//                    }
//                    setShowAnim(linNews,
//                            (ImageView) findViewById(R.id.img_news),
//                            View.VISIBLE);
//                    newsPager.onRefreshListView();
//
//                    return;
//                }
//                newsRb.setChecked(true);
////			} else if (v.getId() == R.id.view2) {
////				if (informationRb.isChecked()) {
////					if (isAnim) {
////						return;
////					}
////					informationPager.onRefreshListView();
////					setShowAnim(linRead,
////							(ImageView) findViewById(R.id.img_read),
////							View.VISIBLE);
////					return;
////				}
////				informationRb.setChecked(true);
//            } else if (v.getId() == R.id.view3) {
//                if (attenRb.isChecked()) {
//                    return;
//                }
//                attenRb.setChecked(true);
//
//            } else if (v.getId() == R.id.view4) {
//                if (msgRb.isChecked()) {
//                    return;
//                }
//                msgRb.setChecked(true);
//
//            } else if (v.getId() == R.id.view5) {
//                if (userRb.isChecked()) {
//                    return;
//                }
//                userRb.setChecked(true);
//            }
//        }
//
//    }
//
//    /**
//     * ������ʾ�����ض���
//     */
//
//    private void setShowAnim(LinearLayout linLayout, ImageView img, int Visible) {
//
//        //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
////		newsRb.setBounds(0, 0, 0, 0);
//
//        if (Visible == -100) {
//            linNews.setVisibility(View.GONE);
////			linRead.setVisibility(View.GONE);
////			Drawable top = getResources().getDrawable(drawable);
////			newsRb.setCompoundDrawablePadding(getDensity_int(2));
////			newsRb.setCompoundDrawables(null,top,null,null);
//            isAnim = false;
//            return;
//        }
//        linLayout.setVisibility(Visible);
//        if (Visible == View.VISIBLE) {
//            Animation operatingAnim = AnimationUtils.loadAnimation(this,
//                    R.anim.anim_rote);
//            LinearInterpolator lin = new LinearInterpolator();
//            operatingAnim.setInterpolator(lin);
//            img.startAnimation(operatingAnim);
//            isAnim = true;
//        } else if (Visible == View.GONE) {
//            isAnim = false;
//        }
//    }
//
//    /**
//     * �൱�ڵ����Ӧ��RadioButton
//     *
//     * @param position
//     */
//    public void setButtonPerformClick(int position) {
//        if (position == 0) {
//            newsRb.performClick();
////		} else if (position == 1) {
////			informationRb.performClick();
//        } else if (position == 1) {
//            attenRb.performClick();
//        } else if (position == 2) {
//            msgRb.performClick();
//        } else if (position == 3) {
//            userRb.performClick();
//        }
//
//    }
//
//    int lastOnewId = -1;
//
//    /**
//     * RadioButtonѡ��ı�ʱ�����
//     */
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//        int id = 0;
//        if (checkedId == R.id.tab_1) {
//            id = 0;
//            tabId = TabNewsId;
////		} else if (checkedId == R.id.tab_2) {
////			id = 1;
////			tabId = TabReadId;
//        } else if (checkedId == R.id.tab_3) {
//            id = 1;
//            tabId = TabAttent;
//        } else if (checkedId == R.id.tab_4) {
//            id = 2;
//            tabId = TabNotif;
//        } else if (checkedId == R.id.tab_5) {
//            id = 3;
//            tabId = TabMyId;
//        }
//        mainViewPager.setPageIndex(id);
//        mainViewPager.setCurrentItem(id, false);
//        setShowAnim(null, null, -100);
//
//        PageView page = pageViews.get(mainViewPager.getCurrentItem());
//        lastOnewId = checkedId;
//
//        if (tabId == TabNotif) {
//            ((MsgPager) page).refreshMsg();
//            txtMsgNum.setVisibility(View.GONE);
//            messageUnread = 0;
//        }
//        if (tabId == TabAttent) {
//            ((AttentionPager) page).onRefreshAt();
//        }
//
//        if (txtTitle == null || imgBackLeft == null) {
//            return;
//        }
//        txtTitle.setText(titleNames[id]);
//
//        if (page instanceof ChannelManager && !((ChannelManager) page).isRecomment()) {
//            singleListVisible(true);
//        }else {
//            singleListVisible(false);
//        }
//
//
//        if (page instanceof NewsPager || page instanceof InformationPager) {
//            imgMoreCenter.setVisibility(View.VISIBLE);
//            txtTitle.setPadding(BaseActivity.getDensity_int(60), BaseActivity.getDensity_int(0), 0, 0);
//            imgMoreCenter.setSelected(((ChannelManager) page).isVisibleManager());
//        } else {
//            imgMoreCenter.setVisibility(View.GONE);
//            imgSearch.setVisibility(View.GONE);
//            txtTitle.setPadding(0, BaseActivity.getDensity_int(0), 0, 0);
//
//        }
//        if (page instanceof NewsPager || page instanceof AttentionPager) {
//            imgChannel.setVisibility(View.VISIBLE);
//        } else {
//            imgChannel.setVisibility(View.GONE);
//        }
//
//    }
//
//    @Override
//    protected void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        if (titleView != null) {
//            titleView.setNews();
//        }
//
//        freshNewsPage(isRefresh());
//
//        pageResume();
//    }
//
//    private void addNewsMsg() {
//        msgPager.setMsgCallBack(new MsgPager.NewMsgCallBack() {
//            @Override
//            public void callnewMsg() {
//                if (tabId != TabNotif) {
//                    messageUnread ++;
//                    txtMsgNum.setVisibility(View.VISIBLE);
//                    if (messageUnread>9){
//                        txtMsgNum.setText(String.valueOf(9)+"+");
//                    }else {
//                        txtMsgNum.setText(String.valueOf(messageUnread));
//                    }
//
//                }
//            }
//        });
//    }
//
//    public boolean isRefresh() {
//        int lines = AccountManager.getInstance().getSummary_Is_Display();
//        boolean isAbstractChange = !(abstractLines == lines);
//        abstractLines = isAbstractChange ? lines : abstractLines;
//
//        boolean isFontChange = !(fontType == AccountManager.getInstance()
//                .getCurrentSize());
//        fontType = isFontChange ? AccountManager.getInstance().getCurrentSize()
//                : fontType;
//
//        boolean isCountrySet = !(countryType == CountryMannager.getInstance().getCurrentCountry());
//        countryType = isCountrySet ? CountryMannager.getInstance().getCurrentCountry()
//                : countryType;
//
//        return isFontChange | isAbstractChange | isCountrySet;
//    }
//
//    public void pageResume() {
//        if (Utils.isStringEmpty(pageViews)) {
//            return;
//        }
//        for (PageView pv : pageViews) {
//            if (pv != null) {
//                pv.onResume();
//            }
//        }
//    }
//
//    /**
//     * ����ı�ʱ ˢ���б�
//     *
//     * @param isFontChange true.�ı� false.û��
//     */
//    private void freshNewsPage(boolean isFontChange) {
//        if (Utils.isStringEmpty(pageViews)) {
//            return;
//        }
//        newsPager.onRefresh(isFontChange);
////		informationPager.onRefresh(isFontChange);
////		pageViews.get(mainViewPager.getCurrentItem()).onRefresh(isFontChange);
//    }
//
//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        pagePause();
//    }
//
//    public void pagePause() {
//        if (Utils.isStringEmpty(pageViews)) {
//            return;
//        }
//        for (PageView pv : pageViews) {
//            if (pv != null) {
//                pv.onPause();
//            }
//        }
//    }
//
//    /*
//     * (non-Javadoc)
//     *
//     * @see com.baiyi.jj.app.activity.BaseActivity#onDestroy()
//     */
//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//        pageDestroy();
//    }
//
//    public void pageDestroy() {
//        if (Utils.isStringEmpty(pageViews)) {
//            return;
//        }
//        for (PageView pv : pageViews) {
//            if (pv != null) {
//                pv.onDestroy();
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        super.onActivityResult(requestCode, resultCode, data);
//        if (Utils.isStringEmpty(pageViews)) {
//            return;
//        }
//        pageViews.get(mainViewPager.getCurrentItem()).onActivityResult(
//                requestCode, resultCode, data);
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (newsRb.isChecked() && !newsPager.isRecomment()) {
//                ((NewsPager) newsPager).goRecomment();
//                return true;
//            }
////			else if (informationRb.isChecked()
////					&& !informationPager.isRecomment()) {
////				((InformationPager) informationPager).goRecomment();
////				return true;
////			}
//            if (newsRb.isChecked()) {
//                if ((System.currentTimeMillis() - exitTime) > 2000) {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.txt_exit_app),
//                            Toast.LENGTH_SHORT).show();
//
//                    exitTime = System.currentTimeMillis();
//
//                } else {
//                    destory();
//                    finish();
//                    System.exit(0);
//                }
//            } else {
//                newsRb.setChecked(true);
//
//            }
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void setThemeChangeCallBack() {
//        // TODO Auto-generated method stub
//        // ThemeUtil.setFontOrTheme(titleView, ThemeUtil.getAppTheme(),
//        // FontUtil.getFontSizeType());
//    }
//
//    @Override
//    protected void netWorkChange(boolean isGprs) {
//        // TODO Auto-generated method stub
//        super.netWorkChange(isGprs);
//        for (PageView pv : pageViews) {
//            if (pv != null) {
//                pv.onRefreshToNetWorkChange(isGprs);
//            }
//        }
//    }
//}
