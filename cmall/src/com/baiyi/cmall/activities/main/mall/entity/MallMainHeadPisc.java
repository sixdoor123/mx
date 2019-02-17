package com.baiyi.cmall.activities.main.mall.entity;

/**
 * 商城主页-广告图片
 * @author tangkun
 *
 */
public class MallMainHeadPisc {
	//跳转地址
	private String url;
	//图片地址
	private String fp;
	//不知道
	private String no;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFp() {
		return fp;
	}
	public void setFp(String fp) {
		this.fp = fp;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MallMainHeadPisc [url=" + url + ", fp=" + fp + ", no=" + no + "]";
	}

}
