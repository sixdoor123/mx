package com.baiyi.cmall.activities.main.mall.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.DTDHandler;

import com.baiyi.cmall.activities.main.mall.entity.CtmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailCommentEntity;
import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.model.Bim;
import com.baiyi.cmall.model.Blm;
import com.baiyi.cmall.model.Cbi;
import com.baiyi.cmall.model.Pbi;
import com.baiyi.cmall.model.Pdi;
import com.baiyi.cmall.model.Pim;
import com.baiyi.cmall.model.Pvm;
import com.baiyi.cmall.model.RequestNetResultInfo;
import com.baiyi.cmall.model.Rpv;
import com.baiyi.cmall.utils.JsonParseBase;
import com.tencent.map.b.j;

import android.util.Log;

/**
 *
 * @author sunxy
 */
public class ProductDetailManager extends JsonParseBase {

	/**
	 * 解析数据-评论
	 * 
	 * @param arg1
	 */
	public static MallDetailCommentEntity parseCommentPageDatas(Object arg1) {

		MallDetailCommentEntity mallDetailCommentEntity = null;

		if (arg1 == null) {
			return null;
		}
		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject data = jsonObject.getJSONObject("data");
				if (data == null) {
					return null;
				}

				// 添加总数
				int total = getIntNodeValue(data, "tt");
				TotalUtils.getIntence().setTotal(total);

				mallDetailCommentEntity = new MallDetailCommentEntity();
				JSONArray li = data.getJSONArray("li");
				for (int j = 0; j < li.length(); j++) {
					JSONObject object = li.getJSONObject(j);
					mallDetailCommentEntity.setId(object.getInt("id"));
					mallDetailCommentEntity.setAc(object.getInt("ac"));
					mallDetailCommentEntity.setGc(object.getInt("gc"));
					mallDetailCommentEntity.setBc(object.getInt("bc"));
					mallDetailCommentEntity.setRep(object.getInt("pic"));

					JSONArray ctml = object.getJSONArray("ctml");

					ArrayList<CtmlEntity> ctmlEntities = new ArrayList<CtmlEntity>();
					for (int k = 0; k < ctml.length(); k++) {
						JSONObject ctmlObject = ctml.getJSONObject(k);
						CtmlEntity ctmlEntity = new CtmlEntity();
						ctmlEntity.setId(ctmlObject.getString("id"));
						ctmlEntity.setUh(ctmlObject.getString("uh"));
						ctmlEntity.setUn(ctmlObject.getString("un"));
						ctmlEntity.setCt(ctmlObject.getString("ct"));
						ctmlEntity.setSl(ctmlObject.getInt("sl"));
						ctmlEntity.setRp(ctmlObject.getString("rp"));
						ctmlEntity.setPc(ctmlObject.getString("pc"));
						ctmlEntity.setPt(ctmlObject.getLong("pt"));
						ctmlEntity.setCc(ctmlObject.getString("cc"));
						ctmlEntities.add(ctmlEntity);
					}

					mallDetailCommentEntity.setCtmlList(ctmlEntities);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mallDetailCommentEntity;
	}

	/**
	 * 详情
	 * 
	 * @param arg1
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static RequestNetResultInfo parseDetailPageDatas(Object arg1) {
		RequestNetResultInfo resultInfo = new RequestNetResultInfo();
		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				resultInfo.setStatus(jsonObject.getInt("status"));
				resultInfo.setMsg(jsonObject.getString("msg"));

				JSONObject data = jsonObject.getJSONObject("data");
				// 产品描述
				JSONObject pdiObject = data.getJSONObject("pdi");
				Pdi pdi = new Pdi();
				pdi.setId(getStringNodeValue(pdiObject, "id"));
				// 产品描述条目
				JSONObject pimObject = pdiObject.getJSONObject("pim");
				Pim pim = new Pim();

				pim.setId(getStringNodeValue(pimObject, "id"));
				pim.setNsf(getIntNodeValue(pimObject, "nsf"));
				pim.setPi(getLongNodeValue(pimObject, "pi"));
				pim.setIn(getStringNodeValue(pimObject, "in"));
				pim.setNo(getIntNodeValue(pimObject, "no"));

				pdi.setPim(pim);

				// 产品描述条目值pvm
				JSONArray pvmArray = pdiObject.getJSONArray("pvm");
				ArrayList<Pvm> pvms = new ArrayList<Pvm>();
				for (int j = 0; j < pvmArray.length(); j++) {
					JSONObject object = pvmArray.getJSONObject(j);
					Pvm pvm = new Pvm();

					pvm.setId(getStringNodeValue(object, "id"));
					pvm.setIl(getStringNodeValue(object, "il"));
					pvm.setIt(getIntNodeValue(object, "it"));
					pvm.setIurl(getStringNodeValue(object, "iurl"));
					pvm.setNo(getIntNodeValue(object, "no"));

					pvms.add(pvm);
				}

				pdi.setPvm(pvms);
				resultInfo.setPdi(pdi);

				// 产品描述条目值-规格参数
				JSONArray rpvlArray = data.getJSONArray("rpvl");
				List<Rpv> rpvs = new ArrayList<Rpv>();
				for (int j = 0; j < rpvlArray.length(); j++) {
					JSONObject object = rpvlArray.getJSONObject(j);
					Rpv rpv = new Rpv();
					rpv.setId(getStringNodeValue(object, "id"));
					rpv.setNo(getIntNodeValue(object, "no"));
					rpv.setPn(getStringNodeValue(object, "pn"));
					rpv.setPt(getIntNodeValue(object, "pt"));
					rpv.setPu(getStringNodeValue(object, "pu"));
					rpv.setPv(getStringNodeValue(object, "pv"));

					rpvs.add(rpv);
				}

				resultInfo.setRpvs(rpvs);
			}

		} catch (Exception e) {
		}
		return resultInfo;
	}

	/**
	 * 商品
	 * 
	 * @param arg1
	 * @return
	 */
	public static RequestNetResultInfo parseProductInfoPageDatas(Object arg1) {
		Log.d("TAG", "商品:  \n" + arg1);
		JSONArray array = (JSONArray) arg1;
		RequestNetResultInfo info = new RequestNetResultInfo();

		try {
			for (int i = 0; i < array.length(); i++) {

				JSONObject jsonObject = array.getJSONObject(i);

				info.setMsg(jsonObject.getString("msg"));
				info.setStatus(jsonObject.getInt("status"));

				JSONObject data = jsonObject.getJSONObject("data");

				// 产品服务快递支付支持
				JSONObject pszc = data.getJSONObject("pszc");
				// 配送支持
				JSONArray psz = pszc.getJSONArray("psz");
				

				// izc 发票支持
				JSONArray izc = pszc.getJSONArray("izc");
				

				// 付款方式支持
				JSONArray pmz = pszc.getJSONArray("pmz");
				

				// 商家基本信息cbi
				JSONObject cbiObject = data.getJSONObject("cbi");
				Cbi cbi = new Cbi();
				cbi.setId(cbiObject.getLong("id"));
				cbi.setAc(cbiObject.getInt("ac"));
				cbi.setCm(cbiObject.getString("cm"));
				cbi.setAp(cbiObject.getInt("ap"));
				cbi.setFc(cbiObject.getInt("fc"));
				cbi.setCd(cbiObject.getString("cd"));
				cbi.setLg(cbiObject.getString("lg"));

				info.setCbi(cbi);

				// 产品基本信息pbi
				Pbi pbi = getPbi(data.getJSONObject("pbi"));
				pbi.setPsz(psz);
				pbi.setIzc(izc);
				pbi.setPmz(pmz);
				info.setPbi(pbi);

				// biml 广告列表
				List<Bim> bims = getBim(data.getJSONArray("biml"));
				info.setBims(bims);
			}

			return info;

		} catch (Exception e) {
		}
		return info;
	}

	private static List<Bim> getBim(JSONArray array) {
		List<Bim> bims = new ArrayList<Bim>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				Bim bim = new Bim();
				bim.setNo(object.getString("no"));
				bim.setFp(object.getString("fp"));
				bim.setUrl(object.getString("url"));
				bims.add(bim);
			}

			return bims;

		} catch (Exception e) {
		}
		return bims;
	}

	private static Pbi getPbi(JSONObject object) {
		Pbi pbi = new Pbi();

		try {

			pbi.setId(object.getLong("id"));
			pbi.setSn(object.getString("sn"));
			pbi.setSd(object.getString("sd"));
			pbi.setIo(object.getString("io"));
			pbi.setBn(object.getString("bn"));
			pbi.setJd(object.getString("jd"));
			pbi.setPurl(object.getString("purl"));
			pbi.setDqj(object.getString("dqj"));
			pbi.setDqk(object.getString("dqk"));
			pbi.setPn(object.getString("pn"));
			pbi.setPp(object.getString("pp"));
			pbi.setCd(object.getString("cd"));
			pbi.setPs(object.getInt("ps"));

			pbi.setGgl(object.getJSONArray("ggl"));

			return pbi;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pbi;
	}

}
