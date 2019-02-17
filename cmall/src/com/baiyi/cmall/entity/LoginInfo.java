package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * 登录成功后返回的信息
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 下午1:42:49
 */
public class LoginInfo implements Serializable {

	private String id;
	// 登录名
	private String loginName;
	// 用户名
	private String userName;
	// 公司名称
	private String companyName;
	// 手机
	private String mobile;
	// 用户ID
	private String userID;
	// 商家ID
	private String companyID;

	// 邮箱
	private String email;

	private String token;

	// 密码
	private String pwd;

	// 结果信息
	private RequestNetResultInfo resultInfo;

	// 判断是否是商家
	private boolean iscompany;

	// 进入会员中心的路径
	private String baseAddress;

	//图片输出路径
	private String headPortrait;
	
	public String getBaseAddress() {
		return baseAddress;
	}

	public void setBaseAddress(String baseAddress) {
		this.baseAddress = baseAddress;
	}

	/**
	 * @return the iscompany
	 */
	public boolean isIscompany() {
		return iscompany;
	}

	/**
	 * @param iscompany
	 *            the iscompany to set
	 */
	public void setIscompany(boolean iscompany) {
		this.iscompany = iscompany;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd
	 *            the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the companyID
	 */
	public String getCompanyID() {
		return companyID;
	}

	/**
	 * @param companyID
	 *            the companyID to set
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the resultInfo
	 */
	public RequestNetResultInfo getResultInfo() {
		return resultInfo;
	}

	/**
	 * @param resultInfo
	 *            the resultInfo to set
	 */
	public void setResultInfo(RequestNetResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	
	

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginInfo [id=" + id + ", loginName=" + loginName + ", userName=" + userName + ", userID=" + userID
				+ ", companyID=" + companyID + ", mobile=" + mobile + ", email=" + email + ", token=" + token + ", pwd="
				+ pwd + ", resultInfo=" + resultInfo + "]";
	}

}
