package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.user.entity.LanguageEntities;
import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.entity.WithdrawEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现历史记录适配器
 * Created by lizhilong on 2016/9/20.
 */

public class WithdrawHisAdapter extends BaseItemAdapter<WithdrawEntity> {

    private Context context;
    public WithdrawHisAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_withdraw;
    }

    @Override
    public BaseViewHolder getBindingHolder(View view) {
        return new WithdrawViewHold(view);
    }

    @Override
    public UIDataBase getUIDataItem(WithdrawEntity live) {
        return new UIDataWithdraw(live);
    }

    class UIDataWithdraw extends UIDataBase<WithdrawEntity>{
        public UIDataWithdraw(WithdrawEntity data) {
            super(data);
        }
    }

    class WithdrawViewHold extends BaseViewHolder<UIDataWithdraw>{

        TextView tvData;
        TextView tvMoney;
        TextView tvState;

        public WithdrawViewHold(View itemView) {
            super(itemView);
            tvData = (TextView)itemView.findViewById(R.id.tv_withdraw_data);
            tvMoney = (TextView)itemView.findViewById(R.id.tv_withdraw_money);
            tvState = (TextView)itemView.findViewById(R.id.tv_withdraw_state);
        }

        @Override
        public void bind(@NonNull UIDataWithdraw data, int position) {

            tvData.setText(Utils.getTimeYMD(Utils.getTimeSecond(data.data.getApply_date(),true)));
            tvMoney.setText("$"+Utils.getPoint2Float(data.data.getAmount()));
            tvState.setText(getWithdrawStatus(data.data.getAudit()));
            if (data.data.getAudit()==1){
                tvState.setTextColor(context.getResources().getColor(R.color.day_text_orange));
            }
        }
    }


    private String getWithdrawStatus(int status){
        String statusStr = "";
        if (status == 1){
            statusStr = context.getResources().getString(R.string.txt_tixian_pending);
        }else if (status == 2){
            statusStr = context.getResources().getString(R.string.txt_tixian_processed);
        }else if (status == 3){
            statusStr = context.getResources().getString(R.string.txt_tixian_colose);
        }else {
            statusStr = context.getResources().getString(R.string.txt_tixian_pending);
        }
        return statusStr;
    }

}
