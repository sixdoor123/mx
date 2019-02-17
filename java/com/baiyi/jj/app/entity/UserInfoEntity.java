package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_userinfo")
public class UserInfoEntity implements Serializable {
	// [{"data":{"AC":"10471","RG":"","ST":"2","EM":"","MN":"","ID":"2230","CR":null,
	// "LN":"","TM":"2014\/12\/20
	// 10:05:00","Token":"B43FXLMAISV","MO":"15999999999","TP":"1"},
	// "state":1,"msg":"��¼�ɹ�"}]
//	{
//		"data": [
//		{
//			"avatar": null,
//				"client_id": "",
//				"country": "U.S",
//				"display": "aaaaaa",
//				"email": "234@qq.com",
//				"first_name": "",
//				"integral": 0,
//				"job": null,
//				"language": "English",
//				"last_name": "",
//				"location": null,
//				"mobile": null,
//				"mobile_code": "123456",
//				"paypal": null,
//				"provider": "local",
//				"userid": 53
//		}
//		],
//		"state": 200
//	}

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String avatar;
	@DatabaseField
	private String country;
	@DatabaseField
	private String display;
	@DatabaseField
	private String email;
	@DatabaseField
	private String first_name;
	@DatabaseField
	private String last_name;
	@DatabaseField
	private String job;
	@DatabaseField
	private String language;
	@DatabaseField
	private String location;
	@DatabaseField
	private String mobile;
	@DatabaseField
	private String name;
	@DatabaseField
	private String paypal;
	@DatabaseField
	private String MID;
	@DatabaseField
	private int integral;
	@DatabaseField
	private int userType;

	public static int UserType_Gust = 0;
	public static int UserType_Accout = 1;
	public static int UserType_Three = 2;

	private String Token;
	private String RefreshToken;
	private String tokenType;
	private String Account;
	private String Pwd;

	public UserInfoEntity() {
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String MID) {
		this.MID = MID;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getRefreshToken() {
		return RefreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getPwd() {
		return Pwd;
	}

	public void setPwd(String pwd) {
		Pwd = pwd;
	}


	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPaypal() {
		return paypal;
	}

	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}



	private int state;
	private String msg;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
