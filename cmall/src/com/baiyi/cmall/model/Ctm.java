package com.baiyi.cmall.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 评论ctm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:42:13
 *
 */
public class Ctm implements Serializable {

	//用户头像（图片路径）
	private String uh = null;
	//用户名称
	private String un = null; 
	//评价时间
	private Date ct = null;
	//星级
	private int sl = 0;
	//规格
	private String rp = null;
	//购买数量
	private int pc = 0;
	//购买时间
	private Date pt = null;
	//评价内容
	private String cc= null;
	
	
}
