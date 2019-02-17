/**
 * 
 */
package com.baiyi.jj.app.entity.article;

import java.io.Serializable;

/**
 * @author tangkun
 *
 */
public class ArticleRecommend implements Serializable{
	//推荐索引
	private int index;
	//推荐版本
	private int version;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

}
