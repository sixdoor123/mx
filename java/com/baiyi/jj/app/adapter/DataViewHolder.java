package com.baiyi.jj.app.adapter;

import java.util.HashMap;

import android.view.View;

public class DataViewHolder {

	/** 用来存View */
	private HashMap<Integer, View> mapView = new HashMap<Integer, View>();
	/** 用来存数据 */
	private HashMap<String, Object> mapData = new HashMap<String, Object>();

	/** 放view */
	public void setView(int key, View view) {
		this.mapView.put(key, view);
	}

	@SuppressWarnings("unchecked")
	public <T> T getView(int key) {

		return (T) this.mapView.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getView(Class<T> class1, int key) {

		return (T) this.mapView.get(key);
	}
	
	public void setData(String key,Object value){
		mapData.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getData(int key) {

		return (T) this.mapView.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(Class<T> class1, int key) {

		return (T) this.mapView.get(key);
	}
}
