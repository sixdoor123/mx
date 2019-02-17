package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.SupportDao;
import com.baiyi.jj.app.entity.NewsDetailCommentEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.UserHeadUtils;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<NewsDetailCommentEntity> datas = null;
    private Context context = null;
    private LayoutInflater inflater;
    private ItemAgreeOnclick agreeOnclick = null;

    public CommentAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        datas = new ArrayList<NewsDetailCommentEntity>();

    }

    public void refresh(){
        this.notifyDataSetChanged();
    }

    public List<NewsDetailCommentEntity> getData() {
        return datas;
    }

    public int getItemCount() {
        if (Utils.isStringEmpty(datas)) {
            return 0;
        }
        return datas.size();
    }

    public void setData(List<NewsDetailCommentEntity> list) {
        if (Utils.isStringEmpty(list)) {
            List<NewsDetailCommentEntity> noList = new ArrayList<NewsDetailCommentEntity>();
            this.getData().clear();
            this.getData().addAll(noList);
            notifyDataSetChanged();
            return;
        }
        this.getData().clear();
        this.getData().addAll(list);
        this.notifyDataSetChanged();

    }

    public void addData(List<NewsDetailCommentEntity> list) {
        if (Utils.isStringEmpty(list)) {
            return;
        }
        this.getData().addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * ����һ������
     */
    public void insert(NewsDetailCommentEntity entity) {
        this.datas.add(0,entity);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder arg0, final int arg1) {
        final NewsDetailCommentEntity entity = datas.get(arg1);
        if (entity == null) {
            return;
        }
        arg0.userName.setText(entity.getComUserName());
        arg0.comContent.setText(entity.getComContent());
        arg0.userName.setTypeface(CmsApplication.getDetailConten(context));
        arg0.comContent.setTypeface(CmsApplication.getDetailConten(context));
        arg0.suppNum.setText(entity.getComAgree() + "");
//		String timeN = Utils.getTimeName(context, entity.getComTime());
        TLog.e("time",entity.getComTime());
        arg0.comTime.setText(Utils.getTimeYMDHHMM(Utils.getTimeSecond(entity.getComTime(),!entity.isNotUtc())));
        arg0.linSupp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (agreeOnclick != null&&!arg0.imgSupp.isSelected()) {
                    agreeOnclick.Onclick(entity, arg1);
                }
            }
        });

        setAgreeView(entity.getComId(), arg0.imgSupp);

        UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
        if (infoEntity != null && entity.getComUserId().equals(infoEntity.getMID())) {
            UserHeadUtils.getInstance(context).loadImage(arg0.imgHead, Utils.getNoNullString(entity.getComUserHead()));
            return;
        }
        setImageHead(arg0.imgHead, entity.getComUserHead());
    }

    private String getTime(String time) {
        if (time.contains("T")) {
            time = time.replace("T", " ");
        }
        return time;
    }


    private void setAgreeView(String comid, ImageView imgAgree) {
        if (Utils.isStringEmpty(comid)) {
            return;
        }
        if (CmsApplication.getUserInfoEntity() != null) {
            imgAgree.setSelected(new SupportDao(context).isSupport(comid, CmsApplication.getUserInfoEntity().getMID()));
        }

    }

    public void updateItem(int itemIndex) {
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = inflater
                .inflate(R.layout.list_item_detail_comment, arg0, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View arg0) {
            super(arg0);
            userName = (TextView) arg0.findViewById(R.id.username);
            comContent = (TextView) arg0.findViewById(R.id.com_comment_content);
            comTime = (TextView) arg0.findViewById(R.id.txt_com_time);
            suppNum = (TextView) arg0.findViewById(R.id.txt_supp_num);
            imgSupp = (ImageView) arg0.findViewById(R.id.img_com_supp);
            linSupp = (LinearLayout) arg0.findViewById(R.id.lin_support);
            imgHead = (CircleImageView) arg0.findViewById(R.id.img_head);

        }

        TextView userName; //�û�����
        TextView comContent; // ��������
        TextView comTime; //����ʱ��
        TextView suppNum; // ��������
        ImageView imgSupp; //����ͼ��
        LinearLayout linSupp; //����
        CircleImageView imgHead; // ͷ��

    }

    private void setImageHead(final ImageView imageView, String converPath) {
        GlideTool.getCommentHead(context, converPath, imageView, R.drawable.default_head);
    }

    public interface ItemAgreeOnclick {
        public void Onclick(NewsDetailCommentEntity entity, int postion);
    }

    public void setAgreeOnclick(ItemAgreeOnclick agreeOnclick) {
        this.agreeOnclick = agreeOnclick;
    }

}
