/**
 * 
 */
package com.baiyi.jj.app.activity.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.net.AppUtils;
import com.baiyi.jj.app.activity.user.net.URLUtils;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.DiscoverTableEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.NetUtils;

/**
 * ����-�Ӿ�����Ӱѡ�����
 * @author tangkun
 *
 */
public class DiscoveryItemPager extends LinearLayout{

	//��Ӱ��
	private LinearLayout linAddTable = null;
	// �����̳�
	private LinearLayout btnMarket = null;
	// �Ӿ�����Ӱ�������
	private PhotoOnItemclick photoOnItemclick = null;
	

	/**
	 * @param context
	 */
	public DiscoveryItemPager(Context context) {
		super(context);
		initContent();
	}
	
	/**
	 * @param context
	 * @param attrs
	 */
	public DiscoveryItemPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initContent();
	}

	/**
	 *  ��ʼ������
	 */
	private void initContent()
	{
		ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.page_discovery_item, this);
		linAddTable = (LinearLayout) findViewById(R.id.lin_addtable);
		addTable();
		btnMarket = (LinearLayout) findViewById(R.id.btn_discovery_market);
		btnMarket.setOnClickListener(new ItemOnclick());
	}
	
	private void addTable() {
		List<DiscoverTableEntity> list = initData();
		linAddTable.removeAllViews();
		for (int i = 0; i < list.size(); i++) {
			final DiscoverTableEntity entity = list.get(i);
			View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.item_discover_table, null);
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.btn_discovery_table);
			TextView tView = (TextView) view.findViewById(R.id.txt_discover_name);
			ImageView iView = (ImageView) view.findViewById(R.id.img_discover_img);
			tView.setText(entity.getTableNameStr());
			iView.setImageResource(entity.getTableRes());
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (photoOnItemclick != null) {
						photoOnItemclick.onItemClick(entity.getTableName());
					}
				}
			});
			linAddTable.addView(view);
		}

	}
	
	
	public void setPhotoOnItemclick(PhotoOnItemclick photoOnItemclick) {
		this.photoOnItemclick = photoOnItemclick;
	}

	private class ItemOnclick implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			if (id == R.id.btn_discovery_market) {
				UserInfoEntity entity = CmsApplication.getUserInfoEntity();
				if (entity == null) {
					AppUtils.loginRemind(
							getContext(),
							getResources().getString(R.string.tip_dialog_title),
							getResources().getString(
									R.string.tip_dialog_unlongin));
					return;
				}
				IntentClassChangeUtils.startMemberWeb(getContext(),
						URLUtils.getMemberMarket(Constant.SYS_ID,entity.getToken(),entity.getMID()), "",true);
				return;
			}
//			if(id == R.id.btn_discovery_shijue)
//			{
//				Intent intent = new Intent(getContext(), PhotoActivity.class);
//				intent.putExtra(Define.ArticleType, ChannelDataUtils.Channel_ShiJue);
//				getContext().startActivity(intent);
//			}else if(id == R.id.btn_discovery_sheying)
//			{
//				Intent intent = new Intent(getContext(), PhotoActivity.class);
//				intent.putExtra(Define.ArticleType, ChannelDataUtils.Channel_Sheying);
//				getContext().startActivity(intent);
//			}
//			if(photoOnItemclick != null)
//			{
//				photoOnItemclick.onItemClick(id);
//			}
		}
		
	}
	
	public interface PhotoOnItemclick
	{
		public void onItemClick(String tablename);
	}
	
	
	private List<DiscoverTableEntity> initData() {
		ArrayList<DiscoverTableEntity> list = new ArrayList<DiscoverTableEntity>();
		DiscoverTableEntity entity = new DiscoverTableEntity();
		entity.setTableName(ArticleHistoryUtils.Tablename_Interest);
		entity.setTableNameStr(getResources().getString(R.string.name_interest));
		entity.setTableRes(R.drawable.entertainment_interest2x);
		DiscoverTableEntity entity2 = new DiscoverTableEntity();
		entity2.setTableName(ArticleHistoryUtils.Tablename_Photo);
		entity2.setTableNameStr(getResources().getString(R.string.name_photo));
		entity2.setTableRes(R.drawable.popleft_icon_entertainment_visual);
		list.add(entity);
		list.add(entity2);
		return list;
	}

}
