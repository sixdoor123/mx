/**
 *
 */
package com.baiyi.jj.app.listitem.search;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.baiyi.jj.app.application.CmsApplication;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.news.NoImgRender;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.Utils;

/**
 * @author tangkun
 */
public class SearchNoImgRender extends SearchHolder/**implements GdapterTypeRender*/
{
    private TextView txtTitle = null;
    private TextView txtSource = null;
    private ImageView imgMore = null;
    private TextView txtTime = null;
    protected TextView txtAbstract = null;
    private TextView txtCommentNum = null;
    private LinearLayout linAddTag = null;

    private ImageView imgDelete = null;
    private boolean isDelete = false;

    private Context context;
    private NewsListEntity data = null;

    private LinearLayout line3TimeGruop = null;

    private TextView txtSuppNum = null;
    private TextView txtOppoNum = null;
    private TextView txtComNum = null;

    private OnDeleteClick deleteClick = null;

    private LinearLayout linParent = null;
    private RelativeLayout linTagLine = null;
    private LinearLayout linAll = null;

    public SearchNoImgRender(View arg0, Context context, boolean isEdit,
                             OnDeleteClick deleteClick) {
        super(arg0);
        this.isDelete = isEdit;
        this.context = context;
//		this.adapter = searchDetailAdapter;
        this.deleteClick = deleteClick;
        setEvents(arg0);
//		convertView = ContextUtil.getLayoutInflater(context).inflate(
//				R.layout.list_item_collect_noimg, null);
    }

//	@Override
//	public View getConvertView() {
//		return convertView;
//	}

    public void setEvents(View convertView) {
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
        linAll = (LinearLayout) convertView.findViewById(R.id.lin_item);

    }

    public void setDatas(final int position, NewsListEntity entity) {

        data = entity;
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
        txtTitle.setTypeface(CmsApplication.getListTitleFace(context));

        setDZ(position, entity);

        imgDelete.setVisibility(isDelete ? View.VISIBLE : View.GONE);

        setMargin();
    }

    private void setMargin() {
        if (this.isDelete) {
            linAll.setPadding(BaseActivity.getDensity_int(-52), 0, 0, 0);
        }
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
    private void setDZ(int position, NewsListEntity entity) {
        data = entity;
        if (!Utils.isStringEmpty(data.getChannel_id())
                && (data.getChannel_id().equals(ListItemBaseNews.ChannelId_DZ) || // ����
                data.getChannel_id().equals(ListItemBaseNews.ChannelId_NH))) { // �ں�
            line3TimeGruop.setVisibility(View.GONE);
            setDZData();
        } else {
            line3TimeGruop.setVisibility(View.VISIBLE);
            setOtherData();
        }

    }

    /**
     * ����ʱ
     */
    private void setDZData() {
        txtComNum.setText(data.getArticle_comment_num() + "");
        txtOppoNum.setText(data.getInterest_opposition_num() + "");
        txtSuppNum.setText(data.getInterest_agree_num() + "");

    }

    /**
     * ����ʱ
     */
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
