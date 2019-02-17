package com.baiyi.cmall.activities.user.merchant.order.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.model.Odm;
import com.baiyi.cmall.model.Pdm;
import com.baiyi.cmall.model.RequestNetResultInfo;

import android.text.GetChars;

/**
 *
 * @author sunxy
 */
public class MerchantOrderManager {

	/**
	 * 意向详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static OrderEntity parseIntebtionDetail(Object arg1) {
		OrderEntity entity = new OrderEntity();
		try {
			JSONArray array = (JSONArray) arg1;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray data = jsonObject.getJSONArray("data");
				for (int j = 0; j < data.length(); j++) {
					JSONObject object = data.getJSONObject(j);

					entity.setPhone(object.getString("phone"));
					// 收货人名称
					entity.setReceivername(object.getString("receivername"));
					// 品牌
					entity.setBrandname(object.getString("brandname"));
					// 订单ID
					entity.setId(object.getString("id"));
					// 分类
					entity.setCategoryName(object.getString("categoryName"));
					// 标题
					entity.setTitle(object.getString("title"));
					// 价格
					entity.setPrice(object.getString("price"));
					// 发票类型名称
					entity.setInvoicetypename(object.getString("invoicetypename"));
					// 地址
					entity.setAddress(object.getString("receiveraddress"));
					// 库存
					entity.setInventory(object.getString("inventory"));
					// 发票内容
					entity.setContext(object.getString("context"));
					entity.setEare(object.getString("cityname"));
					// 预付
					entity.setPrepayment(object.getString("prepayment"));
					// 名称
					entity.setOfferName(object.getString("offerName"));
					// 公司名称
					entity.setCompanyname(object.getString("purcompanyname"));
					entity.setOrderState(object.getInt("orderstate"));
					entity.setIntentionid(object.getString("intentionid"));
					// 资源总额
					entity.setResamount(object.getString("resamount"));
					entity.setBinaryvalue(object.getString("binaryvalue"));
				}
			}

			return entity;

		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 产品订单详情
	 * 
	 * @param arg1
	 */
	@SuppressWarnings("rawtypes")
	public static RequestNetResultInfo parseProductDetail(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		@SuppressWarnings("rawtypes")
		RequestNetResultInfo info = new RequestNetResultInfo<>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				info.setMsg(jsonObject.getString("msg"));
				info.setStatus(jsonObject.getInt("status"));

				JSONObject data = jsonObject.getJSONObject("data");

				// 公共部分
				Odm odm = new Odm();
				odm.setId(data.getString("id"));
				odm.setIc(data.getString("ic"));
				odm.setOs(data.getInt("os"));
				odm.setRp(data.getString("rp"));
				odm.setIt(data.getString("it"));
				odm.setCn(data.getString("cn"));
				odm.setIi(data.getString("ii"));
				odm.setAd(data.getString("ad"));
				odm.setPh(data.getString("ph"));
				odm.setOi(data.getString("oi"));
				odm.setDq(data.getString("dq"));

				List<Pdm> pdms = new ArrayList<Pdm>();
				JSONArray pdml = data.getJSONArray("pdml");
				for (int j = 0; j < pdml.length(); j++) {
					JSONObject object = pdml.getJSONObject(j);

					Pdm pdm = new Pdm();
					pdm.setBn(object.getString("bn"));
					pdm.setId(object.getString("id"));
					pdm.setCo(object.getString("co"));
					pdm.setAm(object.getString("am"));
					pdm.setPurl(object.getString("purl"));
					pdm.setPn(object.getString("pn"));
					pdm.setPr(object.getString("pr"));

					pdm.setGgl(object.getJSONArray("ggl"));

					pdms.add(pdm);
				}

				odm.setPdms(pdms);

				info.setOdm(odm);

			}

			return info;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return info;
	}

}
