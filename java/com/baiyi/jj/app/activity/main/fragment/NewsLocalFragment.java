package com.baiyi.jj.app.activity.main.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.AllAttetionAct;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.entity.localnews.LocationEntity;
import com.baiyi.jj.app.manager.LocationSManager;
import com.baiyi.jj.app.manager.NewsListDataManager;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.pop.AreaListPop;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
public class NewsLocalFragment extends NewsBaseFragment{

    private String fidStr = "";

    @Bind(R.id.head_news)
    LinearLayout linHead;
    @Bind(R.id.btn_selectarea)
    ImageView btnSelectArea;
    @Bind(R.id.txt_areaname)
    TextView areaNameTxt;
    @Bind(R.id.btn_relocal)
    ImageView btnLocal;

    private LocationEntity curentLocation;

    boolean isFirst = true;

    public static NewsLocalFragment newInstance() {
        NewsLocalFragment fragment = new NewsLocalFragment();
        fragment.setArguments(null);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLocalData();
    }

    private void initLocalData(){
        LocationSManager.initAreaData(getContext());

        getGPSLocation(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (isFirst){
            String areaName = preference.Get(XMLName.Xml_AreaName,"");
            if (!Utils.isStringEmpty(areaName)){
                curentLocation = new LocationEntity();
                curentLocation.setAreaName(areaName);
                areaNameTxt.setText(areaName);
                addLocationHead(curentLocation);
            }
            isFirst = false;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initPermission();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查权限
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
            }
        }
    }
    /**
     * 通过GPS获取定位信息
     */
    public void getGPSLocation(final boolean isRelocal) {
        Location gps = LocationSManager.getGPSLocation(getContext());
        if (gps == null) {
            TLog.e("location","*******************gps location is null");
            getNetworkLocation(isRelocal);
            //设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
            LocationSManager.addLocationListener(getContext(), LocationManager.GPS_PROVIDER, new LocationSManager.ILocationListener() {
                @Override
                public void onSuccessLocation(Location location) {
                    if (location != null) {
                        loadAddress(location.getLatitude(),location.getLongitude(),isRelocal);
                        TLog.e("location","gps onSuccessLocation location:  lat==" + location.getLatitude() + "     lng==" + location.getLongitude());
                        LocationSManager.unRegisterListener(getActivity());
                    } else {
                        TLog.e("location","gps location is null");
//                        getNetworkLocation();
                    }
                }
            });
        } else {
            loadAddress(gps.getLatitude(),gps.getLongitude(),isRelocal);
            TLog.e("location","*************gps location: lat==" + gps.getLatitude() + "  lng==" + gps.getLongitude());
        }
    }
    private void getNetworkLocation(boolean isRelocal) {
        Location net = LocationSManager.getNetWorkLocation(getContext());
        if (net == null) {
            TLog.e("location","net location is null");
//            areaNameTxt.setText(R.string.tip_location_failure);
            loadAddress(0,0,false);
        } else {
            TLog.e("location","network onSuccessLocation location:  lat==" + net.getLatitude() + "     lng==" + net.getLongitude());
//            net.setLatitude(19.427620);
//            net.setLongitude(-99.14023);
//            net.setLatitude(20.980831);
//            net.setLongitude(-89.61590);
//            net.setLatitude(24.240583);
//            net.setLongitude(-104.70907);
//            net.setLatitude(18.654834);
//            net.setLongitude(-102.637025);

            loadAddress(net.getLatitude(),net.getLongitude(),isRelocal);
        }
    }
    private void loadAddress(double lat, double lng, final boolean isRelocal){

//        Geocoder geocoder = new Geocoder(getContext());
//        boolean falg = geocoder.isPresent();
//        if (falg){
//            LocationEntity entity = LocationSManager.getAddress(lat,lng,geocoder);
//            if (entity != null || !Utils.isStringEmpty(entity.getAreaName())){
//                curentLocation = entity;
//                addLocationHead(entity);
//                return;
//            }
//        }

        LocationSManager.loadAreaName(lat, lng, new LocationSManager.GoeLocationListener() {
            @Override
            public void onLoadAddress(final LocationEntity entity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (curentLocation != null && entity != null){
                            curentLocation = entity;
                        }else {
                            if (isRelocal){
                                areaNameTxt.setText(R.string.tip_location_failure);
                                return;
                            }
                            curentLocation = entity;
                        }
                        addLocationHead(entity);

                        if (entity != null && !Utils.isStringEmpty(entity.getAreaName())){
                            saveLocaArea(entity.getAreaName());
                        }
                    }
                });
            }
        });
    }

    @OnClick({R.id.btn_selectarea,R.id.btn_relocal})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_relocal){
            reLocat();
        }else if (view.getId() == R.id.btn_selectarea){
            showAreaList();
        }
    }

    private void reLocat(){
        areaNameTxt.setText(R.string.tip_location_loading);
        getGPSLocation(true);
    }

    private void addLocationHead(LocationEntity entity){
//        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_location,null);
//        TextView areaNameTxt = (TextView) headView.findViewById(R.id.txt_areaname);
//        btnSelectArea = (ImageView) headView.findViewById(R.id.btn_selectarea);
        if (linHead.getVisibility() == View.VISIBLE){
            if (entity == null || Utils.isStringEmpty(entity.getAreaName())){
                return;
            }
            if (entity.getAreaName().equals(areaNameTxt.getText())){
                TLog.e("head---","--------------------------2--"+areaNameTxt.getText()+"---"+entity.getAreaName());
                return;
            }
        }else {
            linHead.setVisibility(View.VISIBLE);
            if (getCacheSize() > 10){
                return;
            }
        }
        linHead.setVisibility(View.VISIBLE);
        if (entity != null && !Utils.isStringEmpty(entity.getAreaName())){
            areaNameTxt.setText(entity.getAreaName());
            curentLocation = entity;
            loadListData();
        }else {
            String areaName = preference.Get(XMLName.Xml_AreaName,"");
            if (Utils.isStringEmpty(areaName)){
                areaNameTxt.setText(getContext().getResources().getString(R.string.tip_location_failure));
            }else {
                curentLocation = new LocationEntity();
                curentLocation.setAreaName(areaName);
                areaNameTxt.setText(areaName);
                loadListData();
            }
//            btnSelectArea.setSelected(true);
//            showAreaList();
        }
    }
    AreaListPop areaListPop;
    private void showAreaList(){
        if (areaListPop == null){
            areaListPop = new AreaListPop(getContext(),btnSelectArea);
            areaListPop.setOnItemSelected(new AreaListPop.OnItemSelected() {
                @Override
                public void selectItem(LocationEntity entity) {
                    curentLocation = entity;
                    saveLocaArea(curentLocation.getAreaName());
                    areaNameTxt.setText(curentLocation.getAreaName());
                    showAreaList();
                    loadListData();
                }
            });
        }
        areaListPop.showPopView(btnSelectArea);

    }

    private void loadListData(){
        if (curentLocation == null){
            return;
        }
        showNoCacheLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.init();
                setListRefreshing();
            }
        }, 1000);
    }

    private void saveLocaArea(String areaName){
        if (preference != null) {
            preference.Set(XMLName.Xml_AreaName, areaName);
            preference.saveConfig();
        }
    }

    @Override
    public void saveFidStr(String fid) {
        if (preference != null) {
            preference.Set(XMLName.XML_NewsFID+"Local", fidStr);
            preference.saveConfig();
        }
    }

    @Override
    public void initCreatData(boolean isShowNoData) {
//        initLocalData();
        hideNoCacheLoading();
        if (curentLocation == null){
            TLog.e("99","initcreatdata----------location-null");
        }else {
//            TLog.e("88","0000000----------"+curentLocation.getAreaName());
            TLog.e("head---","--------------------------9---"+isShowNoData);
            if (isShowNoData && curentLocation != null){
                loadListData();
            }
        }
    }

    @Override
    public int getCacheSize() {
        if (curentLocation == null || Utils.isStringEmpty(curentLocation.getAreaName())){
            return 0;
        }
        return new NewsListDao(getContext()).getLocalNewsSize(curentLocation.getAreaName());
    }

    @Override
    public List<NewsListEntity> getCacheData(int page, int id) {
        if (curentLocation == null || Utils.isStringEmpty(curentLocation.getAreaName())){
            return null;
        }
        return  new NewsListDao(getContext()).getLocaNewsData(curentLocation.getAreaName(), page, id);
    }

    @Override
    public boolean hasHeadView() {
        return false;
    }

    @Override
    public String getChannelId() {
        return "local";
    }

    @Override
    public String getUrl() {
        return NetUtils.getLocalNewslist();
    }
    @Override
    public boolean isLocal() {
        return true;
    }
    @Override
    public String getPostData(boolean isWifi) {
        fidStr = preference.Get(XMLName.XML_NewsFID+"Local", "");
        UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
        String token = "";
        if (infoEntity != null){
            token = infoEntity.getToken();
        }else {
            String usertoken = CmsApplication.getUserToken();
            if (!Utils.isStringEmpty(usertoken)){
                token = usertoken.substring(usertoken.indexOf(" ")+1);
            }
        }
        if (curentLocation == null || Utils.isStringEmpty(curentLocation.getAreaName())){
            curentLocation = new LocationEntity();
            if (!areaNameTxt.getText().toString().equals(getResources().getString(R.string.tip_location_failure))){
                curentLocation.setAreaName(areaNameTxt.getText().toString());
            }
        }
        return NewsListDataManager.getLocalPost(getContext(),curentLocation,fidStr,isWifi,token);
    }

    @Override
    public ArticleEntity getAricleEntity(JSONArray array) {
        return NewsListDataManager.getParseData(array, false, false, null, false,curentLocation.getAreaName());
    }

    @Override
    public void addMorningHead() {

    }

}
