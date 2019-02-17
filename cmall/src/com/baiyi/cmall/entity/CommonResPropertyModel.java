package com.baiyi.cmall.entity;

/**
 * 瑙勬牸鍙傛暟Model
 * @author 琚佸皬寤�?
 * @since 2016-01-05
 * @version 1.0
 */
public class CommonResPropertyModel{
	private Long ID;
	/**
	 * 搴忓垪鍙�?
	 */
	private static final long serialVersionUID = 6439870065911737134L;
	/**
	 * 灞炴�id
	 */
	private Long propertyid;
	/**
	 * 灞炴�鍒嗙被鍚嶇О
	 */
	private String codevalue;
	/**
	 * 灞炴�鍚�?
	 */
	private String propertyname;
	/**
	 * 灞炴�鍊�?
	 */
	private String propertyvalue;
	/**
	 * 灞炴�鍊煎崟浣�
	 */
	private String propertyunit;
	
	public Long getPropertyid() {
		return propertyid;
	}
	public void setPropertyid(Long propertyid) {
		this.propertyid = propertyid;
	}
	public String getCodevalue() {
		return codevalue;
	}
	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}
	public String getPropertyname() {
		return propertyname;
	}
	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}
	public String getPropertyvalue() {
		return propertyvalue;
	}
	public void setPropertyvalue(String propertyvalue) {
		this.propertyvalue = propertyvalue;
	}
	public String getPropertyunit() {
		return propertyunit;
	}
	public void setPropertyunit(String propertyunit) {
		this.propertyunit = propertyunit;
	}
	/**
	 * @return the iD
	 */
	public Long getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(Long iD) {
		ID = iD;
	} 
	
}
