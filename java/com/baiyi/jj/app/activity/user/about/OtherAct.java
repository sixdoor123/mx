package com.baiyi.jj.app.activity.user.about;

import com.baiyi.jj.app.activity.BaseActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.turbo.turbo.mexico.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Administrator on 2017/4/26 0026.
 */
public class OtherAct extends BaseActivity{
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_back)
    ImageView imgBack;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.act_set_about, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        imgBack.setVisibility(View.VISIBLE);
        titleName.setText(getResources().getString(R.string.txt_other));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
//        List<NewsListEntity> listEntities = new NewsListDao(this).queryForAll();
//        for (int i = 0;i<listEntities.size();i++){
//            TLog.e("list",listEntities.get(i).getChannel_id()+"======="+listEntities.get(i).getId()+"=========="+listEntities.get(i).getReservezone1());
//        }
    }

    @OnClick({R.id.img_back, R.id.btn_center_support, R.id.btn_center_terms, R.id.btn_center_about})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_center_support:
                Intent intent1 = new Intent(this, MemberWebViewActivity.class);
                intent1.putExtra(Define.MemberNAME, "support");
                startActivity(intent1);
                break;
            case R.id.btn_center_terms:
                Intent intent2 = new Intent(this, MemberWebViewActivity.class);
                intent2.putExtra(Define.MemberNAME, "terms");
                startActivity(intent2);
                break;
            case R.id.btn_center_about:
                Intent intent3 = new Intent(this, MemberWebViewActivity.class);
                intent3.putExtra(Define.MemberNAME, "about");
                startActivity(intent3);
        }
    }

}
