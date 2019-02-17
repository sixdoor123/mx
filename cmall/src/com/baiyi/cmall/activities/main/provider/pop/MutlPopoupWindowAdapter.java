package com.baiyi.cmall.activities.main.provider.pop;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 下拉列表的适配器（供应、采购）
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 上午10:30:39
 */
public class MutlPopoupWindowAdapter extends BaseAdapter {

	// 上下文
	private Context context;
	// 数据
	private ArrayList<SelectedInfo> datas = new ArrayList<SelectedInfo>();
	private SelectedInfo parentInfo;
	// 状态 0表示一级菜单调用
	// 1 表示二级菜单调用
	private int state;

	// 布局解析器
	private LayoutInflater inflater;

	private String selectId;
	// 搜索关键字
	private String searchInfo = "";

	public MutlPopoupWindowAdapter(Context context) {
		this.context = context;

		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setSelectId(String infoId) {
		this.selectId = infoId;
		this.notifyDataSetChanged();
	}

	public void setSelectId(String infoId, boolean isNotify) {
		this.selectId = infoId;
		if (isNotify) {
			this.notifyDataSetChanged();
		}
	}

	public String getSelectId() {
		return selectId;
	}

	SelectedInfo info;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_mutl_pop, null);

			holder.mImgSelected = (ImageView) convertView.findViewById(R.id.img_selected);
			holder.mTxtItemSel = (TextView) convertView.findViewById(R.id.txt_item_sel);
			// holder.mImgRight = (ImageView) convertView
			// .findViewById(R.id.img_right_arrow);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		info = datas.get(position);
		if (!Utils.isStringEmpty(selectId)) {
			if ("-1".equals(selectId) && "-1".equals(info.getCm_categorycode())) {
				setSelectItem("-1".equals(selectId), convertView, holder);
			} else if (!Utils.isStringEmpty(info.getCm_categorycode())) {
				setSelectItem(info.getCm_categorycode().equals(selectId), convertView, holder);
			}
		} else {
			if (!TextUtils.isEmpty(searchInfo)) {
				setSelectItem(info.getCm_categorycode().equals(searchInfo), convertView, holder);
			}
		}
		holder.mTxtItemSel.setText(info.getCm_categoryname());

		return convertView;
	}

	public void setSelectItem(boolean isSelect, View convertView, ViewHolder holder) {
		if (isSelect) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.bg_hui3));
			holder.mTxtItemSel.setTextColor(context.getResources().getColor(R.color.bg_buyer));
			holder.mImgSelected.setImageResource(R.drawable.selected_red);
			// holder.mImgRight.setVisibility(View.VISIBLE);
			holder.mImgSelected.setVisibility(View.VISIBLE);
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.bg_white));
			holder.mTxtItemSel.setTextColor(context.getResources().getColor(R.color.bg_hui1));
			holder.mImgSelected.setImageResource(R.drawable.no_selected);
			// holder.mImgRight.setVisibility(View.VISIBLE);
			holder.mImgSelected.setVisibility(View.GONE);
		}
	}

	static class ViewHolder {
		TextView mTxtItemSel;
		ImageView mImgSelected;
		// ImageView mImgRight;
	}

	private OnSelectedSeconedItemListener itemListener;

	public void setItemListener(OnSelectedSeconedItemListener itemListener) {
		this.itemListener = itemListener;
	}

	/**
	 * 当选择二级条目时，改变字体颜色和状态
	 */
	public interface OnSelectedSeconedItemListener {
		void onSelectedSeconedItem(ImageView mImgSelected, TextView mTextSel);
	}

	public void setLeftData(ArrayList<SelectedInfo> data) {
		if (Utils.isStringEmpty(data)) {
			return;
		}
		this.datas.addAll(data);
		this.notifyDataSetChanged();
	}

	public void setRightData(SelectedInfo state) {
		parentInfo = state;
		if (parentInfo == null) {
			return;
		}

		this.datas = state.getSonDatas();
		if (Utils.isStringEmpty(datas)) {
			return;
		}
		notifyDataSetChanged();
	}

	public void setParentInfo(SelectedInfo parentInfo) {
		this.parentInfo = parentInfo;
	}

	public SelectedInfo getParentInfo() {
		return parentInfo;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

}
