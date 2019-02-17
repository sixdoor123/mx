package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.cache.Dao.OfflineChannelDao;
import com.baiyi.jj.app.language.LanguageSelectUtils;
import com.baiyi.jj.app.utils.Define;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.entity.LanguageEntities;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ����ѡ��
 * Created by tangkun on 16/9/8.
 */
public class LanguageSetAct extends BaseActivity {
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.text_right)
    TextView textRight;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.language_list)
    RecyclerView languageList;
    @Bind(R.id.loading)
    MyLoadingBar loading;

    private LanguageAdapter languageAdapter;

    private List<LanguageEntities> languages = new ArrayList<>();

    private LanguageEntities selLanguage;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_language_us, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        initTitle();
        initList();
        loadLanguageList();
    }

    /**
     * 获取语言列表
     */
    private void loadLanguageList() {

        LanguageEntities entityEn = new LanguageEntities();
        entityEn.setId(String.valueOf(SwitchLanguageUtils.languageid_EN));
        entityEn.setName(getString(R.string.txt_language_en));
        languages.add(entityEn);
        LanguageEntities entityHi = new LanguageEntities();
        entityHi.setId(String.valueOf(SwitchLanguageUtils.languageid_ES));
        entityHi.setName(getString(R.string.txt_language_hi));
        languages.add(entityHi);

        selLanguage = LanguageSelectUtils.getCurrentLanguageEntities(languages,this);
        languageAdapter.setSelectId(LanguageSelectUtils.getCurrentId(selLanguage, LanguageSetAct.this));
        languageAdapter.setData(languages);

    }

    private void initTitle() {
        imgBack.setVisibility(View.VISIBLE);
        titleName.setText(getResources().getString(R.string.txt_account_language));
        textRight.setVisibility(View.VISIBLE);
        textRight.setTextColor(getResources().getColor(R.color.word_white_red_press));
        textRight.setText(getResources().getString(R.string.txt_button25));
    }

    private void initList() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        languageList.setLayoutManager(lm);
        languageAdapter = new LanguageAdapter();
        languageList.setAdapter(languageAdapter);
    }

    class LanguageAdapter extends BaseItemAdapter<LanguageEntities> {


        @Override
        public int getLayout() {
            return R.layout.item_us_language;
        }

        @Override
        public BaseViewHolder getBindingHolder(View view) {
            return new LanguageHolder(view);
        }

        @Override
        public UIDataBase getUIDataItem(LanguageEntities live) {
            return new UIDataLanguage(live);
        }

        class UIDataLanguage extends UIDataBase<LanguageEntities> {
            public UIDataLanguage(LanguageEntities data) {
                super(data);
            }
        }

        class LanguageHolder extends BaseViewHolder<UIDataLanguage> {
            TextView name;
            ImageView imgSelect;
            LinearLayout btnItem;

            public LanguageHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.txt_name);
                imgSelect = (ImageView) itemView.findViewById(R.id.img_select);
                btnItem = (LinearLayout) itemView.findViewById(R.id.btn_item);
                btnItem.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        setSelectId(Integer.parseInt(languages.get(getAdapterPosition()).getId()));
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void bind(@NonNull UIDataLanguage data, int position) {
                if (Integer.parseInt(languages.get(position).getId()) == getSelectId()) {
                    imgSelect.setVisibility(View.VISIBLE);
                    selLanguage = data.data;
                } else {
                    imgSelect.setVisibility(View.GONE);
                }

                name.setText(data.data.getName());
            }
        }
    }

    private void loadSaveLanguage() {

        if (null == selLanguage) {
            return;
        }
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getModifyLanguageUrl());
        loader.setUrlName(MemberConfig.Modify_Language);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getModifyLanguagePostData(selLanguage));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                stopLoading();
                displayToast(getResources().getString(R.string.txt_paypal_change_failure));
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                JSONArray array = (JSONArray) result;
                stopLoading();

                StateEnties enties = SettingManager.getModifyLanguageResult(array);
                displayToast(getString(R.string.txt_paypal_change_success));
                if (200 != enties.getState()) {
                    return;
                }
                new ChannelDao(LanguageSetAct.this).deleteAll();
//                new OfflineChannelDao(LanguageSetAct.this).deleteAll();

                SwitchLanguageUtils.selectLanguage(LanguageSetAct.this, selLanguage.getName());
                Intent intent = new Intent(LanguageSetAct.this, HomeTabAct.class);
                startActivity(intent);
                finish();
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    @OnClick({R.id.img_back, R.id.text_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.text_right:
                loadSaveLanguage();
                break;
        }
    }

    private void startLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
        loading.start();
    }

    private void stopLoading() {
        if (loading != null) {
            loading.stop();
            loading.setVisibility(View.GONE);
        }
    }
}
