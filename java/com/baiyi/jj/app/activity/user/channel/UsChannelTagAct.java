package com.baiyi.jj.app.activity.user.channel;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.UserNetCallBack;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.SortChannelComparator;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.ChannelFlowLayout;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第一次安装的时候选择频道界面
 */
public class UsChannelTagAct extends BaseActivity {

    @Bind(R.id.tag_channel_view)
    ChannelFlowLayout channelView;
    @Bind(R.id.loading)
    MyLoadingBar loadingBar;

    private List<ChannelItem> AllList;
    private List<ChannelItem> UserList;
    private List<ChannelItem> OtherList;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.act_channel_tag_first, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        loadChannelData();
    }


    public void loadChannelData() {

        String channel = TextLengthUtils.getAssetsStr(this, "channel_init");

        if (Utils.isStringEmpty(channel)) {
            return;
        }
        try {
            JSONArray array1 = new JSONArray(channel);
            List<List<ChannelItem>> dataList = JsonParse.getChannelList2(array1, ChannelDataManager.ChannelType_News);
            List<ChannelItem> defaultUserChannels = dataList.get(0);
            List<ChannelItem> defaultOtherChannels = dataList.get(1);

            Comparator comp = new SortChannelComparator();
            Collections.sort(defaultUserChannels, comp);
            AllList = defaultOtherChannels;
            channelView.setTags(AllList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            addEnvent(BaseAnalyticsActivity.Envent_InitChannel_KeyDown);
            exit(true);
        }
        return true;
    }

    private void exit(final boolean isSkip) {
        UserList = new ArrayList<>();
        OtherList = new ArrayList<>();
//        Preference.getInstance().Set(XMLName.XML_First_Logo, String.valueOf(false));
//        Preference.getInstance().saveConfig();

//        if (Utils.isStringEmpty(CmsApplication.getUserToken())){
//            startLoading(loadingBar,"");
//            new UserNetCallBack(this, new UserNetCallBack.CallBack() {
//                @Override
//                public void callBackLister(int state, UserInfoEntity entity) {
//                    stopLoading(loadingBar);
//                    if (state == UserNetCallBack.SUCCESS) {
//                        if (entity == null) {
//                            return;
//                        }
//                        CmsApplication.setUserInfoEntity(entity, UsChannelTagAct.this);
//                    }
//                    intentToHome(isSkip);
//                }
//            }).loadInitGust(false);
//
//            return;
//        }
        intentToHome(isSkip);
    }

    private void intentToHome(boolean isSkip){

        if (isSkip) {
            for (ChannelItem item : AllList) {
                if (item.getIs_default().equals("false")) {
                    item.setIs_default("true");
                }
                UserList.add(item);
            }
        } else {
            for (ChannelItem item : AllList) {
                if (item.getIs_default().equals("true")) {
                    UserList.add(item);
                } else {
                    OtherList.add(item);
                }
            }
        }

        if (Utils.isStringEmpty(UserList) || UserList.size() < 3) {
            displayToast(getResources().getString(R.string.txt_choose_3channel));
            return;
        }
        ChannelDataManager.getInstance(this).updateNetChannel(ChannelDataManager.ChannelType_News, UserList, OtherList, new ChannelDataManager.ChannelSaveCallBack() {
            @Override
            public void onSaveCallBack(boolean isComplete, String errorMsg) {
                if (isComplete) {
                    updateApp();
                }
            }
        },"class-uschannaltag");
    }

    public void updateApp() {
        Intent intent = new Intent(this, HomeTabAct.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (getIntent().getBundleExtra(Define.EXTRA_BUNDLE) != null) {
            intent.putExtra(Define.EXTRA_BUNDLE,
                    getIntent().getBundleExtra(Define.EXTRA_BUNDLE));
        }
        intent.putExtra(Define.FirstToHome,"PlanA");
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.btn_channel_ok, R.id.tv_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_channel_ok:
                exit(false);
                addEnvent(BaseAnalyticsActivity.Envent_InitChannel_Subscr);
                break;
            case R.id.tv_skip:
                addEnvent(BaseAnalyticsActivity.Envent_InitChannel_Skip);
                exit(true);
                break;
        }
    }
}
