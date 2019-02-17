/**
 * 
 */
package com.baiyi.cmall.views.pop;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.adapter.SecondPopoupWindowAdapter;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.R;

/**
 * Pop 双ListView
 * 
 * @author tangkun
 * 
 */
public class PopDoubleList extends BasePopupWindow {

	private ListView firstListView;
	private ListView secondListView;
	private SecondPopoupWindowAdapter firstAdapter;
	public SecondPopoupWindowAdapter secondAdapter;

	private PopListItemOnclick popListItemOnclick = null;

	private Context context = null;

	private ArrayList<SelectedInfo> firstCitySelects = null;

	// 搜索关键字
	private String searchInfo;

	/**
	 * 85428432
	 * 
	 * @param contentView
	 * @param context
	 * @param height
	 * @param searchInfo
	 */
	public PopDoubleList(View contentView, Context context, int width, int height,
			ArrayList<SelectedInfo> firstCitySelects, String searchInfo) {
		super(contentView, context, width, height);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.firstCitySelects = firstCitySelects;
		this.searchInfo = searchInfo;
		if (Utils.isStringEmpty(this.firstCitySelects)) {
			((BaseActivity) context).displayToast("数据null");
			dismiss();
			return;
		}

		intitView(contentView);
	}
	SelectedInfo firstInfo  ;
	private void intitView(View contentView) {
		firstListView = (ListView) contentView.findViewById(R.id.first);
		firstListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		secondListView = (ListView) contentView.findViewById(R.id.second);
		// secondListView.setVisibility(View.GONE);

		firstAdapter = new SecondPopoupWindowAdapter(context);
		secondAdapter = new SecondPopoupWindowAdapter(context);

		firstAdapter.setSearchInfo(searchInfo);

		firstListView.setAdapter(firstAdapter);
		secondListView.setAdapter(secondAdapter);
		firstAdapter.setLeftData(firstCitySelects);

		firstListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 firstInfo = (SelectedInfo) parent
						.getItemAtPosition(position);
				secondListView.setVisibility(View.VISIBLE);
				if ("全部".equals(firstInfo.getCm_categoryname())) {
					if (popListItemOnclick != null) {
						popListItemOnclick.setPopListItemOnclick(position,
								firstInfo, null, true);

						secondAdapter.setRightData(null);
						secondListView.setVisibility(View.GONE);
						dismiss();
					}
				} else {
					secondAdapter.setSelectId(" ");
					secondListView.setVisibility(View.VISIBLE);
					secondAdapter.setRightData(firstInfo);
				}
				firstAdapter.setSelectId(firstInfo.getCm_categorycode());
			}
		});

		secondListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SelectedInfo secondInfo = (SelectedInfo) parent
						.getItemAtPosition(position);
				if (popListItemOnclick != null) {
					popListItemOnclick.setPopListItemOnclick(position,
							secondAdapter.getParentInfo(), secondInfo, false);
				}
				secondAdapter.setSelectId(secondInfo.getCm_categorycode());
				dismiss();
			}
		});
	}

	public void setPopListItemOnclick(PopListItemOnclick popListItemOnclick) {
		this.popListItemOnclick = popListItemOnclick;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

}
