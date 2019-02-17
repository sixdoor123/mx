package com.baiyi.cmall.entity;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.baiyi.cmall.R.id;

/**
 * 供应意向单 - 详情 - 属性
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-25 下午5:28:32
 */
public class IntentionDetailStandardInfo implements Serializable {

	// 属性
	// 参数id
	private String id;
	// 属性类名称
	private String codevalue;
	// 属性名
	private String propertyname;
	// 属性值
	private String propertyvalue;
	//属性值单位
	private String propertyunit;
	private String propertyid;

	/**
	 * @return the propertyid
	 */
	public String getPropertyid() {
		return propertyid;
	}

	/**
	 * @param propertyid the propertyid to set
	 */
	public void setPropertyid(String propertyid) {
		this.propertyid = propertyid;
	}

	/**
	 * @return the propertyunit
	 */
	public String getPropertyunit() {
		return propertyunit;
	}

	/**
	 * @param propertyunit the propertyunit to set
	 */
	public void setPropertyunit(String propertyunit) {
		this.propertyunit = propertyunit;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the codevalue
	 */
	public String getCodevalue() {
		return codevalue;
	}

	/**
	 * @param codevalue
	 *            the codevalue to set
	 */
	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	/**
	 * @return the propertyname
	 */
	public String getPropertyname() {
		return propertyname;
	}

	/**
	 * @param propertyname
	 *            the propertyname to set
	 */
	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	/**
	 * @return the propertyvalue
	 */
	public String getPropertyvalue() {
		return propertyvalue;
	}

	/**
	 * @param propertyvalue
	 *            the propertyvalue to set
	 */
	public void setPropertyvalue(String propertyvalue) {
		this.propertyvalue = propertyvalue;
	}

//	@Override
//	public int describeContents() {
//
//		return 1;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		dest.writeString(id);
//		dest.writeString(codevalue);
//		dest.writeString(propertyname);
//		dest.writeString(propertyvalue);
//
//	}
//
//	public IntentionDetailStandardInfo(Parcel in) {
//		id = in.readString();
//		codevalue = in.readString();
//		propertyname = in.readString();
//		propertyvalue = in.readString();
//
//	}
//
//	public static final Creator<IntentionDetailStandardInfo> CREATOR = new Creator<IntentionDetailStandardInfo>() {
//		public IntentionDetailStandardInfo createFromParcel(Parcel in) {
//			return new IntentionDetailStandardInfo(in);
//		}
//		public IntentionDetailStandardInfo[] newArray(int size) {
//			return new IntentionDetailStandardInfo[size];
//		}
//	};

	public IntentionDetailStandardInfo() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IntentionDetailStandardInfo [id=" + id + ", codevalue="
				+ codevalue + ", propertyname=" + propertyname
				+ ", propertyvalue=" + propertyvalue + "]";
	}

	
	
}
