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
import com.baiyi.jj.app.language.LanguageSelectUtils;
import com.baiyi.jj.app.language.PreferenceUtil;
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
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ����ѡ��
 * Created by tangkun on 16/9/8.
 */
public class CountrySetAct extends BaseActivity {
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.text_right)
    TextView textRight;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.country_list)
    RecyclerView countryList;
    @Bind(R.id.loading)
    MyLoadingBar loading;

    private CountryAdapter countryAdapter;

    private List<LanguageEntities> countries = null;

    private String currentCountry;

    private LanguageEntities selCountry;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_country_us, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        initTitle();
        initList();

        initData();
        LoadCountryList();
    }

    private void LoadCountryList() {
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getCountryListUrl());
        loader.setUrlName(MemberConfig.Get_Country_List);
        loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
        loader.setPostData(new JSONObject().toString());
        loader.setMethod(BaseNetLoder.Method_Get);
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

                countries = SettingManager.getLanguageListData(array);
                selCountry = LanguageSelectUtils.getCurrentCountryEntities(countries,currentCountry);
                countryAdapter.setSelectId(LanguageSelectUtils.getCurrentId(selCountry,CountrySetAct.this));
                countryAdapter.setData(countries);
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    private void initData()
    {
        currentCountry = getIntent().getStringExtra(MemberConfig.Country);
    }

    private void initTitle() {
        imgBack.setVisibility(View.VISIBLE);

        titleName.setText(getResources().getString(R.string.txt_account_country));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        textRight.setVisibility(View.VISIBLE);
        textRight.setTextColor(getResources().getColor(R.color.word_white_red_press));
        textRight.setText(getResources().getString(R.string.txt_button25));
    }

    private void initList()
    {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        countryList.setLayoutManager(lm);

        countryAdapter = new CountryAdapter();
        countryList.setAdapter(countryAdapter);
    }

    class CountryAdapter extends BaseItemAdapter<LanguageEntities>{


        @Override
        public int getLayout() {
            return R.layout.item_us_country;
        }

        @Override
        public BaseViewHolder getBindingHolder(View view) {
            return new LanguageHolder(view);
        }

        @Override
        public UIDataBase getUIDataItem(LanguageEntities s) {
            return new UIDataLanguage(s);
        }


        class UIDataLanguage extends UIDataBase<LanguageEntities>{
            public UIDataLanguage(LanguageEntities data) {
                super(data);
            }
        }

        class LanguageHolder extends BaseViewHolder<UIDataLanguage>{
            TextView name;
            ImageView imgSelect;
            LinearLayout btnItem;
            public LanguageHolder(View itemView) {
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.txt_name);
                imgSelect = (ImageView)itemView.findViewById(R.id.img_select);
                btnItem = (LinearLayout)itemView.findViewById(R.id.btn_item);
                btnItem.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        setSelectId(Integer.parseInt(countries.get(getAdapterPosition()).getId()));
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void bind(@NonNull UIDataLanguage data, int position) {
                if (Integer.parseInt(countries.get(position).getId()) == getSelectId())
                {
                    imgSelect.setVisibility(View.VISIBLE);
                    selCountry = data.data;
                }else
                {
                    imgSelect.setVisibility(View.GONE);
                }
                name.setText(data.data.getName());
            }
        }
    }

    private void loadData() {

        if (null == selCountry){
            return;
        }
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getModifyCountryUrl());
        loader.setUrlName(MemberConfig.Modify_Country);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getModifyCountryPostData(selCountry));
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
                displayToast(enties.getMsg());
                if (200 != enties.getState())
                {
                    return;
                }

                PreferenceUtil.commitString(MemberConfig.App_Country, selCountry.getName());
                Intent intent = new Intent();
                UserInfoEntity entity = CmsApplication.getUserInfoEntity();
                if (entity != null && entity.getUserType() != UserInfoEntity.UserType_Gust)//第三方登录
                {
                    intent.putExtra(MemberConfig.Country, selCountry.getName());
                }
                setResult(MemberConfig.Country_ResultCode,intent);
                setExitSwichLayout();
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
                loadData();
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
