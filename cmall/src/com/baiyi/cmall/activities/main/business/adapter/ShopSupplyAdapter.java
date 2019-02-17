/**
 * 
 */
package com.baiyi.cmall.activities.main.business.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter;
import com.baiyi.cmall.model.Blm;
import com.baiyi.cmall.utils.Utils;

/**
 * @author tangkun
 *
 */
public class ShopSupplyAdapter extends BaseRecyclerAdapter<Blm>{

	/**
	 * @param context
	 */
	public ShopSupplyAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_shop_supply,parent,false);
      return new ShopSupplyView(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		Blm entity = objects.get(position);
		((ShopSupplyView) holder).mTvName.setText(entity.getC3()+"("+entity.getC1()+")");
		((ShopSupplyView) holder).mTvCategory.setText(entity.getC2());
		((ShopSupplyView) holder).mTvNum.setText(Utils.getWeight(entity.getC4())+"¶Ö");
		((ShopSupplyView) holder).mTvPrice.setText(Utils.dealPrice(entity.getC5())+"Ôª/¶Ö");
	}
	public static class ShopSupplyView extends RecyclerView.ViewHolder {

		TextView mTvName;// ÏêÏ¸ÄÚÈÝ
		TextView mTvCategory;// ÏêÏ¸ÄÚÈÝ
		TextView mTvNum;// ÏêÏ¸ÄÚÈÝ
		TextView mTvPrice;// ÏêÏ¸ÄÚÈÝ

		public ShopSupplyView(View itemView) {
			super(itemView);
			mTvName = (TextView) itemView.findViewById(R.id.txt_business_shop_supply);
			mTvCategory = (TextView) itemView.findViewById(R.id.txt_business_shop_category);
			mTvNum = (TextView) itemView.findViewById(R.id.txt_business_shop_num);
			mTvPrice = (TextView) itemView.findViewById(R.id.txt_business_shop_minprice);

		}

	}
}
