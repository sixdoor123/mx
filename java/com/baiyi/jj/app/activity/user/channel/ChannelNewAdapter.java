package com.baiyi.jj.app.activity.user.channel;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.utils.ChannelIconManager;
import com.baiyi.jj.app.utils.TLog;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/14 0014.
 */
public class ChannelNewAdapter extends BaseItemAdapter<ChannelItem> {

    private int imgFillH = BaseActivity.getDensity_int(40);
    private int imgFillW = (int) (imgFillH/0.68);

    private Context context;
    private boolean isChecked;

    private TopCheckClick topCheckClick;
    private BottomCheckClick bottomCheckClick;

    public ChannelNewAdapter(Context context,boolean isChecked) {
        this.context = context;
        this.isChecked = isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        this.notifyDataSetChanged();
    }

    public void removeItem(int postion){
        if (postion>=datas.size()){
            return;
        }
        datas.remove(postion);
//        this.notifyItemRemoved(postion);
        this.notifyDataSetChanged();
    }

    public void addItem(ChannelItem item){
        datas.add(getUIDataItem(item));
//        this.notifyItemInserted(datas.size()-1);
        this.notifyDataSetChanged();
    }
    public void addItem(ChannelItem item,int postion){
        datas.add(postion,getUIDataItem(item));
//        this.notifyItemInserted(datas.size()-1);
        this.notifyDataSetChanged();
    }

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

    @Override
    public void setData(List<ChannelItem> data) {
        super.setData(data);
    }


    class LanguageHolder extends BaseViewHolder<UIDataLanguage> {
        TextView name;
        ImageView imgHead;
        CheckBox checkBox;
        ImageView imgCheck;
        ImageView imgICON;
        View linParent;


        View itemView;

        public LanguageHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            imgHead = (ImageView) itemView.findViewById(R.id.img_head);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_select);
            imgCheck = (ImageView) itemView.findViewById(R.id.img_select);
            imgICON = (ImageView) itemView.findViewById(R.id.img_channel_icon);
            linParent = itemView.findViewById(R.id.btn_item);
            this.itemView = itemView;
//            imgHead.getLayoutParams().height = imgFillH;
//            imgHead.getLayoutParams().width = imgFillW;


        }

        @Override
        public void bind(@NonNull final UIDataLanguage data, final int position) {
            checkBox.setVisibility(View.GONE);
            if (isChecked){
                imgCheck.setVisibility(View.GONE);
            }else {
                imgCheck.setVisibility(View.VISIBLE);
            }
            if ( data.data.getIs_default().equals("true")) {
                imgCheck.setSelected(true);
//                imgICON.setImageResource(ChannelIconManager.getInstance(context).getIconOn(Integer.parseInt(data.data.getCid())));
            }else {
//                imgICON.setImageResource(ChannelIconManager.getInstance(context).getIconOn(Integer.parseInt(data.data.getCid())));
            }
            name.setText(data.data.getChannel_name());
            imgCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imgCheck.isSelected() && topCheckClick != null){
                        topCheckClick.onClick(data.data,itemView,position);
                    }
                    if (bottomCheckClick != null && !imgCheck.isSelected()){
                        bottomCheckClick.onClick(data.data,itemView,position);
                    }
                }
            });
            imgICON.setImageResource(ChannelIconManager.getInstance(context).getIconUn(Integer.parseInt(data.data.getCid())));
//            GlideTool.getListImage(context, data.data.getConvert_img(), imgHead);

//            GlideTool.getSignatureImage(context, data.data.getConvert_img(), imgHead);
        }
    }

    public interface BottomCheckClick{
        void onClick(ChannelItem item,View contentView,int postion);
    }


    public interface TopCheckClick{
        void onClick(ChannelItem item,View contentView,int postion);
    }

    public void setTopCheckClick(TopCheckClick topCheckClick) {
        this.topCheckClick = topCheckClick;
    }

    public void setBottomCheckClick(BottomCheckClick bottomCheckClick) {
        this.bottomCheckClick = bottomCheckClick;
    }
}