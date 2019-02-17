package com.baiyi.cmall.request.manager;

import java.util.List;

import com.baiyi.cmall.activities.main.mall.pop.entity.Qcm;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;

/**
 * 商城参数管理
 * 
 * @author lizl
 */
public class MallManager {

	/**
	 * key String 检索词 cy String 类别 bd String 品牌 on String 产地 dy String 交割地 pr
	 * String 价格 ps Integer 每页显示条数 pi Integer 页码 sf int 排序字段 st int 排序类型 pro
	 * String 其他属性(属性)
	 */

	// 销量
	private static String sale = "";
	// 价格
	private static String price = "";
	// 搜索
	private static String search = "";

	/**
	 * 产品的分类列表
	 * 
	 * @param cc
	 * @param pi
	 * @param ps
	 * @param state
	 *            2: 销量
	 * @param qcms
	 * @return
	 */
	public static String getCategoryListData(String cc, int state, List<Qcm> qcms, int pi, int ps) {
		//
		StringBuilder builder = new StringBuilder();
		if (!Utils.isStringEmpty(cc)) {
//			builder.append("cy").append("=").append(cc).append("&");
		}
		builder.append("pi").append("=").append(pi).append("&");
		builder.append("ps").append("=").append(ps).append("&");

		// 默认销量
		if (TextViewUtil.isStringEmpty(sale)) {
			StringBuilder saleBuilder = new StringBuilder();
			saleBuilder.append("sf").append("=").append(4).append("&");
			saleBuilder.append("st").append("=").append(0).append("&");
			sale = saleBuilder.toString();
		}

		// 默认价格
		if (TextViewUtil.isStringEmpty(price)) {
			StringBuilder priceBuilder = new StringBuilder();
			priceBuilder.append("pr").append("=").append(4).append("&");
			price = priceBuilder.toString();
		}

		switch (state) {
		case 1:// 搜索
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("key").append("=").append(cc).append("&");
			search = sBuilder.toString();
			break;
		case 2:// 销量
			StringBuilder saleB = new StringBuilder();
			saleB.append("sf").append("=").append(cc).append("&");
			saleB.append("st").append("=").append(0).append("&");
			sale = saleB.toString();
			break;
		case 3:// 价格
				// 默认价格
			StringBuilder priceB = new StringBuilder();
			priceB.append("pr").append("=").append(cc).append("&");
			price = priceB.toString();
			break;

		case -1:// 复位
			break;
		}

		builder.append(sale)/*.append(price)*/.append(search);

		// 筛选
		if (qcms != null && qcms.size() > 0)

		{
			for (Qcm qcm : qcms) {
				if (!qcm.getSubName().equals("全部")) {
					// 得到过滤条件
					String fif = getFif(qcm.getQn());
					builder.append(fif).append("=").append(qcm.getBd()).append("&");
				}
			}
		}

		return builder.toString();
	}

	private static String getFif(String qn) {

		if ("类别".equals(qn)) {
			return "cy";
		} else if ("品牌".equals(qn)) {
			return "bd";
		} else if ("产地".equals(qn)) {
			return "on";
		} else if ("交割地".equals(qn)) {
			return "dy";
		} else if ("价格".equals(qn)) {
			return "pr";
		}
		return "pro";
	}

	/**
	 * 商品分页数据
	 * 
	 * @param pi
	 * @param ps
	 * @return
	 */
	public static String getListData(int pi, int ps) {
		//
		StringBuilder builder = new StringBuilder();

		builder.append("pi").append("=").append(pi).append("&");
		builder.append("ps").append("=").append(ps);
		return builder.toString();
	}

	/**
	 * 关注商家
	 * 
	 * @param id
	 * @return
	 */
	public static String getAddCompanyData(int id) {
		StringBuilder builder = new StringBuilder();
		builder.append("targetid").append("=").append(id);
		return builder.toString();
	}

}
