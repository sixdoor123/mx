/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商城-详情-评论
 * 
 * @author tangkun
 * 
 */
public class MallDetailCommentEntity implements Serializable {

	// id
	private int id;

	// 全部评价
	private int ac;

	// 好评
	private int gc;

	// 差评
	private int bc;

	// 晒图
	private int rep;

	// 评价列表
	private ArrayList<CtmlEntity> ctmlList;

	public MallDetailCommentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MallDetailCommentEntity(int id, int ac, int gc, int bc,
			ArrayList<CtmlEntity> ctmlList) {
		super();
		this.id = id;
		this.ac = ac;
		this.gc = gc;
		this.bc = bc;
		this.ctmlList = ctmlList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAc() {
		return ac;
	}

	public void setAc(int ac) {
		this.ac = ac;
	}

	public int getGc() {
		return gc;
	}

	public void setGc(int gc) {
		this.gc = gc;
	}

	public int getBc() {
		return bc;
	}

	public void setBc(int bc) {
		this.bc = bc;
	}

	public int getRep() {
		return rep;
	}

	public void setRep(int rep) {
		this.rep = rep;
	}

	public ArrayList<CtmlEntity> getCtmlList() {
		return ctmlList;
	}

	public void setCtmlList(ArrayList<CtmlEntity> ctmlList) {
		this.ctmlList = ctmlList;
	}

}
