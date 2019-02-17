package com.baiyi.cmall.activities.main.mall.adapter;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadCategory;
import com.baiyi.core.util.ContextUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author tangkun
 * 商城首页的分类字段列表适配器
 */
public class MallHeadAdapter extends BaseGridViewAdpater<MallMainHeadCategory> {

	private Context context;
	private LayoutInflater inflater;

	public MallHeadAdapter(Context context) {
		super();
		this.context = context;
		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public View getBindView(final int position, View convertView,
			ViewGroup parent) {

		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_mall_head, null);
			holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final MallMainHeadCategory entity = (MallMainHeadCategory) getItem(position);
		holder.txtName.setText(entity.getCn());
		holder.txtName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (clickCategory != null) {
					clickCategory.setItemOnclick(position, entity);
				}
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
	}

	private OnItemClickCategory clickCategory;

	public void setItemClick(OnItemClickCategory clickCategory) {
		this.clickCategory = clickCategory;
	}

	public interface OnItemClickCategory {
		public void setItemOnclick(int position, MallMainHeadCategory entity);
	}
}
