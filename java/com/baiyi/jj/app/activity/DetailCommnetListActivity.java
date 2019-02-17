package com.baiyi.jj.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.user.net.AppUtils;
import com.baiyi.jj.app.adapter.CommentAdapter;
import com.baiyi.jj.app.adapter.CommentAdapter.ItemAgreeOnclick;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.NewsDetailCommentEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.net.NewsDetailUtils;
import com.baiyi.jj.app.net.NewsDetailUtils.CommentListCallBack;
import com.baiyi.jj.app.net.NewsDetailUtils.CommentVoteCallBack;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailCommnetListActivity extends BaseActivity {

    private MyLoadingBar progressBar = null;

    private XRecyclerView listView = null;
    private CommentAdapter adapter = null;

    private EditText editContent = null;
    private Button btnSend = null;

    private int comPageNum = 1;
    private String ArticleId;
    private String ArticleType;

    private NewsDetailUtils newsDetailUtils = null;

    boolean isResult = false;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        // TODO Auto-generated method stub
        super.initWin(false, true);
        View titleView = LayoutInflater.from(this).inflate(R.layout.title_left,
                null);
        win_title.addView(titleView);

        win_content.addView(ContextUtil.getLayoutInflater(this).inflate(
                R.layout.activity_all_comment_list, null));

        ArticleId = getIntent().getStringExtra(Define.ArticleId);
        ArticleType = getIntent().getStringExtra(Define.ArticleType);

        initTitleView();
        initListView();
        initMenu();
        newsDetailUtils = new NewsDetailUtils();
        startLoading();
        loadData();
    }


    /**
     * ��ʼ��������View
     */
    private void initTitleView() {
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                exit();
            }
        });
        TextView txtTitle = (TextView) findViewById(R.id.title_name);
        txtTitle.setText(getResources().getString(R.string.name_title_comment));
//		txtTitle.setTypeface(CmsApplication.getTitleFace(this));
    }

    private void initMenu() {

        btnSend = (Button) findViewById(R.id.btn_send);
        editContent = (EditText) findViewById(R.id.edit_comment);
        btnSend.setOnClickListener(new MenuOnclick());


    }

    private void initListView() {
        progressBar = (MyLoadingBar) findViewById(R.id.loading);
        listView = (XRecyclerView) findViewById(R.id.news_feeds);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);

        listView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);

        listView.setLoadingListener(new LoadingListener() {

            @Override
            public void onRefresh() {
                comPageNum = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                comPageNum++;
                loadData();
            }
        });

        adapter = new CommentAdapter(this);
        listView.setAdapter(adapter);

        adapter.setAgreeOnclick(new ItemAgreeOnclick() {

            @Override
            public void Onclick(NewsDetailCommentEntity entity, int postion) {
                loadCommentVote(postion, entity, 1);
            }
        });
    }

    private void loadData() {

        newsDetailUtils.loadCommentData(this, ArticleId, comPageNum,
                new CommentListCallBack() {

                    @Override
                    public void ComListBack(List<NewsDetailCommentEntity> list) {
                        stopLoading();

                        btnSend.setClickable(true);

                        if (null == list) {
                            list = new ArrayList<>();
                        }
//                        Collections.reverse(list);

                        if (comPageNum == 1) {
                            adapter.setData(list);
                            listView.refreshComplete();
                        } else {
                            listView.loadMoreComplete();
                            adapter.addData(list);
                        }

                    }
                });
    }

    private class MenuOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_send) {
                UserInfoEntity user = CmsApplication.getUserInfoEntity();
                if (user == null) {
                    AppUtils.loginRemind(
                            DetailCommnetListActivity.this,
                            getResources().getString(R.string.tip_dialog_title),
                            getResources().getString(
                                    R.string.tip_dialog_unlongin));
                    return;
                }
                isResult = true;
                hideInput();
                loadSendComment();
            }

        }

    }

    /**
     * 强制隐藏软键盘
     */
    private void hideInput() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(editContent.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void startLoading() {
        if (null != progressBar) {
            progressBar.start();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void stopLoading() {
        if (null != progressBar) {
            progressBar.stop();
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * ��������
     */
    private void loadSendComment() {

        btnSend.setClickable(false);

        final String content = editContent.getText().toString();
        if (Utils.isStringEmpty(content)) {
            btnSend.setClickable(true);
            displayToast(getResources().getString(R.string.tip_comm_nocontent));
            return;
        }
        if (Utils.isStringEmpty(content.trim())) {
            btnSend.setClickable(true);
            displayToast(getResources().getString(R.string.tip_comm_nocontent));
            return;
        }
        if (content.trim().length() > 2000) {
            btnSend.setClickable(true);
            displayToast(getResources().getString(R.string.tip_comm_too_long));
            return;
        }
        startLoading();
        new NewsDetailUtils().loadSendComment(ArticleType,
                DetailCommnetListActivity.this, ArticleId, content,
                new NewsDetailUtils.SendCommentCallBack() {

                    @Override
                    public void sendCallBack(int state) {
                        btnSend.setClickable(true);
                        stopLoading();
                        if (state == NewsDetailUtils.State_Success) {
                            editContent.setText("");
                            final NewsDetailCommentEntity entity = new NewsDetailCommentEntity();
                            entity.setComAgree(0);
                            entity.setComId("-1000");
                            entity.setComContent(content);
                            entity.setComNum(0);
                            entity.setComOpposition(0);
                            entity.setComTime(Utils.getTimeYMDHMS(System.currentTimeMillis()));
                            entity.setNotUtc(true);
                            UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
                            if (infoEntity != null) {
                                entity.setComUserHead(infoEntity.getAvatar());
                                entity.setComUserId(infoEntity.getMID());
                                entity.setComUserName(infoEntity.getDisplay());
                            }
                            comPageNum = 1;
                            listView.smoothScrollToPosition(0);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
//                                    loadData();
                                    adapter.insert(entity);
                                }
                            }, 500);
                        }
                    }
                });

    }


    private void exit() {
        if (isResult) {
            Intent intent = new Intent(getApplicationContext(),
                    BaseNewsDetailAct.class);
            setResult(Constant.Result_Comment, intent);
        }
        setExitSwichLayout();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void loadCommentVote(final int position,
                                 final NewsDetailCommentEntity entity, final int isAgree) {

        checkLogin(new AnonymityLister() {

            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
                    return;
                }
                if (infoEntity.getMID().equals(entity.getComUserId())) {
//                    displayToast(getResources().getString(
//                            R.string.tip_comm_vote_myself));
                    return;
                }
                if (Utils.isStringEmpty(entity.getComId())) {
                    return;
                }
                startLoading();

                newsDetailUtils.loadCommentVote(
                        DetailCommnetListActivity.this, entity.getComId(), isAgree,
                        new CommentVoteCallBack() {

                            public void voteCallBack(int state) {
                                stopLoading();
                                if (state == NewsDetailUtils.State_Success) {
                                    adapter.updateItem(position);
                                    if (isAgree == 1) {
                                        entity.setComAgree(entity.getComAgree() + 1);
                                    }
                                    return;
                                } else if (state == NewsDetailUtils.State_Repeat) {
                                    // adapter.setItemSelect(position, entity);
                                }
                            }
                        });
            }
        });

    }

}
