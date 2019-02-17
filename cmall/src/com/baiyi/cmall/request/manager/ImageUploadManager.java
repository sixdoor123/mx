package com.baiyi.cmall.request.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.AddressEntity;
import com.baiyi.cmall.utils.ImageTools;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * ��¼����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 ����1:34:45
 */
public class ImageUploadManager {

	/**
	 * ��ȡ�ϴ�ͷ��ĵ�ַ
	 * 
	 * @param result
	 * @return
	 */
	public static AddressEntity getFixPhotoAddressInfo(JSONArray result) {

		AddressEntity addressEntity = new AddressEntity();

		for (int i = 0; i < result.length(); i++) {
			try {
				JSONObject object = result.getJSONObject(i);
				JSONObject dataObject = object.getJSONObject("data");

				addressEntity.setImageupload(dataObject
						.getString("imageupload"));
				addressEntity.setImageupdate(dataObject
						.getString("imageupdate"));
				addressEntity.setImageaddress(dataObject
						.getString("imageaddress"));

			} catch (JSONException e) {
				Log.d("TT", "printStackTrace");
			}
		}
		return addressEntity;
	}

	/**
	 * ��ȡ�ϴ�ͷ���json����
	 * 
	 * ���������[{ContentLength:10240, FileName: "20160412174624_0.jpg", InputByte:
	 * "ADSFASDFASDGASDG"}]
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String getImageuploadPostData(Bitmap bitmap) {

		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		byte[] data = ImageTools.getBitmapByte(bitmap);
		try {
			object.put("ContentLength", data.length);
			object.put("FileName", System.currentTimeMillis() + ".png");
			object.put("InputByte", ImageTools.bitmapToBase64(data));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array.put(object).toString();
	}

	/**
	 * ��ȡ�ϴ�ͷ���data���
	 * 
	 * @param result
	 * @return
	 */
	public static String getImageUpLoadInfo(JSONArray result) {

		String dataString = null;
		for (int i = 0; i < result.length(); i++) {
			try {
				JSONObject object = result.getJSONObject(i);
				JSONArray dataArray = object.getJSONArray("data");
				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject objectObject = dataArray.getJSONObject(j);
					dataString = objectObject.getString("Data");
				}

			} catch (JSONException e) {
				Log.d("TT", "printStackTrace");
			}
		}
		return dataString;
	}

	/**
	 * ��ȡ�޸�ͷ���json����
	 * 
	 * ���������{id: 10032, data: "", format: "JPEG", height: 100, width: 100, name:
	 * "20160412174624_0.jpg", url: "/Image/Photo/20160412174624_0.jpg"}
	 * 
	 * @param bitmap
	 * @param resultData
	 * @return
	 */
	public static String getFixPhotoPostData(Bitmap bitmap, String resultData) {

		JSONObject object = new JSONObject();

		try {
			object.put("id", CmallApplication.getUserInfo().getUserID());
			object.put("data", "");
			object.put("format", "JPEG");
			object.put("width", bitmap.getWidth());
			object.put("height", bitmap.getHeight());
			object.put("name", System.currentTimeMillis() + ".jpg");
			object.put("url", resultData);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

}
