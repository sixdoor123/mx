/**
 * 
 */
package com.baiyi.jj.app.listitem.search;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.news.NoImgRender;
import com.baiyi.jj.app.adapter.SearchDetailAdapter;
import com.baiyi.jj.app.adapter.base.GdapterTypeRender;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.listitem.search.SearchNoImgRender.OnDeleteClick;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.RemoteImageView;

/**
 * @author tangkun
 * 
 */
public class SearchRighImgItem implements GdapterTypeRender {

	private TextView txtTitle = null;
	private RemoteImageView imgLeft = null;
	private TextView txtSource = null;
	private TextView txtTime = null;
	private TextView txtCommentNum = null;
	private ImageView imgMore = null;
	//���ɾ����ť
	private ImageView imgDelete = null;
	private boolean isDelete = false;
	
	private LinearLayout line2TimeGruop = null;
	/**
	 * ��ǩ
	 */
	private LinearLayout linAddTag = null;
	
	private View convertView = null;
	private Context context = null;
	private SearchDetailAdapter adapter = null;
	
	private NewsListEntity data = null;

	private LinearLayout linParent = null;
	private OnYDeleteClick yDeleteClick = null;
	
	/**
	 * @param context
	 */
	public SearchRighImgItem(Context context, SearchDetailAdapter searchDetailAdapter,
			boolean isEdit,OnYDeleteClick deleteClick) {
		this.isDelete = isEdit;
		this.context = context;
		this.yDeleteClick = deleteClick;
		this.adapter = searchDetailAdapter;
    	convertView = ContextUtil.getLayoutInflater(context).inflate(R.layout.list_item_collect_oneleft, null);
	}


	public void setBitmap(NewsListEntity data, boolean isLoading) {
		if(imgLeft.getVisibility() == View.VISIBLE)
		{
			imgLeft.loadImg(context,data.toImgList().get(0).getMedia_path());
		}
	}
	

	@Override
	public View getConvertView() {
		// TODO Auto-generated method stub
		return convertView;
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method 
		imgLeft = (RemoteImageView) convertView.findViewById(R.id.img_one_left);
		int imgW = (Config.getInstance().getScreenWidth(context)-ContextUtil.dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset)) / 3;
		int imgH = (int)(imgW * ListItemBaseNews.Defult_Img_Percent);
		imgLeft.getLayoutParams().width = imgW;
		imgLeft.getLayoutParams().height = imgH;
		
		
		txtCommentNum = (TextView) convertView.findViewById(R.id.txt_comment1);
		txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle1);
		txtTime = (TextView) convertView.findViewById(R.id.txt_time1);
		txtSource = (TextView) convertView.findViewById(R.id.txt_source1);

		line2TimeGruop = (LinearLayout)convertView.findViewById(R.id.title_2line);
		line2TimeGruop.setVisibility(View.VISIBLE);
		linAddTag = (LinearLayout) convertView.findViewById(R.id.lin_addlabel1);
		imgMore = (ImageView) convertView.findViewById(R.id.news_more1);
		linAddTag.setVisibility(View.GONE);
		imgMore.setVisibility(View.GONE);
		imgDelete = (ImageView) convertView.findViewById(R.id.img_delete);
		linParent = (LinearLayout) convertView.findViewById(R.id.lin_parent);
	
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.adapter.base.GdapterTypeRender#setDatas(int, boolean)
	 */
	@Override
	public void setDatas(final int position, boolean isLoading) {
		// TODO Auto-generated method stub
		
    	data = (NewsListEntity)adapter.getItem(position);
    	if(data == null)
    	{
    		return;
    	}
		imgDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (yDeleteClick != null) {
					yDeleteClick.OnClick(data, position);
				}
			}
		});
		
		displayOneCenter(data);
		
		setBitmap(data, isLoading);
		
		imgDelete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
		setMargin();
	}
	private void setMargin() {
		// ���Ͼ�s
		linParent.setPadding(
				BaseActivity.getDensity_int(NoImgRender.left_Right),
				FontUtil.getTopMaigrn(),
				BaseActivity.getDensity_int(NoImgRender.left_Right),
				FontUtil.getTopMaigrn());
		// ժҪ
		FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) line2TimeGruop
				.getLayoutParams();
		layoutParams2.setMargins(0,
				BaseActivity.getDensity_int(FontUtil.getLineMaigrn4_5()), 0, 0);
		line2TimeGruop.setLayoutParams(layoutParams2);

	}

	private void displayOneCenter(NewsListEntity data) {
		String timeN = Utils.getCollectTime(data.getArticle_pubDate());
		// ���±���
		txtTitle.setText(Utils.ToDBC(data.getArticle_title().replace(" ", "").replace("\n", "")));
		txtTitle.setTextSize(FontUtil.getSearchTitleTextSize(context));
		// ���·���ʱ��
		txtTime.setText(timeN);
		// ������Դ
		if (Utils.isStringEmpty(data.getArticle_source().replace("\n", ""))) {
			txtSource.setVisibility(View.GONE);
		}
		txtSource.setText(data.getArticle_source());
		// ����������Ŀ
		txtCommentNum.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
	}
	
	public interface OnYDeleteClick{
		public void OnClick(NewsListEntity entity, int position);
	}
}
