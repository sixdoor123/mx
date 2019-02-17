package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.entity.CreditsEntity;
import com.baiyi.jj.app.entity.CreditsEntity;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class CreditsHisAdatper extends BaseItemAdapter<CreditsEntity> {

    private Context context;
    public CreditsHisAdatper(Context context) {
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_credits;
    }

    @Override
    public BaseViewHolder getBindingHolder(View view) {
        return new CreditsViewHold(view);
    }

    @Override
    public UIDataBase getUIDataItem(CreditsEntity live) {
        return new UIDataCredits(live);
    }

    class UIDataCredits extends UIDataBase<CreditsEntity>{
        public UIDataCredits(CreditsEntity data) {
            super(data);
        }
    }

    class CreditsViewHold extends BaseViewHolder<UIDataCredits>{

        TextView tvDate;
        TextView tvAmounts;
        TextView tvAction;

        public CreditsViewHold(View itemView) {
            super(itemView);
            tvDate = (TextView)itemView.findViewById(R.id.tv_credits_date);
            tvAmounts = (TextView)itemView.findViewById(R.id.tv_credits_amount);
            tvAction = (TextView)itemView.findViewById(R.id.tv_credits_time);
        }

        @Override
        public void bind(@NonNull UIDataCredits data, int position) {

            tvDate.setText(Utils.getTimeYMD(Utils.getTimeSecond(data.data.getCreated_at(),true)));
            tvAmounts.setText(String.valueOf(data.data.getIntegral_num()));
            tvAction.setText(data.data.getIntegral_action());
        }
    }

}
