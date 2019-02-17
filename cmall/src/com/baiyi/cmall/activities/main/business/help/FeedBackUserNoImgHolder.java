/**
 * 
 */
package com.baiyi.cmall.activities.main.business.help;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.holder.BaseHolder;

/**
 * @author tangkun
 * 
 */
public class FeedBackUserNoImgHolder extends BaseHolder {


	public TextView txtContent = null;
	public TextView txtTime = null;
	public ImageView imgHead = null;
	public LinearLayout linAddImg = null;
	
	
	public FeedBackUserNoImgHolder(View convertView) {
		super(convertView);
		imgHead = (ImageView) convertView.findViewById(R.id.img_head);
		int imgW = BaseActivity.getDensity_int(35);
		int imgH = BaseActivity.getDensity_int(35);
		imgHead.getLayoutParams().width = imgW;
		imgHead.getLayoutParams().height = imgH;

		txtTime = (TextView) convertView.findViewById(R.id.txt_time);
		txtContent = (TextView) convertView.findViewById(R.id.txt_content);
		linAddImg = (LinearLayout) convertView.findViewById(R.id.lin_img_add);
	}
}
