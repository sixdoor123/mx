package com.baiyi.jj.app.activity.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.attention.AllAttetionAct;
import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.activity.attention.MyAttentionListAct;
import com.baiyi.jj.app.activity.attention.net.AttetionNet;
import com.baiyi.jj.app.activity.attention.net.JsonAttention;
import com.baiyi.jj.app.activity.attention.view.IXListViewListener;
import com.baiyi.jj.app.activity.attention.view.OnMenuItemClickListener;
import com.baiyi.jj.app.activity.attention.view.PullToRefreshSwipeMenuListView;
import com.baiyi.jj.app.activity.attention.view.SwipeMenu;
import com.baiyi.jj.app.activity.attention.view.SwipeMenuCreator;
import com.baiyi.jj.app.activity.attention.view.SwipeMenuItem;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.AttentionCacheManager;
import com.baiyi.jj.app.cache.Dao.AttetionWordsDao;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.turbo.turbo.mexico.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class TagFragment extends Fragment{

    @Bind(R.id.list_attent)
    PullToRefreshSwipeMenuListView mListView;
    @Bind(R.id.lin_shownoattetion)
    LinearLayout linShowAtt;
    @Bind(R.id.img_right)
    ImageView imgRight;

    private AppAdapter mAdapter;


    public static final int AttetCode = 33;

    private int pageindex = 1;

    private List<AttentionWordsEntity> myAttenList;

    private String mid;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;

    private String name;
    private int currentCountry;

    public static TagFragment newInstance(String channelId) {
        TagFragment fragment = new TagFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", channelId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        currentCountry = CountryMannager.getInstance().getCurrentCountry();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_attention, container, false);
        ButterKnife.bind(this, view);
        titleName.setText(getResources().getString(R.string.txt_attention_title));
        imgRight.setVisibility(View.VISIBLE);
        initMenu();
        return view;
    }
    private void initMenu() {

        mAdapter = new AppAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh() {
                pageindex = 1;
                loadMyAttentionList();
            }

            @Override
            public void onLoadMore() {
                pageindex++;
                loadMyAttentionList();

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 1) {
                    return;
                }
                Intent intent = new Intent(getContext(), MyAttentionListAct.class);
                intent.putExtra(Define.HotWord,myAttenList.get(position-1));
                intent.putExtra(Define.IsAttention, true);
                TLog.e("hotid", "hotid----" + String.valueOf(myAttenList.get(position - 1).getHotword_id()));
                ((BaseActivity) getContext()).startActivityForResult(intent, 0);
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(R.drawable.bg_right_blue);
                deleteItem.setTitle(getContext().getResources().getString(R.string.txt_attention_cancel));
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(getContext().getResources().getColor(R.color.day_text_color_write));
                deleteItem.setWidth(dp2px(90));
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:// delete
                        mAdapter.remove(position);
                        TLog.e("del", myAttenList.get(position).getWords() + "===============" + myAttenList.get(position).getChannel_name() + "----" + myAttenList.get(position).getAlias());
                        loadDelAttention(myAttenList.get(position));
                        myAttenList.remove(position);
                        if (myAttenList.size() == 0) {
                            linShowAtt.setVisibility(View.VISIBLE);
                            mListView.setNoMoreUnShow();
                        }
                        break;
                }
            }
        });
    }

    private void initData() {
        if (CmsApplication.getUserInfoEntity() != null) {
            mid = CmsApplication.getUserInfoEntity().getMID();
        }
        myAttenList = new AttetionWordsDao(getContext()).queryAllAttetioned(mid);
        if (Utils.isStringEmpty(myAttenList)){
            myAttenList = new ArrayList<>();
        }
        AttentionCacheManager.getInstance().clear();
        AttentionCacheManager.getInstance().addCacheList(myAttenList);
        mAdapter.setData(myAttenList);
        if (Utils.isStringEmpty(myAttenList)) {
            setRefreshList();
        }else{
            linShowAtt.setVisibility(View.GONE);
        }
    }

    private void setRefreshList() {
        mListView.refresh();
        pageindex = 1;
        loadMyAttentionList();
    }

    public void onRefreshAt() {
        if (AttentionCacheManager.getInstance().isTagRefresh()){
            setRefreshList();
            AttentionCacheManager.getInstance().setTagRefresh(false);
            return;
        }
        String midStr = CmsApplication.getUserInfoEntity() != null ? CmsApplication.getUserInfoEntity().getMID() : "";
        if (!midStr.equals(mid)) {
            mAdapter.setData(new ArrayList<AttentionWordsEntity>());
            setRefreshList();
            mid = midStr;
        }
        if (currentCountry != CountryMannager.getInstance().getCurrentCountry()){
            setRefreshList();
            currentCountry = CountryMannager.getInstance().getCurrentCountry();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefreshAt();
    }

    @OnClick(R.id.img_right)
    public void OnRightClick() {

        Intent intent = new Intent(getContext(), AllAttetionAct.class);
        ((BaseActivity) getContext()).startActivityForResult(intent, 1);
    }
    @OnClick(R.id.btn_addattention)
    public void onClick(View view) {
        Intent intent3 = new Intent(getContext(), AllAttetionAct.class);
        ((BaseActivity) getContext()).startActivityForResult(intent3, 1);
    }

    private void loadDelAttention(final AttentionWordsEntity entity) {

        new AttetionNet().loaddelAttention(String.valueOf(entity.getHotword_id()), new AttetionNet.delCallBack() {
            @Override
            public void callBack(int state) {
                if (state == AttetionNet.State_Success) {
                    AttentionCacheManager.getInstance().deleteOneCache(entity.getChannel_id()+""+entity.getHotword_id());
                    entity.setAttet(false);
                    new AttetionWordsDao(getContext()).updateOrAdd(entity);
                } else {

                }
            }
        });
    }
    private void loadMyAttentionList() {


        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            mListView.stopRefresh();
            mListView.stopLoadMore();
//            linShowAtt.setVisibility(View.VISIBLE);
            return;
        }
        linShowAtt.setVisibility(View.GONE);

        final long start = System.currentTimeMillis();
        Buffer buffer;
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        TLog.e("abc1", NetUrl.getAttetionList(pageindex, Constant.limit_attetion));
        Request request = new Request.Builder()
                .url(NetUrl.getAttetionList(pageindex, Constant.limit_attetion))
                .get()
                .header("Content-Type", "application/json")
                .header("Authorization", CmsApplication.getUserToken())
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long value = System.currentTimeMillis() - start;
                ((BaseActivity)getContext()).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Tag,value);
                final String Json = response.body().string();
                TLog.e("abc", "json-===" + Json);
                if (getContext() == null){
                    return;
                }
                ((BaseActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<AttentionWordsEntity> attentionList = JsonAttention.getFollowWords(Json);
                        mListView.stopRefresh();
                        mListView.stopLoadMore();
                        TLog.e("ATG", pageindex + "");

                        if (Utils.isStringEmpty(attentionList)) {
                            if (pageindex == 1) {

                                myAttenList.clear();
                                AttentionCacheManager.getInstance().clear();
                                mAdapter.clear();
                                linShowAtt.setVisibility(View.VISIBLE);
                                mListView.setNoMoreUnShow();
                            } else {
                                mListView.setNoMore();
                            }
                            return;
                        }
                        linShowAtt.setVisibility(View.GONE);
                        if (pageindex == 1) {
                            myAttenList.clear();
                            myAttenList.addAll(attentionList);
                            mAdapter.setData(myAttenList);
                            AttentionCacheManager.getInstance().clear();
                            AttentionCacheManager.getInstance().addCacheList(myAttenList);
                            new AttetionWordsDao(getContext()).updateCacheList(myAttenList);

                        } else {
                            myAttenList.addAll(attentionList);
                            mAdapter.addData(attentionList);
                        }
                    }
                });


            }
        });
    }

    class AppAdapter extends BaseAdapter {
        private List<AttentionWordsEntity> attentionList;
        private int imgFillH = BaseActivity.getDensity_int(40);
        private int imgFillW = (int) (imgFillH/0.68);

        public AppAdapter() {
            this.attentionList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return attentionList.size();
        }

        @Override
        public AttentionWordsEntity getItem(int position) {
            return attentionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void remove(int position) {
            attentionList.remove(position);
            this.notifyDataSetInvalidated();
        }


        public void setData(List<AttentionWordsEntity> list) {
            if (Utils.isStringEmpty(list)) {
                return;
            }
            attentionList.clear();
            attentionList.addAll(list);
            this.notifyDataSetInvalidated();
        }

        public void addData(List<AttentionWordsEntity> list) {
            if (Utils.isStringEmpty(list)) {
                return;
            }
            attentionList.addAll(list);
            this.notifyDataSetInvalidated();
        }

        public void clear() {
            attentionList.clear();
            this.notifyDataSetInvalidated();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(),
                        R.layout.item_myattent, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            AttentionWordsEntity item = getItem(position);
            GlideTool.getListImage(getContext(), item.getIcon(), holder.iv_icon);
            holder.tv_name.setText(item.getAlias());
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);

                iv_icon.getLayoutParams().height = imgFillH;
                iv_icon.getLayoutParams().width = imgFillW;

                view.setTag(this);
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void onActivityResult(int resultCode) {
        if (resultCode == AttetCode) {
            setRefreshList();
        }
    }
}
