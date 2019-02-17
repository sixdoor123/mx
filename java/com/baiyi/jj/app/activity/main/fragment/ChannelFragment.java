//package com.baiyi.jj.app.activity.main.fragment;
//
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.baiyi.jj.app.activity.user.channel.ChannelDataUtils;
//import com.baiyi.jj.app.adapter.MyRecyclerGridAdapter;
//import com.baiyi.jj.app.entity.ChannelItem;
//import com.baiyi.jj.app.entity.UserInfoEntity;
//import com.baiyi.jj.app.utils.ArticleHistoryUtils;
//import com.baiyi.jj.app.utils.Utils;
//import com.baiyi.jj.app.views.MyLoadingBar;
//import com.turbo.turbo.mexico.R;
//
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by Administrator on 2017/1/11 0011.
// */
//public class ChannelFragment extends Fragment{
//
//    @Bind(R.id.page_channel_manager)
//    RelativeLayout pageChannelManagerGroup = null;
//    @Bind(R.id.channer_recycler)
//    RecyclerView recyclerView = null;
//    MyRecyclerGridAdapter recyclerGridAdapter = null;
//
//    @Bind(R.id.lin_shownochannel)
//    LinearLayout linNoChannel;
//    @Bind(R.id.btn_addchannel)
//    Button btnAddChannel;
//    @Bind(R.id.btn_exit)
//     Button btnExit = null;
//    @Bind(R.id.channel_manager_loading)
//     MyLoadingBar progressBar = null;
//    @Bind(R.id.btn_center_arrow)
//     ImageView imgArrow;
//    private String tableName;
//    protected boolean isRecomment = true;
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_news,container,false);
//        ButterKnife.bind(this,view);
//
//
//        return view;
//
//    }
//
//    protected void loadChannelData(final boolean isDisArtile, final UserChannelCallBack callBack) {
//        linNoChannel.setVisibility(View.GONE);
//        if(isVisibleManager())
//        {
//            startLoad();
//        }
//        ((BaseActivity) getActivity()).loadAnonymity(new BaseActivity.AnonymityLister() {
//
//            @Override
//            public void setAnonymityLister(final UserInfoEntity entity) {
//                if (entity == null){
//                    return;
//                }
//                ChannelDataUtils.getInstance().loadChannelList(getActivity(), new ChannelDataUtils.ChannelResultCallBack() {
//
//                            @Override
//                            public void onResultCallBack(
//                                    List<ChannelItem> userChannelList,
//                                    List<ChannelItem> otherChannelList) {
//
//                                stopLoad();
//
//                                if (Utils.isStringEmpty(userChannelList)){
//                                    linNoChannel.setVisibility(View.VISIBLE);
//                                    recyclerGridAdapter.clear();
//                                    btnExit.setVisibility(View.GONE);
//                                }else{
//                                    btnExit.setVisibility(View.VISIBLE);
//                                }
//                                if (callBack != null){
//                                    callBack.callback(userChannelList);
//                                }
//
////                                if(isDisArtile)
////                                {
////                                    loadArticleData(entity.getMID(), userChannelList);
////                                }
////                                if (recyclerGridAdapter.isDataChange(userChannelList)) {
////                                    recyclerGridAdapter.clear();
////                                    disChannelManger(userChannelList);
////                                }
//                            }
//                        },
//                        ChannelDataUtils.getChannelType(ArticleHistoryUtils.Tablename_News),
//                        entity.getMID(),false);
//            }
//        });
//    }
//
//    interface UserChannelCallBack{
//        public void callback(List<ChannelItem> list);
//    }
//
//    public boolean isVisibleManager() {
//        if (pageChannelManagerGroup == null) {
//            return false;
//        }
//        return (pageChannelManagerGroup.getVisibility() == View.VISIBLE);
//    }
//
//    private void startLoad() {
//        progressBar.start();
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
//
//    }
//    private void stopLoad() {
//        progressBar.stop();
//        progressBar.setVisibility(View.GONE);
//
//    }
//}
