/**
 * 
 */
package com.baiyi.cmall.activities.main.business.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter;
import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.activities.main.mall.holder.MallHolder;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.model.Blm;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.tencent.a.a.a.a.h;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import net.sf.json.JSONObject;

/**
 * 商城主页 item
 * 
 * @author tangkun
 * 
 */
public class ShopProAdapter extends BaseRecyclerAdapter<PbiEntity> {

	/**
	 * @param objects
	 */
	public ShopProAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_mall, parent, false);
		MallHolder vh = new MallHolder(view);
		return vh;
	}

	@Override
	public void onBindVH(ViewHolder viewHolder, int position) {
		PbiEntity entity = objects.get(position);
		String name = objects.get(position).getPn() + "(" + entity.getGgs()
				+ ")";
		MallHolder holder = (MallHolder) viewHolder;
		((MallHolder) holder).txtName.setText(name);
		String h = entity.getPp();
		((MallHolder) holder).txtPrice.setText("￥" + entity.getPp());
		((MallHolder) holder).txtUnit.setText("/ton");

		// loadImg(entity, holder.imgConver);
		// loadImg(entity, holder.imgSigin);
	}

	/**
	 * 图片下载
	 * 
	 * @param urlStr
	 * @param view
	 */

	private void loadImg(String urlStr, final ImageView view) {

		ImageLoader loader = new ImageLoader(context, true);
		loader.setUrl(urlStr);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				Log.d("TT", "errorMessage");
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				BitmapDrawable drawable = (BitmapDrawable) result;
				view.setBackground(drawable);
				Log.d("TT", "onCompelete");

			}
		});

		CmallApplication.getImageStrategy().startLoader(loader);

	}
}
