package com.baiyi.jj.app.manager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.baiyi.jj.app.entity.localnews.LocationEntity;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/20 0020.
 */
public class LocationSManager {
    private static final long REFRESH_TIME = 5000L;
    private static final float METER_POSITION = 0.0f;
    private static ILocationListener mLocationListener;
    private static LocationListener listener = new MyLocationListener();
//    private static GoeLocationListener addressListener;
    private static List<String> areaLists = new ArrayList<>();


    private static class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {//��λ�ı����
            if (mLocationListener != null) {
                mLocationListener.onSuccessLocation(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {//��λ״̬����

        }

        @Override
        public void onProviderEnabled(String provider) {//��λ״̬���ü���

        }

        @Override
        public void onProviderDisabled(String provider) {//��λ״̬�����ü���
            if (mLocationListener != null) {
                mLocationListener.onSuccessLocation(null);
            }
        }
    }

    public static void initAreaData(Context context){
        String areaStr = context.getResources().getString(R.string.tip_mexico22);
        areaLists = Utils.splitToList(areaStr,",");
//        try {
//            JSONArray array = new JSONArray();
//            for (int i = 0;i<areaLists.size()-1;i+=2){
//                JSONObject object = new JSONObject();
//                object.put("areashort",areaLists.get(i+1));
//                object.put("areaname",areaLists.get(i));
//                TLog.e("hash",areaLists.get(i)+"-----"+areaLists.get(i+1));
//                array.put(object);
//            }
//            TLog.e("array9999999999999",array.toString());
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

    }

    /**
     * GPS��ȡ��λ��ʽ
     */
    public static Location getGPSLocation(@NonNull Context context) {
        Location location = null;
        LocationManager manager = getLocationManager(context);
        //�߰汾��Ȩ�޼��
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//�Ƿ�֧��GPS��λ
            //��ȡ����GPS��λ��Ϣ������ǵ�һ�δ򿪣�һ����ò�����λ��Ϣ��һ������������������Ч��ʱ�䷶Χ���Ի�ȡ��λ��Ϣ
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return location;
    }

    /**
     * network��ȡ��λ��ʽ
     */
    public static Location getNetWorkLocation(Context context) {
        Location location = null;
        LocationManager manager = getLocationManager(context);
        //�߰汾��Ȩ�޼��
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//�Ƿ�֧��Network��λ
            //��ȡ����network��λ��Ϣ
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return location;
    }

    /**
     * ��ȡ��õĶ�λ��ʽ
     */
    public static Location getBestLocation(Context context, Criteria criteria) {
        Location location;
        LocationManager manager = getLocationManager(context);
        if (criteria == null) {
            criteria = new Criteria();
        }
        String provider = manager.getBestProvider(criteria, true);
        if (TextUtils.isEmpty(provider)) {
            //����Ҳ������ʺϵĶ�λ��ʹ��network��λ
            location = getNetWorkLocation(context);
        } else {
            //�߰汾��Ȩ�޼��
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            //��ȡ���ʺϵĶ�λ��ʽ�����Ķ�λȨ��
            location = manager.getLastKnownLocation(provider);
        }
        return location;
    }

    public static LocationEntity getAddress(double lat,double lng, Geocoder geocoder)  {
        LocationEntity entity = new LocationEntity();
        try {
            //���ݾ�γ�Ȼ�ȡ����λ����Ϣ
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                entity.setLatitude(address.getLatitude());
                entity.setLongitude(address.getLongitude());
                entity.setAreaName(address.getAdminArea());
                entity.setCountryName(address.getCountryName());
                entity.setCityName(address.getLocality());
//                stringBuilder.append(address.getCountryName()).append("_");//����
//                stringBuilder.append(address.getLocality()).append("_");//��
//                stringBuilder.append(address.getAdminArea()).append("_");//ʡ��
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }
    /**
     * ��λ����
     */
    public static void addLocationListener(Context context, String provider, ILocationListener locationListener) {

        addLocationListener(context, provider, REFRESH_TIME, METER_POSITION, locationListener);
    }

    /**
     * ��λ����
     */
    public static void addLocationListener(Context context, String provider, long time, float meter, ILocationListener locationListener) {
        if (locationListener != null) {
            mLocationListener = locationListener;
        }
        if (listener == null) {
            listener = new MyLocationListener();
        }
        LocationManager manager = getLocationManager(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (mLocationListener != null) {
                mLocationListener.onSuccessLocation(null);
            }
            return;
        }
        manager.requestLocationUpdates(provider, time, meter, listener);
    }

    /**
     * ȡ����λ����
     */
    public static void unRegisterListener(Context context) {
        if (listener != null) {
            LocationManager manager = getLocationManager(context);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //�Ƴ���λ����
            manager.removeUpdates(listener);
        }
    }

    private static LocationManager getLocationManager(@NonNull Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static void loadAreaName(final double lat, final double lng, final GoeLocationListener addressListener) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        final Request request = new Request.Builder()
                .url(NetUtils.getAreaName(lat,lng))
                .get()
                .build();
        TLog.e("url",NetUtils.getAreaName(lat,lng));
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (addressListener != null){
                    addressListener.onLoadAddress(null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
//                TLog.e(TAG+"str-0------",str);
                LocationEntity entity = null;
                try{
                    JSONObject root = new JSONObject(str);
                    JSONArray result = root.getJSONArray("results");
                    if (result != null && result.length()>=1);
                    {
                        JSONObject object = result.getJSONObject(0);
                        String shortname = "";
                        JSONArray arraycompon = object.getJSONArray("address_components");
                        for (int k = 0;k<arraycompon.length();k++){
                            JSONObject object1 = arraycompon.getJSONObject(k);
                            JSONArray typearray = object1.getJSONArray("types");
                            if (typearray.toString().contains("administrative_area_level_1")){
                                shortname = object1.getString("short_name");
                                break;
                            }
                        }
                        TLog.e("address",shortname+"-----------");
                        for (int i = 0;i<areaLists.size()-1;i+=2){
                            if (shortname.equals(areaLists.get(i+1))){
                                TLog.e("hash",areaLists.get(i)+"-----"+areaLists.get(i+1));
                                entity = new LocationEntity();
                                entity.setAreaName(areaLists.get(i));
                                entity.setLatitude(lat);
                                entity.setLongitude(lng);
                                break;
                            }
                        }
//                        String address = object.getString("formatted_address");
//                        String[] adds = Utils.split(address,",");
//                        if (adds.length>=2){
//                            address = adds[adds.length-2];
//                        }
//                        TLog.e("address",address+"-----------");
//                        for (int i = 0;i<areaLists.size()-1;i+=2){
////                            if (address.contains(areaLists.get(i+1)) || address.contains(areaLists.get(i)) || areaLists.get(i).contains(address)){
//                            if (address.contains(areaLists.get(i+1))){
//                               //  Michoacán de Ocampo  Michoacán
//                                TLog.e("hash",areaLists.get(i)+"-----"+areaLists.get(i+1));
//                                entity = new LocationEntity();
//                                entity.setAreaName(areaLists.get(i));
//                                entity.setLatitude(lat);
//                                entity.setLongitude(lng);
//                                break;
//                            }
//                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                if (addressListener != null){
                    addressListener.onLoadAddress(entity);
                }
            }
        });
    }

    public static List<LocationEntity> getLocationArea(Context context){
        String channel = TextLengthUtils.getAssetsStr(context, "arealist");
        List<LocationEntity> locationEntities = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(channel);
            for (int i = 0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                LocationEntity entity = new LocationEntity();
                entity.setAreaName(object.optString("areaname"));
                entity.setAreashort(object.optString("areashort"));
                locationEntities.add(entity);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return locationEntities;

    }

    public static List<LocationEntity>  getLocationArea2(Context context){
        String areaStr = context.getResources().getString(R.string.tip_mexico22);
        areaLists = Utils.splitToList(areaStr,",");
        List<LocationEntity> locationEntities = new ArrayList<>();
            for (int i = 0;i<areaLists.size()-1;i+=2){
                LocationEntity entity = new LocationEntity();
                entity.setAreaName(areaLists.get(i));
                entity.setAreashort(areaLists.get(i+1));
                locationEntities.add(entity);
            }
        return locationEntities;
    }

//    public void setAddressListener(GoeLocationListener addressListener) {
//        this.addressListener = addressListener;
//    }

    /**
     * �Զ���ӿ�
     */
    public interface ILocationListener {
        void onSuccessLocation(Location location);
    }
    public interface GoeLocationListener{
        void onLoadAddress(LocationEntity entity);
    }
}
