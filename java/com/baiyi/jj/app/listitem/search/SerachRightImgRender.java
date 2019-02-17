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

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.CmsApplication;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.news.NoImgRender;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.RemoteImageView;

/**
 * @author tangkun
 */
public class SerachRightImgRender extends SearchHolder/**implements GdapterTypeRender*/
{

    private TextView txtTitle = null;
    private RemoteImageView imgLeft = null;
    private TextView txtSource = null;
    private TextView txtTime = null;
    private TextView txtCommentNum = null;
    private ImageView imgMore = null;

    private ImageView imgDelete = null;
    private boolean isDelete = false;

    private LinearLayout line2TimeGruop = null;

    private LinearLayout linAddTag = null;

    private Context context = null;

    private NewsListEntity data = null;

    private LinearLayout linParent = null;
    private OnYDeleteClick yDeleteClick = null;

    private LinearLayout linAll = null;

    /**
     * @param context
     */
    public SerachRightImgRender(View arg0, Context context,
                                boolean isEdit, OnYDeleteClick deleteClick) {
        super(arg0);
        this.isDelete = isEdit;
        this.context = context;
        this.yDeleteClick = deleteClick;
//		this.adapter = searchDetailAdapter;
        setEvents(arg0);
//    	convertView = ContextUtil.getLayoutInflater(context).inflate(R.layout.list_item_collect_oneleft, null);
    }


    public void setBitmap(NewsListEntity data) {
        if (imgLeft.getVisibility() == View.VISIBLE) {
//			imgLeft.loadImg(data.toImgList().get(0).getMedia_path());
            GlideTool.getListImage(context, data.toImgList().get(0).getMedia_path(), imgLeft);
        }


    }


//	@Override
//	public View getConvertView() {
//		// TODO Auto-generated method stub
//		return convertView;
//	}

    public void setEvents(View convertView) {
        // TODO Auto-generated method
        imgLeft = (RemoteImageView) convertView.findViewById(R.id.img_one_left);
        int imgW = (Config.getInstance().getScreenWidth(context) - ContextUtil.dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset)) / 3;
        int imgH = (int) (imgW * ListItemBaseNews.Defult_Img_Percent);
        imgLeft.getLayoutParams().width = imgW;
        imgLeft.getLayoutParams().height = imgH;


        txtCommentNum = (TextView) convertView.findViewById(R.id.txt_comment1);
        txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle1);
        txtTime = (TextView) convertView.findViewById(R.id.txt_time1);
        txtSource = (TextView) convertView.findViewById(R.id.txt_source1);

        line2TimeGruop = (LinearLayout) convertView.findViewById(R.id.title_2line);
        line2TimeGruop.setVisibility(View.VISIBLE);
        linAddTag = (LinearLayout) convertView.findViewById(R.id.lin_addlabel1);
        imgMore = (ImageView) convertView.findViewById(R.id.news_more1);
        linAddTag.setVisibility(View.GONE);
        imgMore.setVisibility(View.GONE);
        imgDelete = (ImageView) convertView.findViewById(R.id.img_delete);
        linParent = (LinearLayout) convertView.findViewById(R.id.lin_parent);
        linAll = (LinearLayout) convertView.findViewById(R.id.lin_item);

    }

    /* (non-Javadoc)
     * @see com.baiyi.jj.app.adapter.base.GdapterTypeRender#setDatas(int, boolean)
     */
    public void setDatas(final int position, NewsListEntity entity) {
        // TODO Auto-generated method stub

        data = entity;
        if (data == null) {
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

        setBitmap(data);

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
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) line2TimeGruop
                .getLayoutParams();
        layoutParams2.setMargins(0,
                BaseActivity.getDensity_int(FontUtil.getLineMaigrn4_5()), 0, 0);
        line2TimeGruop.setLayoutParams(layoutParams2);

    }

    private void displayOneCenter(NewsListEntity data) {
        String timeN = Utils.getCollectTime(data.getArticle_pubDate());
        // ���±���
//		txtTitle.setText(Utils.ToDBC(data.getArticle_title().replace(" ", "").replace("\n", "")));
        txtTitle.setText(data.getArticle_title());
        txtTitle.setTextSize(FontUtil.getSearchTitleTextSize(context));
        // ���·���ʱ��
        txtTime.setText(timeN);
        txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
        // ������Դ
        if (Utils.isStringEmpty(data.getArticle_source().replace("\n", ""))) {
            txtSource.setVisibility(View.GONE);
        }
        txtSource.setText(data.getArticle_source());
        // ����������Ŀ
        txtCommentNum.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
    }

    public interface OnYDeleteClick {
        public void OnClick(NewsListEntity entity, int position);
    }
}
