package com.baiyi.cmall.activities.main.home_pager.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.home_pager.entity.MainEntity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.cmall.utils.Utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Õ∆ºˆ…Ãº“  ≈‰∆˜
 * 
 * @author lizl
 * 
 */
public class HotBusinessAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<MainEntity> hotBrandS;
	private LayoutInflater inflater;

	public HotBusinessAdapter(Context context) {

		this.context = context;
		this.hotBrandS = new ArrayList<MainEntity>();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return hotBrandS.size();
	}

	@Override
	public Object getItem(int position) {

		return hotBrandS.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHold holder = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.item_hot_brand, null);
			holder = new ViewHold();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.img_hot);
			holder.textView = (TextView) convertView
					.findViewById(R.id.tv_company_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}

		MainEntity entity = hotBrandS.get(position);
		ImageTools.getNormalImage(context, holder.imageView, entity.getPicPath());
		holder.textView.setText(entity.getTitle());

		return convertView;
	}

	public static class ViewHold {

		ImageView imageView;
		TextView textView;

	}

	public void setData(ArrayList<MainEntity> hotBrandS) {
		this.hotBrandS = hotBrandS;
		if (Utils.isStringEmpty(this.hotBrandS)) {
			this.hotBrandS = new ArrayList<MainEntity>();
		}
		this.notifyDataSetChanged();
	}

}
