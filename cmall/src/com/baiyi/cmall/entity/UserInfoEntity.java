/**
 * 
 */
package com.baiyi.cmall.entity;

import java.io.Serializable;

import com.baiyi.cmall.utils.Utils;

/**
 * 用户基本信息
 * 
 * @author tangkun
 * 
 */
public class UserInfoEntity implements Serializable {
	// [{"data":{"AC":"10471","RG":"","ST":"2","EM":"","MN":"","ID":"2230","CR":null,
	// "LN":"","TM":"2014\/12\/20
	// 10:05:00","Token":"B43FXLMAISV","MO":"15999999999","TP":"1"},
	// "state":1,"msg":"登录成功"}]
	// 会员id
	private String id;
	private String MID;
	// 会员名称
	private String MN;
	// 会员登录名
	private String LN;
	// 会员Email
	private String EM;
	// 会员手机号码
	private String MO;
	// 会员类型
	private String TP;
	// 会员等级
	private String RG;
	// 会员账号
	private String AC;
	// Credential
	private String CR;
	// 会员状态
	private String ST;
	// 时间戳
	private String TM;

	private String Stub;
	private String Token;
	private String PublicKey;
	//昵称
	private String Name;
	
	private String Account;
	private String Pwd;
	//用户头像
	private String PicPath;
	//用户等级
	private String Level;
	//当前级别升级积分（成长值），
	private int UpgradePoints;
	//下级别升级积分（成长值），
	private int NextUpgradePoints;
	//是否支付密码
	private boolean IsSetPayPassword;
	//是否设置密码
	private boolean IsSetPassword;
	//是否
	private boolean IsSetLoginName;
	//消息数量
	private int UserUnReadCount;
	//群发消息最大ID
	private int MultiMaxID;
	
	//商家ID
	private String companyID;
	//用户ID
	private String userID;

	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the mN
	 */
	public String getMN() {
		return MN;
	}

	/**
	 * @param mN the mN to set
	 */
	public void setMN(String mN) {
		MN = mN;
	}

	/**
	 * @return the companyID
	 */
	public String getCompanyID() {
		return companyID;
	}

	/**
	 * @param companyID the companyID to set
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

//	public String getMN() {
//		return MN;
//	}
//
//	public void setMN(String mN) {
//		MN = mN;
//	}

	public String getLN() {
		return LN;
	}

	public void setLN(String lN) {
		LN = lN;
	}

	public String getEM() {
		return EM;
	}

	public void setEM(String eM) {
		EM = eM;
	}

	public String getMO() {
		return MO;
	}

	public void setMO(String mO) {
		MO = mO;
	}

	public String getTP() {
		return TP;
	}

	public void setTP(String tP) {
		TP = tP;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getAC() {
		return AC;
	}

	public void setAC(String aC) {
		AC = aC;
	}

	public String getCR() {
		return CR;
	}

	public void setCR(String cR) {
		CR = cR;
	}

	public String getST() {
		return ST;
	}

	public void setST(String sT) {
		ST = sT;
	}

	public String getTM() {
		return TM;
	}

	public void setTM(String tM) {
		TM = tM;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getStub() {
		return Stub;
	}

	public void setStub(String stub) {
		Stub = stub;
	}

	public String getPublicKey() {
		return PublicKey;
	}

	public void setPublicKey(String publicKey) {
		PublicKey = publicKey;
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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPicPath() {
		return PicPath;
	}

	public void setPicPath(String picPath) {
		PicPath = picPath;
	}

	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}

	public int getUpgradePoints() {
		return UpgradePoints;
	}

	public void setUpgradePoints(int upgradePoints) {
		UpgradePoints = upgradePoints;
	}

	public int getNextUpgradePoints() {
		return NextUpgradePoints;
	}

	public void setNextUpgradePoints(int nextUpgradePoints) {
		NextUpgradePoints = nextUpgradePoints;
	}

	public boolean isIsSetPayPassword() {
		return IsSetPayPassword;
	}

	public void setIsSetPayPassword(boolean isSetPayPassword) {
		IsSetPayPassword = isSetPayPassword;
	}

	public boolean isIsSetPassword() {
		return IsSetPassword;
	}

	public void setIsSetPassword(boolean isSetPassword) {
		IsSetPassword = isSetPassword;
	}

	public boolean isIsSetLoginName() {
		return IsSetLoginName;
	}

	public void setIsSetLoginName(boolean isSetLoginName) {
		IsSetLoginName = isSetLoginName;
	}

	public int getUserUnReadCount() {
		return UserUnReadCount;
	}

	public void setUserUnReadCount(int userUnReadCount) {
		UserUnReadCount = userUnReadCount;
	}

	public int getMultiMaxID() {
		return MultiMaxID;
	}

	public void setMultiMaxID(int multiMaxID) {
		MultiMaxID = multiMaxID;
	}

	public void setDecryptedStub(String stub) {
		String[] stubArray = Utils.split(stub, "|");
		this.MID = stubArray[0];
		this.MN = stubArray[1];
		this.LN = stubArray[2];
		this.EM = stubArray[3];
		this.MO = stubArray[4];
		this.TP = stubArray[5];
		this.RG = stubArray[6];
		this.AC = stubArray[7];
		this.ST = stubArray[8];
		this.TM = stubArray[9];
	}
}
