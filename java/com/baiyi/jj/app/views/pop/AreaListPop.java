package com.baiyi.jj.app.views.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.entity.localnews.LocationEntity;
import com.baiyi.jj.app.manager.LocationSManager;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21 0021.
 */
public class AreaListPop{

    OnItemSelected onItemSelected;
    private Context context = null;
    private PopupWindow popupWindow = null;
    private ImageView btnAreaSel;

    public AreaListPop(Context context, ImageView btnArea) {
        this.context = context;
        this.btnAreaSel = btnArea;
        initPopView();

    }

    private void initPopView() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_arealist, null);
        popupWindow = new PopupWindow(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                btnAreaSel.setSelected(false);
                backGroundAlpha(1f);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);

        final List<LocationEntity> locationEntities = LocationSManager.getLocationArea2(context);
        ListView listView = (ListView)view.findViewById(R.id.list_area);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onItemSelected != null){
                    onItemSelected.selectItem(locationEntities.get(i));
                }
            }
        });

        // Ìí¼ÓÊý¾Ý
        for(int i = 0; i < locationEntities.size(); i++){
            LocationEntity entity = locationEntities.get(i);
            adapter.add(entity.getAreaName());
        }
    }

    /**
     * ?????????????
     *
     * @param alp
     */
    private void backGroundAlpha(float alp) {
        WindowManager.LayoutParams lParams = ((BaseActivity) context)
                .getWindow().getAttributes();
        lParams.alpha = alp;
        ((BaseActivity) context).getWindow().setAttributes(lParams);

    }


    public void showPopView(View parent) {
        if (popupWindow.isShowing()) {
            parent.setSelected(false);
            popupWindow.dismiss();
        } else {
            parent.setSelected(true);
            popupWindow.showAsDropDown(parent,0,BaseActivity.getDensity_int(10));

        }
    }



    public interface OnItemSelected{
        void selectItem(LocationEntity entity);
    }

    public void setOnItemSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }
}
