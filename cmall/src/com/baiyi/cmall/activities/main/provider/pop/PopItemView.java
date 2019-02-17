package com.baiyi.cmall.activities.main.provider.pop;

import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 弹出框的的View、
 * 
 * @author sunxy
 */
public class PopItemView extends ScrollView {

	// 线性布局
	private LinearLayout mLlRoot = null;
	private Context context = null;

	private SelectedInfo info = null;

	public PopItemView(Context context) {
		this(context, null);
	}

	public PopItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		initView();
	}

	private TextView mTxtItemSel;
	private ImageView mImgSelected;

	private void initView() {
		// 设置ScrollView
		this.setLayoutParams(getParams());
		// 影藏滚动条
		this.setVerticalScrollBarEnabled(false);

		// 初始化线性布局
		this.mLlRoot = new LinearLayout(context);
		this.mLlRoot.setLayoutParams(getParams());
		this.mLlRoot.setOrientation(LinearLayout.VERTICAL);

		this.addView(mLlRoot);

	}

	private String selectId = null;
	private String searchInfo = null;

	/**
	 * 显示数据
	 */
	public void display(final SelectedInfo info) {
		this.info = info;

		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.pop_item, null);

		mImgSelected = (ImageView) view.findViewById(R.id.img_selected);
		mTxtItemSel = (TextView) view.findViewById(R.id.txt_item_sel);

		if (!Utils.isStringEmpty(selectId)) {
			if ("-1".equals(selectId) && "-1".equals(info.getCm_categorycode())) {
				setSelectItem("-1".equals(selectId), view);
			} else if (!Utils.isStringEmpty(info.getCm_categorycode())) {
				setSelectItem(info.getCm_categorycode().equals(selectId), view);
			}
		} else {
			if (!TextUtils.isEmpty(searchInfo)) {
				setSelectItem(info.getCm_categorycode().equals(searchInfo), view);
			}
		}
		mTxtItemSel.setText(info.getCm_categoryname());

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != itemListener) {
					itemListener.onItemOclick(info);
				}
			}
		});

		this.mLlRoot.addView(view);

	}

	/**
	 * 条目显示
	 * 
	 * @param isSelect
	 * @param view
	 */
	public void setSelectItem(boolean isSelect, View view) {
		if (isSelect) {
			view.setBackgroundColor(context.getResources().getColor(R.color.bg_hui3));
			mTxtItemSel.setTextColor(context.getResources().getColor(R.color.bg_buyer));
			mImgSelected.setImageResource(R.drawable.selected_red);
			mImgSelected.setVisibility(View.VISIBLE);
		} else {
			view.setBackgroundColor(context.getResources().getColor(R.color.bg_white));
			mTxtItemSel.setTextColor(context.getResources().getColor(R.color.bg_hui1));
			mImgSelected.setImageResource(R.drawable.no_selected);
			mImgSelected.setVisibility(View.GONE);
		}
	}

	/**
	 * 布局参数
	 */
	private LayoutParams getParams() {
		LayoutParams params = //
				new LayoutParams(LayoutParams.MATCH_PARENT, //
						LayoutParams.MATCH_PARENT);
		return params;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

	private onPopItemViewClickListener itemListener;

	public void setItemListener(onPopItemViewClickListener itemListener) {
		this.itemListener = itemListener;
	}

	public interface onPopItemViewClickListener {
		void onItemOclick(SelectedInfo info);
	}
}
