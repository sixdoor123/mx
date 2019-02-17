package com.baiyi.jj.app.activity.attention;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiyi.jj.app.activity.attention.net.AttetionNet;
import com.baiyi.jj.app.adapter.SearchAttentionAdapter;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class AttetionFragment extends Fragment {


    @Bind(R.id.attention_list)
    XRecyclerView mListView;

    private SearchAttentionAdapter attentionAdapter;

    private String ChannelId;

    public static AttetionFragment newInstance(String channelId) {
        AttetionFragment fragment = new AttetionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Define.ChannelId, channelId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChannelId = getArguments().getString(Define.ChannelId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attetion, container, false);
        ButterKnife.bind(this, view);
        initRecycleView();
        initData();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        attentionAdapter.refreshList();
    }

    private void initData() {
//        List<AttentionWordsEntity> lists = new ArrayList<>();
//        for (int i = 0; i<10;i++){
//            AttentionWordsEntity entity = new AttentionWordsEntity();
//            entity.setAlias("Alias"+i+"=="+ChannelId);
//            entity.setHotword_id(10001);
//            lists.add(entity);
//        }
//        attentionAdapter.setData(lists);
        mListView.refresh();
        loadUnAttentionList(false, ChannelId);

    }

    private void initRecycleView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);
        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mListView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {

                loadUnAttentionList(true, ChannelId);

            }

            @Override
            public void onLoadMore() {
                mListView.loadMoreComplete();
            }
        });
        attentionAdapter = new SearchAttentionAdapter(getActivity());
        mListView.setAdapter(attentionAdapter);

    }

    private void loadUnAttentionList(boolean isNet, final String channelid) {

        new AttetionNet().loadAllWordsList(getActivity(), channelid, new AttetionNet.LoadWordsCallBack() {
            @Override
            public void callBack(final List<AttentionWordsEntity> listEntities) {
                if (null == getActivity()) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mListView.isRefreshing()) {
                            mListView.refreshComplete();
                        }
                        if (ChannelId.equals(channelid)) {
                            attentionAdapter.setData(listEntities);
                        }
                    }
                });
            }
        }, isNet);

    }
}
