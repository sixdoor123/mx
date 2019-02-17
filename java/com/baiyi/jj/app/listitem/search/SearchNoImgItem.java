package com.baiyi.jj.app.listitem.search;

/**
 * 
 */
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.news.NoImgRender;
import com.baiyi.jj.app.adapter.SearchDetailAdapter;
import com.baiyi.jj.app.adapter.base.GdapterTypeRender;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.Utils;

/**
 * @author tangkun
 * 
 */
public class SearchNoImgItem implements GdapterTypeRender {
	// ��ͼƬժҪ
	private TextView txtTitle = null;
	private TextView txtSource = null;
	private ImageView imgMore = null;
	private TextView txtTime = null;
	protected TextView txtAbstract = null;
	private TextView txtCommentNum = null;
	private LinearLayout linAddTag = null;
	// ���ɾ����ť
	private ImageView imgDelete = null;
	private boolean isDelete = false;

	private Context context;
	private View convertView;
	private SearchDetailAdapter adapter = null;
	private NewsListEntity data = null;

	/**
	 * �ж϶���
	 */
	private LinearLayout line3TimeGruop = null;

	private OnDeleteClick deleteClick = null;

	/**
	 * ����ƫ�ƣ����Ͼ༯�¾�
	 */
	private LinearLayout linParent = null;
	private RelativeLayout linTagLine = null;

	public SearchNoImgItem(Context context,
			SearchDetailAdapter searchDetailAdapter, boolean isEdit,
			OnDeleteClick deleteClick) {
		this.isDelete = isEdit;
		this.context = context;
		this.adapter = searchDetailAdapter;
		this.deleteClick = deleteClick;
		convertView = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.list_item_collect_noimg, null);
	}

	@Override
	public View getConvertView() {
		return convertView;
	}

	@Override
	public void setEvents() {
		// TODO Auto-generated method stub
		txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle);
		txtSource = (TextView) convertView.findViewById(R.id.txt_source_3line);
		imgMore = (ImageView) convertView.findViewById(R.id.news_more_3line);
		txtTime = (TextView) convertView.findViewById(R.id.txt_time_3line);
		txtCommentNum = (TextView) convertView
				.findViewById(R.id.txt_comment1_3line);
		linAddTag = (LinearLayout) convertView
				.findViewById(R.id.lin_addlabel_3line);
		linAddTag.setVisibility(View.GONE);
		txtAbstract = (TextView) convertView
				.findViewById(R.id.txt_abstract_nopic);
		imgMore.setVisibility(View.GONE);
		txtAbstract.setVisibility(View.GONE);

		line3TimeGruop = (LinearLayout) convertView
				.findViewById(R.id.title_3line);

		imgDelete = (ImageView) convertView.findViewById(R.id.img_delete);
		linParent = (LinearLayout) convertView.findViewById(R.id.lin_parent);
		linTagLine = (RelativeLayout) convertView.findViewById(R.id.lin_tagline);
	
	}

	public void setDatas(final int position, boolean isLoading) {

		data = (NewsListEntity) adapter.getItem(position);
		if (data == null) {
			return;
		}
		imgDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (deleteClick != null) {
					deleteClick.OnClick(data, position);
				}
			}
		});
		// ���±���

		txtTitle.setTextSize(FontUtil.getSearchTitleTextSize(context));
		txtTitle.setText(data.getArticle_title().replace("\n", ""));

		setDZ(position);

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
		LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linTagLine
				.getLayoutParams();
		layoutParams2.setMargins(0,
				BaseActivity.getDensity_int(FontUtil.getLineMaigrn4_5()), 0, 0);
		linTagLine.setLayoutParams(layoutParams2);

	}

	/**
	 * �ж�һ���ǲ��Ƕ��Ӹ�ʽ
	 */
	private void setDZ(int position) {
		data = (NewsListEntity) adapter.getItem(position);
		if (!Utils.isStringEmpty(data.getChannel_id())
				&& (data.getChannel_id().equals(ListItemBaseNews.ChannelId_DZ) || // ����
				data.getChannel_id().equals(ListItemBaseNews.ChannelId_NH))) { // �ں�
			line3TimeGruop.setVisibility(View.GONE);
		} else {
			line3TimeGruop.setVisibility(View.VISIBLE);
			setOtherData();
		}

	}

	private void setOtherData() {
		txtCommentNum.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
		// ���·���ʱ��
		String timeN = Utils.getCollectTime(data.getArticle_pubDate());
		txtTime.setText(timeN);
		// ������Դ
		if (Utils.isStringEmpty(data.getArticle_source().replace("\n", ""))) {
			txtSource.setVisibility(View.GONE);
		} else {
			txtSource.setVisibility(View.VISIBLE);
			txtSource.setText(data.getArticle_source().replace("\n", ""));
		}

	}

	public interface OnDeleteClick {
		public void OnClick(NewsListEntity entity, int position);
	}

}

