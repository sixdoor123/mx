package com.baiyi.cmall.activities.main.business.adapter;

  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
  
public class HorizontalListViewAdapter extends BaseAdapter{  
    private List<String> imgPaths;  
    private Context mContext;  
    private LayoutInflater mInflater;  
    Bitmap iconBitmap;  
    private int selectIndex = -1;  
    
    public Map<String, String> selectPaths = new HashMap<String, String>();
  
    public HorizontalListViewAdapter(Context context){  
        this.mContext = context;  
        this.imgPaths = new ArrayList<String>(); 
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);  
    }  
    @Override  
    public int getCount() {  
        return imgPaths.size();  
    } 
    
    public void setDatas(List<String> paths) {
    	this.imgPaths = paths; 
    	this.notifyDataSetChanged();
	}
    public List<String> getDatas(){
    	return imgPaths;
    }
    @Override  
    public Object getItem(int position) {  
        return position;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
    
  
    ViewHolder holder;  
    public View getView(int position, View convertView, ViewGroup parent) {  
        
        if(convertView==null){  
            holder = new ViewHolder();  
            convertView = mInflater.inflate(R.layout.item_list_image, null);  
            holder.mImage=(ImageView)convertView.findViewById(R.id.img_photo); 
    		int imgFillH = BaseActivity.getDensity_int(200);
    		int imgFillW = (int) (imgFillH*0.6);
    		 holder.mImage.getLayoutParams().width = imgFillW;
    		 holder.mImage.getLayoutParams().height = imgFillH;
            holder.mCheck=(CheckBox)convertView.findViewById(R.id.chb_photo);  
            holder.mCheck.setChecked(false);
            holder.linItem = (LinearLayout) convertView.findViewById(R.id.lin_item);
            convertView.setTag(holder);  
        }else{  
            holder=(ViewHolder)convertView.getTag();  
        }  
        final String path = imgPaths.get(position);
        if (selectPaths.containsKey(path)) {
			holder.mCheck.setChecked(true);
		}else {
			holder.mCheck.setChecked(false);
		}
//        holder.linItem.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (holder.mCheck.isChecked()) {
//					System.out.println("----------");
//					holder.mCheck.setChecked(false);
//					map.remove(path);
//				}else {
//					System.out.println("==========");
//					holder.mCheck.setChecked(true);
//					map.put(path, path);
//				}
//			}
//		});
//        if(position == selectIndex){  
//        	holder.mCheck.setChecked(true);  
//        }else{  
//        	holder.mCheck.setChecked(false);  
//        }  
          
//        iconBitmap = getBitmapThumnail(imgPaths[position]);  
//        holder.mImage.setImageBitmap(iconBitmap);  
        ImageLoader.getInstance().displayImage("file://" + imgPaths.get(position), holder.mImage);
        return convertView;  
    }  
    
    public void addSelectPath(String Path) {
		selectPaths.put(Path, Path);
	}
    
    public void removeSelectPath(String path) {
		selectPaths.remove(path);
	}
    
    
    public void clearCheck() {
    	selectPaths.clear();
    	this.notifyDataSetChanged();
    	
	}
  
    private static class ViewHolder {  
        private ImageView mImage ;  
        private Checkable mCheck;  
        private LinearLayout linItem;
    }  
    
    private Bitmap getBitmapThumnail(String path){ 
    	Bitmap bitmap = null;
    	
    	
    	return bitmap;
    }
    
//    private Bitmap getPropThumnail(int id){  
////        Drawable d = mContext.getResources().getDrawable(id);  
////        Bitmap b = BitmapUtil.drawableToBitmap(d);  
//////      Bitmap bb = BitmapUtil.getRoundedCornerBitmap(b, 100);  
////        int w = mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);  
////        int h = mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);  
//          
////        Bitmap thumBitmap = ThumbnailUtils.extractThumbnail(b, w, h);  
//          
//        return thumBitmap;  
//    }  
    public void setSelectIndex(int i){  
        selectIndex = i;  
    }  
}
