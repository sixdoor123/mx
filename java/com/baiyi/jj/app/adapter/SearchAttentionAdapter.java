package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.activity.attention.MyAttentionListAct;
import com.baiyi.jj.app.activity.attention.net.AttetionNet;
import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.cache.AttentionCacheManager;
import com.baiyi.jj.app.cache.Dao.AttetionWordsDao;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.turbo.turbo.mexico.R;

/**
 * 提现历史记录适配器
 * Created by lizhilong on 2016/9/20.
 */

public class SearchAttentionAdapter extends BaseItemAdapter<AttentionWordsEntity> {

    private Context context;
    private int imgFillH = BaseActivity.getDensity_int(40);
    private int imgFillW = (int) (imgFillH/0.68);

    public SearchAttentionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_search_attention;
    }

    @Override
    public BaseViewHolder getBindingHolder(View view) {
        return new AttentionViewHold(view);
    }

    @Override
    public UIDataBase getUIDataItem(AttentionWordsEntity live) {
        return new UIDataAttention(live);
    }

    class UIDataAttention extends UIDataBase<AttentionWordsEntity> {
        public UIDataAttention(AttentionWordsEntity data) {
            super(data);
        }
    }


    class AttentionViewHold extends BaseViewHolder<UIDataAttention> {

        ImageView imgPic;
        TextView tvTitle;
        TextView btnAttention;
        LinearLayout layout;

        public AttentionViewHold(View itemView) {
            super(itemView);
            imgPic = (ImageView) itemView.findViewById(R.id.img_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            btnAttention = (TextView) itemView.findViewById(R.id.btn_attention);
            layout = (LinearLayout) itemView.findViewById(R.id.lin_attention);

            imgPic.getLayoutParams().height = imgFillH;
            imgPic.getLayoutParams().width = imgFillW;

        }

        @Override
        public void bind(@NonNull final UIDataAttention data, int position) {
            tvTitle.setText(data.data.getAlias());
            GlideTool.getListImage(context, data.data.getIcon(), imgPic);
            boolean isHaveCache = AttentionCacheManager.getInstance().isHaveCache(data.data.getChannel_id()+""+data.data.getHotword_id());
            btnAttention.setText( isHaveCache? context.getResources().getString(R.string.txt_attention_alre) : context.getResources().getString(R.string.txt_attention_btn));
            btnAttention.setSelected(isHaveCache);
            btnAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAttention.setClickable(false);
                    TLog.e("abc","loaddel------------0");
                    if (btnAttention.getText().equals(context.getResources().getString(R.string.txt_attention_btn))) {
                        btnAttention.setSelected(true);
                        btnAttention.setText(context.getResources().getString(R.string.txt_attention_alre));
                        loadAddAttention(data.data, btnAttention);
                    } else {
                        btnAttention.setSelected(false);
                        btnAttention.setText(context.getResources().getString(R.string.txt_attention_btn));
                        loadDelAttention(data.data, btnAttention);
                    }
                }
            });
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goWordsList(data.data,btnAttention.getText().toString());
                }
            });
        }
    }


    private void loadAddAttention(final AttentionWordsEntity entity, final TextView button) {
        new AttetionNet().loadAddAttention(String.valueOf(entity.getHotword_id()), new AttetionNet.addCallBack() {
            @Override
            public void callBack(final int state) {
                ((BaseActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setClickable(true);
                        if (state == AttetionNet.State_Success) {
                            AttentionCacheManager.getInstance().addOneCaChe(entity.getChannel_id()+""+entity.getHotword_id(),entity);
//                            new AttetionWordsDao(context).update(entity,true);
                            entity.setAttet(true);
                            new AttetionWordsDao(context).updateOrAdd(entity);
                        } else {
                            ((BaseActivity) context).displayToast(context.getResources().getString(R.string.txt_attention_addf));
                        }

                    }
                });

            }
        });

    }

    private void loadDelAttention(final AttentionWordsEntity entity, final TextView button) {
        new AttetionNet().loaddelAttention(String.valueOf(entity.getHotword_id()), new AttetionNet.delCallBack() {
            @Override
            public void callBack(final int state) {
                ((BaseActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setClickable(true);
                        if (state == AttetionNet.State_Success) {
                            AttentionCacheManager.getInstance().deleteOneCache(entity.getChannel_id()+""+entity.getHotword_id());
                            entity.setAttet(false);
                            new AttetionWordsDao(context).updateOrAdd(entity);
                        } else {
                            ((BaseActivity) context).displayToast(context.getResources().getString(R.string.txt_attention_canf));
                        }

                    }
                });

            }
        });
    }

    private void goWordsList(AttentionWordsEntity entity,String isAttention) {
        Intent intent = new Intent(context, MyAttentionListAct.class);
//        intent.putExtra(Define.ChannelId, entity.getChannel_id());
//        intent.putExtra(Define.ChannelName, entity.getAlias());
//        intent.putExtra(Define.HotWordId, String.valueOf(entity.getHotword_id()));
//        intent.putExtra(Define.HotWord, entity.getWords());
        intent.putExtra(Define.HotWord,entity);
        if (isAttention.equals(context.getResources().getString(R.string.txt_attention_btn))){
            intent.putExtra(Define.IsAttention, false);
        }else{
            intent.putExtra(Define.IsAttention, true);
        }
        ((BaseActivity)context).startActivityForResult(intent,0);
    }

}
