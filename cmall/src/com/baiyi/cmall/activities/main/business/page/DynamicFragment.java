/**
 * 
 */
package com.baiyi.cmall.activities.main.business.page;

import java.util.ArrayList;

import me.relex.seamlessviewpagerheader.delegate.AbsListViewDelegate;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.business.adapter.ShopDynamicAdapter;
import com.baiyi.cmall.activities.main.business.entity.BusinessDynamicEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * µêÆÌ-¶¯Ì¬
 * @author tangkun
 *
 */
public class DynamicFragment extends BaseViewPagerFragment{

	private XRecyclerView mListView;
    private ShopDynamicAdapter adapter = null;
    private AbsListViewDelegate mAbsListViewDelegate = new AbsListViewDelegate();
	
    public static DynamicFragment newInstance(int index) {
    	DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
		ArrayList<BusinessDynamicEntity> data = new ArrayList<BusinessDynamicEntity>();
		for(int i = 0; i < 10; i++)
		{
			BusinessDynamicEntity entity = new BusinessDynamicEntity();
			entity.setPrice("Y10.00");
			data.add(entity);
		}
		adapter = new ShopDynamicAdapter(getActivity(), data);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        mListView = (XRecyclerView) view.findViewById(R.id.pro_listview);
        mListView.setPullRefreshEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mListView.setLayoutManager(layoutManager);
        initHeadView(inflater, container);
        initFootView(inflater, container);
		mListView.setAdapter(adapter);
        return view;
    }
    
    private void initHeadView(LayoutInflater inflater, ViewGroup container)
    {
        View view = inflater.inflate(R.layout.head_business_dynamic, container, false);
        mListView.addHeaderView(view);
        
    }
    
    private void initFootView(LayoutInflater inflater, ViewGroup container)
    {
        View view = inflater.inflate(R.layout.foot_business_dynamic, container, false);
        mListView.addFootView(view);
    }

    @Override public boolean isViewBeingDragged(MotionEvent event) {
        return mAbsListViewDelegate.isViewBeingDragged(event, mListView);
    }

}
