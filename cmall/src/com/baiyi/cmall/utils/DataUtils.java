package com.baiyi.cmall.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.IntentionTypeInfo;
import com.baiyi.cmall.model.Pbi;

/**
 * 
 * 处理数据源的工具类
 * 
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-15 上午10:26:17
 */
public class DataUtils {

	/**
	 * 集合字符串(详细内容)去重
	 * 
	 * @param list
	 */
	public static ArrayList<String> removeDuplicateObj(ArrayList<String> list) {

		ArrayList<String> tempList = new ArrayList<String>();

		for (int k = 0; k < list.size(); k++) {
			if (!tempList.contains(list.get(k))) {
				tempList.add(list.get(k));

			}
		}
		return tempList;
	}

	/**
	 * 得到每一类型的集合
	 * 
	 * @param content
	 * @param datas2
	 * @return
	 */
	public static ArrayList<GoodsSourceInfo> getRelevantSource(String content, ArrayList<GoodsSourceInfo> datas2) {
		ArrayList<GoodsSourceInfo> infos = new ArrayList<GoodsSourceInfo>();
		for (int i = 0; i < datas2.size(); i++) {
			if ((content + "").equals(datas2.get(i).getGoodSPurchaseContent())) {
				infos.add(datas2.get(i));
			}
		}

		return infos;
	}

	/**
	 * 委托供应中的采购信息
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<String> getShowList(GoodsSourceInfo info) {

		ArrayList<String> supplyInfos = new ArrayList<String>();

		if (null != info) {

			if (!Utils.isStringEmpty(info.getGoodSMerchant())) {
				supplyInfos.add("供应商名称 : " + info.getGoodSMerchant());
			}
			if (!Utils.isStringEmpty(info.getGoodSCategory())) {
				supplyInfos.add("分类 : " + info.getGoodSCategory());
			}
			if (!Utils.isStringEmpty(info.getGoodSName())) {
				supplyInfos.add("名称 : " + info.getGoodSName());
			}
			if (!Utils.isStringEmpty(info.getGoodSBrand())) {
				supplyInfos.add("品牌 : " + info.getGoodSBrand());
			}
			if (!Utils.isStringEmpty(info.getGoodSPlace())) {
				supplyInfos.add("产地: " + info.getGoodSPlace());
			}
			if (!Utils.isStringEmpty(info.getGoodSWeight())) {
				supplyInfos.add("库存 :  " + info.getGoodSWeight() + "吨");
			}
			if (!Utils.isStringEmpty(info.getGoodSDelivery())) {
				supplyInfos.add("交割地  : " + info.getGoodSDelivery());
			}
			if (!Utils.isStringEmpty(info.getGoodSPrePrice())) {
				supplyInfos.add("价格 : "+ Utils.twoDecimals(info.getGoodSPrePrice()) + "元/吨");
			}
			if (!Utils.isStringEmpty(info.getPrepayment())) {
				supplyInfos.add("预付金额 : " + Utils.twoDecimals(info.getPrepayment())+ "元");
			}
			if (!Utils.isStringEmpty(info.getGoodSPutTime())) {
				supplyInfos.add("发布时间 : " + info.getGoodSPutTime());
			}
		}

		return supplyInfos;
	}

	/**
	 * 委托采购中的供应信息
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<String> getProviderShowList(GoodsSourceInfo info) {

		ArrayList<String> supplyInfos = new ArrayList<String>();
		if (null != info) {
			supplyInfos.add("采购商 : " + TextViewUtil.getEditString(info.getGoodSMerchant()));
			supplyInfos.add("名称 : " + TextViewUtil.getEditString(info.getGoodSName()));
			supplyInfos.add("分类 : " + TextViewUtil.getEditString(info.getGoodSCategory()));
			supplyInfos.add("品牌 : " + TextViewUtil.getEditString(info.getGoodSBrand()));
			supplyInfos.add("产地 : " + TextViewUtil.getEditString(info.getGoodSArea()));
			supplyInfos.add("数量 : " + TextViewUtil.getEditString(info.getGoodSWeight()) + "吨");
			supplyInfos.add("交 割 地  : " + TextViewUtil.getEditString(info.getGoodSDelivery()));
			supplyInfos.add("价格 : " + TextViewUtil.getEditString(info.getGoodSpriceInterval()) + "元/吨");
			supplyInfos.add("预付金额 : " + TextViewUtil.getEditString(info.getGoodSPrePrice()) + "元");
			supplyInfos.add("发布时间 : " + TextViewUtil.getEditString(info.getGoodSPutTime()));
		}
		return supplyInfos;
	}

	/**
	 * 供应意向列表 类型列表数据源
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getTypeArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("全部", String.valueOf(3)));
		datas.add(new IntentionTypeInfo("我收到的采购意向", "1"));
		datas.add(new IntentionTypeInfo("我发出的供应意向", "2"));

		return datas;
	}

	/**
	 * 供应意向列表 状态列表数据源
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getStateArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("全部", "-1"));
		datas.add(new IntentionTypeInfo("创建", "1"));
		datas.add(new IntentionTypeInfo("交流中", "2"));
		datas.add(new IntentionTypeInfo("已拒绝", "3"));
		datas.add(new IntentionTypeInfo("已取消", "4"));
		datas.add(new IntentionTypeInfo("已下单", "5"));
		datas.add(new IntentionTypeInfo("采购商已删除", "6"));

		return datas;
	}

	/**
	 * 采购意向列表 状态列表数据源
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getPurStateArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("全部", "-1"));
		datas.add(new IntentionTypeInfo("创建", "1"));
		datas.add(new IntentionTypeInfo("交流中", "2"));
		datas.add(new IntentionTypeInfo("已拒绝", "3"));
		datas.add(new IntentionTypeInfo("已取消", "4"));
		datas.add(new IntentionTypeInfo("已下单", "5"));
		datas.add(new IntentionTypeInfo("供应商已删除", "6"));

		return datas;
	}

	/**
	 * 采购意向列表 类型列表数据源
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getPurTypeArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("全部", "3"));
		datas.add(new IntentionTypeInfo("我发出的采购意向", "1"));
		datas.add(new IntentionTypeInfo("我收到的供应意向", "2"));

		return datas;
	}

	/*
	 * 根据意向单的状态，获取状态文字 1. 已创建 2. 交流中 3. 已拒绝 5. 已下单
	 */
	public static String getStateText(int state) {
		String stateString = null;
		switch (state) {
		case 1:
			stateString = "待回复";
			break;
		case 2:
			stateString = "交流中";
			break;
		case 3:
			stateString = "已拒绝";
			break;
		case 5:
			stateString = "已下单";
			break;

		}
		return stateString;

	}

	/**
	 * 得到品牌类型的属性所对应的所有属性
	 * 
	 * @param string
	 * @param datas
	 * @return
	 */
	public static ArrayList<IntentionDetailStandardInfo> getStandardRelevantSource(String string,
			ArrayList<IntentionDetailStandardInfo> datas) {
		ArrayList<IntentionDetailStandardInfo> infos = new ArrayList<IntentionDetailStandardInfo>();
		for (int i = 0; i < datas.size(); i++) {
			if ((string + "").equals(datas.get(i).getCodevalue())) {
				infos.add(datas.get(i));
			}
		}

		return infos;
	}

	/**
	 * 我是采购商——获得供应商具体信息
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getOfferDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		/**
		 * info.setGoodSID(modelData.getString("id"));// 供應ID
		 * info.setGoodSDetails(modelData.getString("offerDetail"));// 供应详情
		 * info.setGoodSpriceInterval(modelData.getString("price"));// 货物价格（单价）
		 * info.setGoodSMerchant(modelData .getString("purchasecompanyname"));//
		 * 采购商名称 info.setGoodSName(modelData.getString("purchasename"));// 名称
		 * info.setGoodSWeight(modelData.getInt("inventory"));// 数量（重量）
		 * info.setGoodSPrePrice(Float.parseFloat(modelData
		 * .getString("prepayment")));// 预付费
		 * info.setGoodSDelivery(modelData.getString("deliveryPlaceName"));//
		 * 交割地
		 */
		IndutryArgumentInfo argumentInfo0 = new IndutryArgumentInfo();
		argumentInfo0.setArgNmae("名称: ");
		argumentInfo0.setArgValue(info.getGoodSContent());
		datas.add(argumentInfo0);

		IndutryArgumentInfo argumentInfo1 = new IndutryArgumentInfo();
		argumentInfo1.setArgNmae("分类: ");
		argumentInfo1.setArgValue(info.getGoodSCategory());
		datas.add(argumentInfo1);

		IndutryArgumentInfo argumentInfo2 = new IndutryArgumentInfo();
		argumentInfo2.setArgNmae("品牌:");
		argumentInfo2.setArgValue(info.getGoodSBrand());
		datas.add(argumentInfo2);

		IndutryArgumentInfo argumentInfo3 = new IndutryArgumentInfo();
		argumentInfo3.setArgNmae("产地:");
		argumentInfo3.setArgValue(info.getGoodSArea());
		datas.add(argumentInfo3);

		IndutryArgumentInfo argumentInfo4 = new IndutryArgumentInfo();
		argumentInfo4.setArgNmae("库存:");
		argumentInfo4.setArgValue(info.getGoodSWeight() + "");
		datas.add(argumentInfo4);

		IndutryArgumentInfo argumentInfo5 = new IndutryArgumentInfo();
		argumentInfo5.setArgNmae("价格:");
		argumentInfo5.setArgValue(info.getGoodSpriceInterval() + "元/吨");
		datas.add(argumentInfo5);

		IndutryArgumentInfo argumentInfo6 = new IndutryArgumentInfo();
		argumentInfo6.setArgNmae("预付金额:");
		argumentInfo6.setArgValue(info.getGoodSPrePrice() + "元");
		datas.add(argumentInfo6);

		IndutryArgumentInfo argumentInfo7 = new IndutryArgumentInfo();
		argumentInfo7.setArgNmae("交割地:");
		argumentInfo7.setArgValue(info.getGoodSDelivery());
		datas.add(argumentInfo7);

		IndutryArgumentInfo argumentInfo8 = new IndutryArgumentInfo();
		argumentInfo8.setArgNmae("详细内容:");
		argumentInfo8.setArgValue(info.getGoodSDetails());
		datas.add(argumentInfo8);

		return datas;
	}

	/**
	 * 我是采购商——获得采购具体信息
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getPurDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		IndutryArgumentInfo argumentInfo0 = new IndutryArgumentInfo();
		argumentInfo0.setArgNmae("名称: ");
		argumentInfo0.setArgValue(info.getGoodSName());
		datas.add(argumentInfo0);

		IndutryArgumentInfo argumentInfo1 = new IndutryArgumentInfo();
		argumentInfo1.setArgNmae("分类: ");
		argumentInfo1.setArgValue(info.getGoodSCategory());
		datas.add(argumentInfo1);

		IndutryArgumentInfo argumentInfo2 = new IndutryArgumentInfo();
		argumentInfo2.setArgNmae("品牌:");
		argumentInfo2.setArgValue(info.getGoodSBrand());
		datas.add(argumentInfo2);

		IndutryArgumentInfo argumentInfo3 = new IndutryArgumentInfo();
		argumentInfo3.setArgNmae("产地:");
		argumentInfo3.setArgValue(info.getGoodSPlace());
		datas.add(argumentInfo3);

		IndutryArgumentInfo argumentInfo4 = new IndutryArgumentInfo();
		argumentInfo4.setArgNmae("数量:");
		argumentInfo4.setArgValue(info.getGoodSWeight() + "");
		datas.add(argumentInfo4);

		IndutryArgumentInfo argumentInfo5 = new IndutryArgumentInfo();
		argumentInfo5.setArgNmae("价格:");
		argumentInfo5.setArgValue(info.getGoodSpriceInterval() + "元/吨");
		datas.add(argumentInfo5);

		IndutryArgumentInfo argumentInfo6 = new IndutryArgumentInfo();
		argumentInfo6.setArgNmae("预付金额:");
		argumentInfo6.setArgValue(info.getGoodSPrePrice() + "元");
		datas.add(argumentInfo6);

		IndutryArgumentInfo argumentInfo7 = new IndutryArgumentInfo();
		argumentInfo7.setArgNmae("交割地:");
		argumentInfo7.setArgValue(info.getGoodSDelivery());
		datas.add(argumentInfo7);

		IndutryArgumentInfo argumentInfo8 = new IndutryArgumentInfo();
		argumentInfo8.setArgNmae("详细内容:");
		argumentInfo8.setArgValue(info.getGoodSPurchaseContent());
		datas.add(argumentInfo8);

		return datas;
	}

	/**
	 * 首页（系统）——获得采购具体信息
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getSystemPurDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		IndutryArgumentInfo argumentInfo0 = new IndutryArgumentInfo();
		argumentInfo0.setArgNmae("采购商: ");
		argumentInfo0.setArgValue(info.getGoodSMerchant());
		datas.add(argumentInfo0);

		IndutryArgumentInfo argumentInfo1 = new IndutryArgumentInfo();
		argumentInfo1.setArgNmae("名称: ");
		argumentInfo1.setArgValue(info.getGoodSName());
		datas.add(argumentInfo1);

		IndutryArgumentInfo argumentInfo2 = new IndutryArgumentInfo();
		argumentInfo2.setArgNmae("分类: ");
		argumentInfo2.setArgValue(info.getGoodSCategory());
		datas.add(argumentInfo2);

		IndutryArgumentInfo argumentInfo3 = new IndutryArgumentInfo();
		argumentInfo3.setArgNmae("品牌:");
		argumentInfo3.setArgValue(info.getGoodSBrand());
		datas.add(argumentInfo3);

		IndutryArgumentInfo argumentInfo4 = new IndutryArgumentInfo();
		argumentInfo4.setArgNmae("产地:");
		argumentInfo4.setArgValue(info.getGoodSArea());
		datas.add(argumentInfo4);

		IndutryArgumentInfo argumentInfo5 = new IndutryArgumentInfo();
		argumentInfo5.setArgNmae("数量:");
		argumentInfo5.setArgValue(info.getGoodSWeight() + "吨");
		datas.add(argumentInfo5);

		IndutryArgumentInfo argumentInfo6 = new IndutryArgumentInfo();
		argumentInfo6.setArgNmae("交割地:");
		argumentInfo6.setArgValue(info.getGoodSDelivery());
		datas.add(argumentInfo6);

		IndutryArgumentInfo argumentInfo7 = new IndutryArgumentInfo();
		argumentInfo7.setArgNmae("价格:");
		argumentInfo7.setArgValue(info.getGoodSpriceInterval() + "元/吨");
		datas.add(argumentInfo7);

		IndutryArgumentInfo argumentInfo8 = new IndutryArgumentInfo();
		argumentInfo8.setArgNmae("预付金额:");
		argumentInfo8.setArgValue(info.getGoodSPrePrice() + "元");
		datas.add(argumentInfo8);

		IndutryArgumentInfo argumentInfo9 = new IndutryArgumentInfo();
		argumentInfo9.setArgNmae("发布时间:");
		argumentInfo9.setArgValue(info.getGoodSPutTime() + "");
		datas.add(argumentInfo9);

		IndutryArgumentInfo argumentInfo10 = new IndutryArgumentInfo();
		argumentInfo10.setArgNmae("详细内容:");
		argumentInfo10.setArgValue(info.getGoodSPurchaseContent());
		datas.add(argumentInfo10);

		return datas;
	}

	/**
	 * 我的委托-委托采购-详情
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getDelegationPurDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		if (null != info) {
			datas.add(new IndutryArgumentInfo("标题:", info.getGoodSTitle()));
			datas.add(new IndutryArgumentInfo("公司名称:", info.getGoodSMerchant()));
			datas.add(new IndutryArgumentInfo("联系人:", info.getGoodSContactPerson()));
			datas.add(new IndutryArgumentInfo("联系方式:", info.getGoodSContactWay()));
			datas.add(new IndutryArgumentInfo("需求描述:", info.getGoodSContent()));
			datas.add(new IndutryArgumentInfo("联系人城市:", info.getGoodSArea()));
			String address = info.getAddress();
			if (address == null || "null".equals(address)) {
				address = "";
			}
			datas.add(new IndutryArgumentInfo("联系人地址:", address));
			String category = info.getGoodSCategory();
			if (category == null || "null".equals(category)) {
				category = "";
			}

			datas.add(new IndutryArgumentInfo("分类:", category));
			datas.add(new IndutryArgumentInfo("交割地:", info.getGoodSDelivery()));
			datas.add(new IndutryArgumentInfo("产地:", info.getStartAddress()));

			datas.add(new IndutryArgumentInfo("品牌:", info.getGoodSBrand()));
			// 数量显示的数据分析
			datas.add(new IndutryArgumentInfo("数量:",
					("0".equals(info.getGoodSWeight()) || "null".equals(info.getGoodSWeight())) ? ""
							: info.getGoodSWeight() + "吨"));
			// 价格显示的数据分析
			String price = Utils.twoDecimals(info.getGoodSPrePrice());
			datas.add(new IndutryArgumentInfo("价格:", "0".equals(price) || "0.00".equals(price) ? "" : price + "元/吨"));

		}
		return datas;
	}

	/**
	 * 我的委托物流-物流详情
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getDelegationLogisticsDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();
		if (null != info) {

			datas.add(new IndutryArgumentInfo("标题:", info.getTitle()));
			datas.add(new IndutryArgumentInfo("公司名称:", info.getGoodSMerchant()));
			datas.add(new IndutryArgumentInfo("联系人:", info.getGoodSContactPerson()));
			datas.add(new IndutryArgumentInfo("联系方式:", info.getGoodSContactWay()));
			datas.add(new IndutryArgumentInfo("需求描述:", info.getGoodSDetails()));
			datas.add(new IndutryArgumentInfo("始发城市:", info.getStartAddress()));
			datas.add(new IndutryArgumentInfo("始发地址:", info.getGoodSStartCity()));
			datas.add(new IndutryArgumentInfo("目的城市:", info.getEndAddress()));
			datas.add(new IndutryArgumentInfo("目的地址:", info.getGoodSEndCity()));
			datas.add(new IndutryArgumentInfo("数量:",
					("0".equals(info.getGoodSWeight()) || "null".equals(info.getGoodSWeight())) ? ""
							: info.getGoodSWeight() + "吨"));
			String type = info.getDeliverytypename();
			if ("null".equals(type) || null == type) {
				type = "";
			}
			datas.add(new IndutryArgumentInfo("货运类型:", type));
			String pack = info.getPacktypename();
			if ("null".equals(pack) || null == pack) {
				pack = "";
			}
			datas.add(new IndutryArgumentInfo("包装方式:", pack));
			// 开始时间
			long startTime = Utils.getLongTime1(info.getGoodSStartTime());
			// 结束时间
			long endTime = Utils.getLongTime1(info.getGoodSEndTime());
			datas.add(new IndutryArgumentInfo("始发时间:", startTime <= 0 ? "" : info.getGoodSStartTime()));
			datas.add(new IndutryArgumentInfo("到达时间:", endTime <= 0 ? "" : info.getGoodSEndTime()));

		}

		return datas;
	}

	/**
	 * 关注供应、关注采购详情
	 * 
	 * @param info
	 * @param state
	 *            1:关注供应 0：关注采购
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getAttentionPurProDetail(GoodsSourceInfo info, int state) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		if (null != info) {
			if (2 == state) {
				datas.add(new IndutryArgumentInfo("供应商:", TextViewUtil.getEditString(info.getCompanyName())));
				datas.add(new IndutryArgumentInfo("供应商类型:", TextViewUtil.getEditString(info.getCompanytypename())));
				datas.add(new IndutryArgumentInfo("城市:", TextViewUtil.getEditString(info.getCity())));
				datas.add(new IndutryArgumentInfo("联系人:", TextViewUtil.getEditString(info.getGoodSContactPerson())));
				datas.add(new IndutryArgumentInfo("联系电话:", TextViewUtil.getEditString(info.getGoodSContactWay())));
				datas.add(new IndutryArgumentInfo("邮箱:", TextViewUtil.getEditString(info.getEmail())));
				datas.add(new IndutryArgumentInfo("详细地址:", TextViewUtil.getEditString(info.getAddress())));
				datas.add(new IndutryArgumentInfo("供应商简介:", TextViewUtil.getEditString(info.getGoodSDetails())));
			} else {
				if (1 == state) {
					datas.add(new IndutryArgumentInfo("名称:", TextViewUtil.getEditString(info.getGoodSName())));
				} else {
					datas.add(new IndutryArgumentInfo("名称:", TextViewUtil.getEditString(info.getGoodSName())));
				}
				datas.add(new IndutryArgumentInfo("分类:", info.getGoodSCategory()));
				datas.add(new IndutryArgumentInfo("产地:", TextViewUtil.getEditString(info.getGoodSPlace())));
				datas.add(new IndutryArgumentInfo("品牌:", TextViewUtil.getEditString(info.getGoodSBrand())));
				datas.add(new IndutryArgumentInfo("数量:", info.getGoodSWeight() + "吨"));
				String price = Utils.twoDecimals(info.getGoodSPrePrice());
				if (price.startsWith(".")) {
					price = "0" + price;
				}
				datas.add(new IndutryArgumentInfo("价格:", price + "元/吨"));
				datas.add(new IndutryArgumentInfo("预付金额:", Utils.twoDecimals(info.getPrepayment()) + "元"));
				datas.add(new IndutryArgumentInfo("交割地:", TextViewUtil.getEditString(info.getGoodSDelivery())));
				datas.add(new IndutryArgumentInfo("发布时间:", TextViewUtil.getEditString(info.getGoodSPutTime())));
				datas.add(new IndutryArgumentInfo("备注详情:", TextViewUtil.getEditString(info.getGoodSDetails())));

			}
			return datas;
		}

		return null;
	}

	/**
	 * 分享名称
	 * 
	 * @return
	 */
	public static ArrayList<String> getShareName() {
		ArrayList<String> datas = new ArrayList<String>();
		datas.add("微信");
		datas.add("微信朋友圈");
		datas.add("手机QQ");
		datas.add("QQ空间");
		datas.add("邮件");
		datas.add("复制链接");
		return datas;
	}

	/**
	 * 分享图标
	 * 
	 * @return
	 */
	public static ArrayList<Integer> getShareImgs() {
		ArrayList<Integer> datas = new ArrayList<Integer>();

		datas.add(R.drawable.share_weixin2x);
		datas.add(R.drawable.share_weixin_circle_friends2x);
		datas.add(R.drawable.share_tel_qq2x);
		datas.add(R.drawable.share_qqspace2x);
		datas.add(R.drawable.share_e_mail2x);
		datas.add(R.drawable.share_link2x);

		return datas;
	}

	/**
	 * 关注的采购商详情
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getAttentionBuyerDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> infos = new ArrayList<IndutryArgumentInfo>();

		infos.add(new IndutryArgumentInfo("采购商: ", TextViewUtil.getEditString(info.getUserName())));
		infos.add(new IndutryArgumentInfo("采购者类型: ", TextViewUtil.getEditString(info.getCompanytypename())));
		infos.add(new IndutryArgumentInfo("联系方式: ", TextViewUtil.getEditString(info.getGoodSContactWay())));
		infos.add(new IndutryArgumentInfo("邮箱: ", TextViewUtil.getEditString(info.getEmail())));
		infos.add(new IndutryArgumentInfo("地址: ", TextViewUtil.getEditString(info.getAddress())));
		return infos;
	}

	public static List<IndutryArgumentInfo> getPageInfo(Pbi pbi) {
		ArrayList<IndutryArgumentInfo> infos = new ArrayList<IndutryArgumentInfo>();

		infos.add(new IndutryArgumentInfo("商品名称: ", TextViewUtil.getEditString(pbi.getPn())));
		infos.add(new IndutryArgumentInfo("副名称: ", TextViewUtil.getEditString(pbi.getSn())));
		infos.add(new IndutryArgumentInfo("别名: ", TextViewUtil.getEditString(pbi.getBn())));
		infos.add(new IndutryArgumentInfo("价格: ", TextViewUtil.getEditString(pbi.getPp() + "元/件")));
		infos.add(new IndutryArgumentInfo("状态: ", TextViewUtil.getEditString(getStateName(pbi.getPs()))));
		infos.add(new IndutryArgumentInfo("到期自动开始: ", TextViewUtil.getEditString(pbi.getDqk())));
		infos.add(new IndutryArgumentInfo("到期自动结束: ", TextViewUtil.getEditString(pbi.getDqj())));
		infos.add(new IndutryArgumentInfo("创建时间: ", TextViewUtil.getEditString(pbi.getCd())));
		infos.add(new IndutryArgumentInfo("开始时间: ", TextViewUtil.getEditString(pbi.getSd())));
		infos.add(new IndutryArgumentInfo("结束时间: ", TextViewUtil.getEditString(pbi.getJd())));

		infos.add(new IndutryArgumentInfo("规格参数: ", TextViewUtil.getEditString(fromJSONArray(pbi.getGgl()))));
		
		infos.add(new IndutryArgumentInfo("配送支持: ", TextViewUtil.getEditString(fromJSONArray(pbi.getPsz()))));
		infos.add(new IndutryArgumentInfo("发票支持: ", TextViewUtil.getEditString(fromJSONArray(pbi.getIzc()))));
		infos.add(new IndutryArgumentInfo("付款方式: ", TextViewUtil.getEditString(fromJSONArray(pbi.getPmz()))));

		return infos;
	}

	private static String fromJSONArray(JSONArray array) {

		StringBuilder builder = new StringBuilder();
		try {
			for (int i = 0; i < array.length(); i++) {

				builder.append(array.get(i)).append(" ");
				
			}
			
			return builder.toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
	}

	private static String getStateName(int ps) {

		switch (ps) {
		case 0:
			return "未发布";
		case 1:
			return "发布";
		case -1:
			return "撤销";

		}
		return "";
	}
}
