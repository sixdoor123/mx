package com.baiyi.jj.app.activity.user.channel;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.SortChannelComparator;
import com.baiyi.jj.app.utils.ChannelIconManager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

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
 * �°�Ƶ������
 * Created by tangkun on 16/9/9.
 */
public class UsChannelAct extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_search)
    ImageView imgEdit;
    @Bind(R.id.channel_list_first)
    XRecyclerView channelList;

    private ChannelAdapter channelAdapter;

    private List<ChannelItem> AllList;
    private List<ChannelItem> UserList;
    private List<ChannelItem> OtherList;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.act_channel_first, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        initTitle();
        initList();
        loadChannelData();
    }

    private void initTitle() {
        titleName.setText(getResources().getString(R.string.title_channel));
        imgBack.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
    }


    private void initList() {

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        channelList.setLayoutManager(lm);
        channelList.setNoMore();
        channelList.setPullRefreshEnabled(false);
        channelAdapter = new ChannelAdapter();
        channelList.setAdapter(channelAdapter);
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
            AllList = defaultUserChannels;
            channelAdapter.setData(AllList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class ChannelAdapter extends BaseItemAdapter<ChannelItem> {

        private int imgFillH = BaseActivity.getDensity_int(18);
        private int imgFillW = imgFillH;

        @Override
        public int getLayout() {
            return R.layout.item_us_channel;
        }

        @Override
        public BaseViewHolder getBindingHolder(View view) {
            return new LanguageHolder(view);
        }

        @Override
        public UIDataBase getUIDataItem(ChannelItem s) {
            return new UIDataLanguage(s);
        }


        class UIDataLanguage extends UIDataBase<ChannelItem> {
            public UIDataLanguage(ChannelItem data) {
                super(data);
            }
        }


        class LanguageHolder extends BaseViewHolder<UIDataLanguage> {
            TextView name;
            ImageView imgHead;
            CheckBox checkBox;
            View linParent;

            public LanguageHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.txt_name);
                imgHead = (ImageView) itemView.findViewById(R.id.img_head);
                checkBox = (CheckBox) itemView.findViewById(R.id.check_select);
                linParent = itemView.findViewById(R.id.btn_item);

                imgHead.getLayoutParams().height = imgFillH;
                imgHead.getLayoutParams().width = imgFillW;

            }

            @Override
            public void bind(@NonNull final UIDataLanguage data, final int position) {

                if (data.data.getIs_default().equals("true")) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                name.setText(data.data.getChannel_name());
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        ChannelItem item = data.data;
                        if (cb.isChecked()) {
                            data.data.setIs_default("true");
                            AllList.get(position).setIs_default("true");
                        } else {
                            data.data.setIs_default("false");
                            AllList.get(position).setIs_default("false");
                        }
                    }
                });

                linParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBox.setChecked(!checkBox.isChecked());
                        ChannelItem item = data.data;
                        if (checkBox.isChecked()) {
                            data.data.setIs_default("true");
                            AllList.get(position).setIs_default("true");
                        } else {
                            data.data.setIs_default("false");
                            AllList.get(position).setIs_default("false");
                        }
                    }
                });

                //GlideTool.getSignatureImage(UsChannelAct.this, data.data.getConvert_img(), imgHead);
                imgHead.setBackgroundResource(ChannelIconManager.getInstance(UsChannelAct.this).getIconUn(Integer.parseInt(data.data.getCid())));
            }
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        exit();
        return true;
    }

    private void exit() {
        UserList = new ArrayList<>();
        OtherList = new ArrayList<>();

        for (ChannelItem item : AllList) {
            if (item.getIs_default().equals("true")) {
                UserList.add(item);
            } else {
                OtherList.add(item);
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
        },"class-uschannalact");
    }
    public void updateApp() {
        Intent intent = new Intent(this, HomeTabAct.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if(getIntent().getBundleExtra(Define.EXTRA_BUNDLE) != null){
            intent.putExtra(Define.EXTRA_BUNDLE,
                    getIntent().getBundleExtra(Define.EXTRA_BUNDLE));
        }
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.btn_channel_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_channel_ok:
                exit();
                break;
        }
    }
}
