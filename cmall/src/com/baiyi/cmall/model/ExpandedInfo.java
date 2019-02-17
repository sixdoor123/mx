package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sunxy
 */
public class ExpandedInfo implements Serializable{
	// 产品描述条目值
	private List<Rpv> rpvs = null;

	public List<Rpv> getRpvs() {
		return rpvs;
	}

	public void setRpvs(List<Rpv> rpvs) {
		this.rpvs = rpvs;
	}
}
