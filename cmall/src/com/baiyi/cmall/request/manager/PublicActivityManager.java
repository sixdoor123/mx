package com.baiyi.cmall.request.manager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.application.UserApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.loader.cache.SimpleCacheLoader;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 上午10:52:13
 */
public class PublicActivityManager {

	/**
	 * 解析品牌数据
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<BrandEntities> getBrandData(Object result) {
		JSONArray array = (JSONArray) result;
		ArrayList<BrandEntities> datas = new ArrayList<BrandEntities>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < 1; j++) {
					JSONObject bObject = jsonArray.getJSONObject(j);
					JSONArray bArray = bObject.getJSONArray("brand");
					for (int k = 0; k < bArray.length(); k++) {
						JSONObject brand = bArray.getJSONObject(k);
						BrandEntities entities = new BrandEntities();

						entities.setId(brand.getString("id"));
						entities.setBrandName(brand.getString("brandname"));

						datas.add(entities);
					}
				}
			}

			return datas;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 查询地区
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<SelectedInfo> getDeliveryData(Object result) {
		ArrayList<SelectedInfo> infos = new ArrayList<SelectedInfo>();
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONArray dataArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject object = dataArray.getJSONObject(j);
					// 的到分类
					JSONArray city = object.getJSONArray("city");
					for (int k = 0; k < city.length(); k++) {
						JSONObject caObject = city.getJSONObject(k);

						SelectedInfo info = new SelectedInfo();
						info.setId(caObject.getString("id"));
						info.setCm_categorycode(caObject.getString("cm_categorycode"));
						info.setCm_categoryname(caObject.getString("cm_categoryname"));

						// 二级分类数据的集合
						ArrayList<SelectedInfo> sonCitities = new ArrayList<SelectedInfo>();
						// 解析 子菜单数据
						JSONArray sonArray = caObject.getJSONArray("son_category");
						for (int l = 0; l < sonArray.length(); l++) {

							JSONObject sonObject = sonArray.getJSONObject(l);

							SelectedInfo son = new SelectedInfo();
							son.setId(sonObject.getString("id"));
							son.setCm_categorycode(sonObject.getString("cm_categorycode"));
							son.setCm_categoryname(sonObject.getString("cm_categoryname"));

							// 三级分类数据的集合
							ArrayList<SelectedInfo> thirdCatities = new ArrayList<SelectedInfo>();
							// 三级菜单
							JSONArray thirds = sonObject.getJSONArray("son_category");
							for (int m = 0; m < thirds.length(); m++) {
								JSONObject third = thirds.getJSONObject(m);
								SelectedInfo th = new SelectedInfo();
								th.setId(third.getString("id"));
								th.setCm_categorycode(third.getString("cm_categorycode"));
								th.setCm_categoryname(third.getString("cm_categoryname"));

								thirdCatities.add(th);
							}
							son.setSonDatas(thirdCatities);
							sonCitities.add(son);
						}

						info.setSonDatas(sonCitities);
						infos.add(info);
					}
				}
			}
			return infos;
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 查询分类
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<SelectedInfo> getCategoryData(Object result) {
		ArrayList<SelectedInfo> infos = new ArrayList<SelectedInfo>();
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONArray dataArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject object = dataArray.getJSONObject(j);
					// 的到分类
					JSONArray cate = object.getJSONArray("category");
					for (int k = 0; k < cate.length(); k++) {
						JSONObject caObject = cate.getJSONObject(k);

						SelectedInfo info = new SelectedInfo();
						info.setId(caObject.getString("id"));
						info.setCm_categorycode(caObject.getString("cm_categorycode"));
						info.setCm_categoryname(caObject.getString("cm_categoryname"));

						// 二级分类数据的集合
						ArrayList<SelectedInfo> sonCates = new ArrayList<SelectedInfo>();
						// 解析 子菜单数据
						JSONArray sonArray = caObject.getJSONArray("son_category");
						for (int l = 0; l < sonArray.length(); l++) {

							JSONObject sonObject = sonArray.getJSONObject(l);

							SelectedInfo son = new SelectedInfo();
							son.setId(sonObject.getString("id"));
							son.setCm_categorycode(sonObject.getString("cm_categorycode"));
							son.setCm_categoryname(sonObject.getString("cm_categoryname"));

							sonCates.add(son);
						}

						info.setSonDatas(sonCates);

						infos.add(info);
					}
				}
			}
			return infos;
		} catch (Exception e) {
		}

		return null;

	}

	/**
	 * 查询包装方式
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<SelectedInfo> getPackWayData(Object result) {
		ArrayList<SelectedInfo> infos = new ArrayList<SelectedInfo>();
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONArray dataArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject object = dataArray.getJSONObject(j);
					JSONArray packageMap = object.getJSONArray("packageMap");
					for (int k = 0; k < packageMap.length(); k++) {
						JSONObject pack = packageMap.getJSONObject(k);

						SelectedInfo packInfo = new SelectedInfo();
						packInfo.setId(pack.getString("id"));
						packInfo.setCm_categoryname(pack.getString("codeValue"));
						packInfo.setCm_categorycode(pack.getString("orderNo"));

						infos.add(packInfo);
					}
				}
			}

			return infos;
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 查询运输方式
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<SelectedInfo> getMoveWayData(Object result) {
		ArrayList<SelectedInfo> infos = new ArrayList<SelectedInfo>();
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONArray dataArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject object = dataArray.getJSONObject(j);
					JSONArray yunshuArray = object.getJSONArray("deliveryMap");
					for (int k = 0; k < yunshuArray.length(); k++) {
						JSONObject yun = yunshuArray.getJSONObject(k);
						SelectedInfo yunInfo = new SelectedInfo();
						yunInfo.setId(yun.getString("id"));
						yunInfo.setCm_categoryname(yun.getString("codeValue"));
						yunInfo.setCm_categorycode(yun.getString("orderNo"));

						infos.add(yunInfo);
					}
				}
			}

			return infos;
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 解析供应分类查询条件的数据
	 * 
	 * @param result
	 */
	public static GoodsSourceInfo getSelectedData(Context context, Object result) {
		Log.d("TAG", "--基础数据--" + result.toString());

		/**
		 * 全部
		 */
		SelectedInfo sInfo = new SelectedInfo();
		sInfo.setCm_categorycode("-1");
		sInfo.setCm_categoryname("全部");
		GoodsSourceInfo sourceInfo = null;
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONArray dataArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject object = dataArray.getJSONObject(j);
					// 品牌
					JSONArray bArray = object.getJSONArray("brand");
					ArrayList<BrandEntities> datas = new ArrayList<BrandEntities>();
					for (int k = 0; k < bArray.length(); k++) {
						JSONObject brand = bArray.getJSONObject(k);
						BrandEntities entities = new BrandEntities();

						entities.setId(brand.getString("id"));
						entities.setBrandName(brand.getString("brandname"));

						datas.add(entities);
					}

					// 分类
					// 一级分类数据的集合
					ArrayList<SelectedInfo> firstCates = new ArrayList<SelectedInfo>();
					firstCates.add(0, sInfo);

					// 的到分类
					JSONArray cate = object.getJSONArray("category");
					for (int k = 0; k < cate.length(); k++) {
						JSONObject caObject = cate.getJSONObject(k);

						SelectedInfo info = new SelectedInfo();
						info.setId(caObject.getString("id"));
						info.setCm_categorycode(caObject.getString("cm_categorycode"));
						info.setCm_categoryname(caObject.getString("cm_categoryname"));

						// 二级分类数据的集合
						ArrayList<SelectedInfo> sonCates = new ArrayList<SelectedInfo>();
						sonCates.add(0, sInfo);
						// 解析 子菜单数据
						JSONArray sonArray = caObject.getJSONArray("son_category");
						for (int l = 0; l < sonArray.length(); l++) {

							JSONObject sonObject = sonArray.getJSONObject(l);

							SelectedInfo son = new SelectedInfo();
							son.setId(sonObject.getString("id"));
							son.setCm_categorycode(sonObject.getString("cm_categorycode"));
							son.setCm_categoryname(sonObject.getString("cm_categoryname"));

							sonCates.add(son);
						}

						info.setSonDatas(sonCates);

						firstCates.add(info);
					}

					// 交割地
					// 一级分类数据的集合
					ArrayList<SelectedInfo> firstCities = new ArrayList<SelectedInfo>();
					SelectedInfo first = new SelectedInfo();
					first.setCm_categorycode("-1");
					first.setCm_categoryname("全部");
					first.setFlag(NumEntity.FIRST);
					firstCities.add(0, first);

					// 的到分类
					JSONArray city = object.getJSONArray("city");
					for (int k = 0; k < city.length(); k++) {
						JSONObject caObject = city.getJSONObject(k);

						SelectedInfo info = new SelectedInfo();
						info.setId(caObject.getString("id"));
						info.setCm_categorycode(caObject.getString("cm_categorycode"));
						info.setCm_categoryname(caObject.getString("cm_categoryname"));

						info.setFlag(NumEntity.FIRST);

						// 二级分类数据的集合
						ArrayList<SelectedInfo> sonCitities = new ArrayList<SelectedInfo>();
						SelectedInfo seconed = new SelectedInfo();
						seconed.setCm_categorycode("-1");
						seconed.setCm_categoryname("全部");
						seconed.setFlag(NumEntity.SECONED);
						sonCitities.add(0, seconed);
						// 解析 子菜单数据
						JSONArray sonArray = caObject.getJSONArray("son_category");
						for (int l = 0; l < sonArray.length(); l++) {

							JSONObject sonObject = sonArray.getJSONObject(l);

							SelectedInfo son = new SelectedInfo();
							son.setId(sonObject.getString("id"));
							son.setCm_categorycode(sonObject.getString("cm_categorycode"));
							son.setCm_categoryname(sonObject.getString("cm_categoryname"));

							son.setFlag(NumEntity.SECONED);

							// 三级分类数据的集合
							ArrayList<SelectedInfo> thirdCatities = new ArrayList<SelectedInfo>();
							SelectedInfo thirdInfo = new SelectedInfo();
							thirdInfo.setCm_categorycode("-1");
							thirdInfo.setCm_categoryname("全部");
							thirdInfo.setFlag(NumEntity.THIRD);
							thirdCatities.add(0, thirdInfo);
							// 三级菜单
							JSONArray thirds = sonObject.getJSONArray("son_category");
							for (int m = 0; m < thirds.length(); m++) {
								JSONObject third = thirds.getJSONObject(m);
								SelectedInfo th = new SelectedInfo();
								th.setId(third.getString("id"));
								th.setCm_categorycode(third.getString("cm_categorycode"));
								th.setCm_categoryname(third.getString("cm_categoryname"));

								th.setFlag(NumEntity.THIRD);

								// 四级交割地数据的集合
								ArrayList<SelectedInfo> fourCatities = new ArrayList<SelectedInfo>();
								SelectedInfo fourInfo = new SelectedInfo();
								fourInfo.setCm_categorycode("-1");
								fourInfo.setCm_categoryname("全部");
								fourInfo.setFlag(NumEntity.FOUR);
								fourCatities.add(0, fourInfo);
								// 四级菜单
								JSONArray fours = third.getJSONArray("son_category");
								for (int n = 0; n < fours.length(); n++) {
									JSONObject four = fours.getJSONObject(n);

									SelectedInfo f = new SelectedInfo();
									f.setId(four.getString("id"));
									f.setCm_categorycode(four.getString("cm_categorycode"));
									f.setCm_categoryname(four.getString("cm_categoryname"));
									f.setFlag(NumEntity.FOUR);

									fourCatities.add(f);
								}
								th.setSonDatas(fourCatities);
								thirdCatities.add(th);
							}
							son.setSonDatas(thirdCatities);
							sonCitities.add(son);
						}

						info.setSonDatas(sonCitities);
						firstCities.add(info);
					}

					// 运输方式
					ArrayList<SelectedInfo> yunInfos = new ArrayList<SelectedInfo>();
					JSONArray yunshuArray = object.getJSONArray("deliveryMap");
					for (int k = 0; k < yunshuArray.length(); k++) {
						JSONObject yun = yunshuArray.getJSONObject(k);
						SelectedInfo yunInfo = new SelectedInfo();
						yunInfo.setId(yun.getString("id"));
						yunInfo.setCm_categoryname(yun.getString("codeValue"));
						yunInfo.setCm_categorycode(yun.getString("orderNo"));

						yunInfos.add(yunInfo);
					}

					// 包装方式
					ArrayList<SelectedInfo> packInfos = new ArrayList<SelectedInfo>();
					JSONArray packageMap = object.getJSONArray("packageMap");
					for (int k = 0; k < packageMap.length(); k++) {
						JSONObject pack = packageMap.getJSONObject(k);

						SelectedInfo packInfo = new SelectedInfo();
						packInfo.setId(pack.getString("id"));
						packInfo.setCm_categoryname(pack.getString("codeValue"));
						packInfo.setCm_categorycode(pack.getString("orderNo"));

						packInfos.add(packInfo);
					}

					// 解析默认排序
					ArrayList<SelectedInfo> sortWays = new ArrayList<SelectedInfo>();
					JSONArray sort = object.getJSONArray("sortWay");
					for (int k = 0; k < sort.length(); k++) {
						JSONObject pack = sort.getJSONObject(k);

						SelectedInfo sortInfo = new SelectedInfo();
						sortInfo.setId(pack.getString("id"));
						sortInfo.setCm_categoryname(pack.getString("codeValue"));
						sortInfo.setCm_categorycode(pack.getString("orderNo"));

						sortWays.add(sortInfo);
					}
//					SelectedInfo s = new SelectedInfo();
//					s.setCm_categorycode("");
//					s.setCm_categoryname("默认");
//					sortWays.add(0, s);

					sourceInfo = new GoodsSourceInfo();

					// 访问路径
					String baseAddress = object.getString("baseAddress").trim();
					XmlUtils.getInstence(context).savaXmlValue(XmlName.Base_Address, baseAddress);

					sourceInfo.setCategoryDatas(firstCates);// 分类
					sourceInfo.setDeliveryDatas(firstCities);// 交割地
					sourceInfo.setSortWays(sortWays);// 默认排序
					sourceInfo.setYunInfos(yunInfos);// 运输方式
					sourceInfo.setPackageInfos(packInfos);// 包装方式
					sourceInfo.setBrands(datas);// 品牌
				}
			}

			return sourceInfo;

		} catch (Exception e) {
		}
		return sourceInfo;
	}
	/**
	 * 解析供应分类查询条件的数据
	 * 
	 * @param result
	 */
	public static GoodsSourceInfo getPublicData(Context context, Object result) {
		Log.d("TAG", "--基础数据--" + result.toString());
		GoodsSourceInfo sourceInfo = null;
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				
				JSONArray dataArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject object = dataArray.getJSONObject(j);
					// 品牌
					JSONArray bArray = object.getJSONArray("brand");
					ArrayList<BrandEntities> datas = new ArrayList<BrandEntities>();
					for (int k = 0; k < bArray.length(); k++) {
						JSONObject brand = bArray.getJSONObject(k);
						BrandEntities entities = new BrandEntities();
						
						entities.setId(brand.getString("id"));
						entities.setBrandName(brand.getString("brandname"));
						
						datas.add(entities);
					}
					
					// 分类
					// 一级分类数据的集合
					ArrayList<SelectedInfo> firstCates = new ArrayList<SelectedInfo>();
					
					// 的到分类
					JSONArray cate = object.getJSONArray("category");
					for (int k = 0; k < cate.length(); k++) {
						JSONObject caObject = cate.getJSONObject(k);
						
						SelectedInfo info = new SelectedInfo();
						info.setId(caObject.getString("id"));
						info.setCm_categorycode(caObject.getString("cm_categorycode"));
						info.setCm_categoryname(caObject.getString("cm_categoryname"));
						
						// 二级分类数据的集合
						ArrayList<SelectedInfo> sonCates = new ArrayList<SelectedInfo>();
						// 解析 子菜单数据
						JSONArray sonArray = caObject.getJSONArray("son_category");
						for (int l = 0; l < sonArray.length(); l++) {
							
							JSONObject sonObject = sonArray.getJSONObject(l);
							
							SelectedInfo son = new SelectedInfo();
							son.setId(sonObject.getString("id"));
							son.setCm_categorycode(sonObject.getString("cm_categorycode"));
							son.setCm_categoryname(sonObject.getString("cm_categoryname"));
							
							sonCates.add(son);
						}
						
						info.setSonDatas(sonCates);
						
						firstCates.add(info);
					}
					
					// 交割地
					// 一级分类数据的集合
					ArrayList<SelectedInfo> firstCities = new ArrayList<SelectedInfo>();
					
					// 的到分类
					JSONArray city = object.getJSONArray("city");
					for (int k = 0; k < city.length(); k++) {
						JSONObject caObject = city.getJSONObject(k);
						
						SelectedInfo info = new SelectedInfo();
						info.setId(caObject.getString("id"));
						info.setCm_categorycode(caObject.getString("cm_categorycode"));
						info.setCm_categoryname(caObject.getString("cm_categoryname"));
						
						info.setFlag(NumEntity.FIRST);
						
						// 二级分类数据的集合
						ArrayList<SelectedInfo> sonCitities = new ArrayList<SelectedInfo>();
						// 解析 子菜单数据
						JSONArray sonArray = caObject.getJSONArray("son_category");
						for (int l = 0; l < sonArray.length(); l++) {
							
							JSONObject sonObject = sonArray.getJSONObject(l);
							
							SelectedInfo son = new SelectedInfo();
							son.setId(sonObject.getString("id"));
							son.setCm_categorycode(sonObject.getString("cm_categorycode"));
							son.setCm_categoryname(sonObject.getString("cm_categoryname"));
							
							son.setFlag(NumEntity.SECONED);
							
							// 三级分类数据的集合
							ArrayList<SelectedInfo> thirdCatities = new ArrayList<SelectedInfo>();
							// 三级菜单
							JSONArray thirds = sonObject.getJSONArray("son_category");
							for (int m = 0; m < thirds.length(); m++) {
								JSONObject third = thirds.getJSONObject(m);
								SelectedInfo th = new SelectedInfo();
								th.setId(third.getString("id"));
								th.setCm_categorycode(third.getString("cm_categorycode"));
								th.setCm_categoryname(third.getString("cm_categoryname"));
								
								th.setFlag(NumEntity.THIRD);
								
								// 四级交割地数据的集合
								ArrayList<SelectedInfo> fourCatities = new ArrayList<SelectedInfo>();
								// 四级菜单
//								JSONArray fours = third.getJSONArray("son_category");
//								for (int n = 0; n < fours.length(); n++) {
//									JSONObject four = fours.getJSONObject(n);
//									
//									SelectedInfo f = new SelectedInfo();
//									f.setId(four.getString("id"));
//									f.setCm_categorycode(four.getString("cm_categorycode"));
//									f.setCm_categoryname(four.getString("cm_categoryname"));
//									f.setFlag(NumEntity.FOUR);
//									
//									fourCatities.add(f);
//								}
//								th.setSonDatas(fourCatities);
								thirdCatities.add(th);
							}
							son.setSonDatas(thirdCatities);
							sonCitities.add(son);
						}
						
						info.setSonDatas(sonCitities);
						firstCities.add(info);
					}
					
					// 运输方式
					ArrayList<SelectedInfo> yunInfos = new ArrayList<SelectedInfo>();
					JSONArray yunshuArray = object.getJSONArray("deliveryMap");
					for (int k = 0; k < yunshuArray.length(); k++) {
						JSONObject yun = yunshuArray.getJSONObject(k);
						SelectedInfo yunInfo = new SelectedInfo();
						yunInfo.setId(yun.getString("id"));
						yunInfo.setCm_categoryname(yun.getString("codeValue"));
						yunInfo.setCm_categorycode(yun.getString("orderNo"));
						
						yunInfos.add(yunInfo);
					}
					
					// 包装方式
					ArrayList<SelectedInfo> packInfos = new ArrayList<SelectedInfo>();
					JSONArray packageMap = object.getJSONArray("packageMap");
					for (int k = 0; k < packageMap.length(); k++) {
						JSONObject pack = packageMap.getJSONObject(k);
						
						SelectedInfo packInfo = new SelectedInfo();
						packInfo.setId(pack.getString("id"));
						packInfo.setCm_categoryname(pack.getString("codeValue"));
						packInfo.setCm_categorycode(pack.getString("orderNo"));
						
						packInfos.add(packInfo);
					}
					
					// 解析默认排序
					ArrayList<SelectedInfo> sortWays = new ArrayList<SelectedInfo>();
					JSONArray sort = object.getJSONArray("sortWay");
					for (int k = 0; k < sort.length(); k++) {
						JSONObject pack = sort.getJSONObject(k);
						
						SelectedInfo sortInfo = new SelectedInfo();
						sortInfo.setId(pack.getString("id"));
						sortInfo.setCm_categoryname(pack.getString("codeValue"));
						sortInfo.setCm_categorycode(pack.getString("orderNo"));
						
						sortWays.add(sortInfo);
					}
					SelectedInfo s = new SelectedInfo();
					s.setCm_categorycode("");
					s.setCm_categoryname("默认");
					sortWays.add(0, s);
					
					sourceInfo = new GoodsSourceInfo();
					
					// 访问路径
					String baseAddress = object.getString("baseAddress").trim();
					XmlUtils.getInstence(context).savaXmlValue(XmlName.Base_Address, baseAddress);
					
					sourceInfo.setCategoryDatas(firstCates);// 分类
					sourceInfo.setDeliveryDatas(firstCities);// 交割地
					sourceInfo.setSortWays(sortWays);// 默认排序
					sourceInfo.setYunInfos(yunInfos);// 运输方式
					sourceInfo.setPackageInfos(packInfos);// 包装方式
					sourceInfo.setBrands(datas);// 品牌
				}
			}
			
			return sourceInfo;
			
		} catch (Exception e) {
		}
		return sourceInfo;
	}

	/**
	 * 我的供应-详细信息
	 * 
	 * @param result
	 * @return
	 */
	public static GoodsSourceInfo getProInfos(Object result) {
		Log.d("TAG", "--getProInfos--" + result.toString());
		GoodsSourceInfo info = new GoodsSourceInfo();
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				// 供应信息
				JSONObject data = jsonObject.getJSONObject("data");
				ArrayList<GoodsSourceInfo> purInfos = new ArrayList<GoodsSourceInfo>();
				JSONArray purList = data.getJSONArray("companyPurIntentionModelList");

				for (int k = 0; k < purList.length(); k++) {
					JSONObject purs = purList.getJSONObject(k);
					GoodsSourceInfo info2 = new GoodsSourceInfo();
					info2.setGoodSID(purs.getString("id"));
					// 名称
					info2.setPurchasename(purs.getString("purchasename"));
					// 采购商名称
					info2.setCompanyName(purs.getString("purcompanyname"));
					info2.setInventory(purs.getString("amount"));
					info2.setIntentionOrderState(purs.getString("intentionstatename"));
					info2.setIntentionstatename(purs.getString("intentionstatename"));
					info2.setDeliveryplacename(purs.getString("deliveryplacename"));
					info2.setPrice(purs.getString("price"));

					info2.setState(purs.getInt("intentionstate"));
					info2.setType(purs.getInt("intentiontype"));
					info2.setDeletebyuser(purs.getInt("deletebyuser"));

					purInfos.add(info2);
				}
				info.setPurInfos(purInfos);

				JSONObject pros = data.getJSONObject("companyOfferModel");
				// 发布时间
				long time = pros.getLong("offerBeginTime");
				info.setGoodSPutTime(Utils.getTimeYMD(time));
				// 结束时间
				info.setGoodSEndTime(Utils.getTimeYMD(pros.getLong("offerEndTime")));

				info.setGoodSID(pros.getString("id"));
				info.setGoodSCategory(pros.getString("categoryName"));
				// 供应详情
				String offerDetails = pros.getString("offerDetail");
				info.setGoodSPurchaseContent(offerDetails);

				info.setGoodSPrePrice(pros.getString("price"));
				info.setGoodSWeight(pros.getString("inventory"));
				// 原产地
				info.setGoodSPlace(pros.getString("originPlaceName"));
				// 预付款
				info.setPrepayment(pros.getString("prepayment"));
				info.setGoodSMerchant(pros.getString("companyName"));
				info.setGoodSBrand(pros.getString("brandName"));
				// 商品名称
				info.setGoodSName(pros.getString("offerName"));
				info.setGoodSDelivery(pros.getString("deliveryPlaceName"));
				// 状态
				info.setState(pros.getInt("offerState"));
				info.setStatename(pros.getString("offerStateName"));
				// 发布状态
				info.setPublicstate(pros.getInt("publicstate"));
				info.setPublicstatename(pros.getString("publicstatename"));
				info.setGoodSRemark(pros.getString("offerAudit"));

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}

}
