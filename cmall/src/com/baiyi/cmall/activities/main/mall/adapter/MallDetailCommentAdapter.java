/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.entity.CtmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailCommentEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailCommentTipEntity;
import com.baiyi.cmall.activities.main.mall.holder.CommentHolder;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

/**
 * 商城-详情-评论列表
 * 
 * @author tangkun
 * 
 */
public class MallDetailCommentAdapter extends BaseRecyclerAdapter<CtmlEntity> {

	/**
	 * @param objects
	 */
	public MallDetailCommentAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_mall_detail_comment, parent, false);
		CommentHolder vh = new CommentHolder(view);
		return vh;
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		// // TODO Auto-generated method stub
		CommentHolder h = (CommentHolder) holder;
		CtmlEntity entity = objects.get(position);
		h.txtName.setText(entity.getUn());
		h.txtTime.setText(Utils.getCurrentTime2(entity.getPt()));
		h.txtContent.setText(entity.getCc());
		h.rbCount.setProgress(entity.getSl());

		ImageTools.getCircleImage(context, h.imgHead, entity.getUh(),
				R.drawable.login);

	}

}
