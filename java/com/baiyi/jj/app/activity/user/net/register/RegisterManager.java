package com.baiyi.jj.app.activity.user.net.register;

import com.baiyi.jj.app.activity.user.net.register.entity.ResultEntities;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.utils.TLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sunxy on 2016/9/13.
 */

public class RegisterManager {

    public static String getRegisterPostData(String email,String password,String client_code) {


        JSONObject object = new JSONObject();
        try {
            object.put("password", password);
            object.put("email", email);
            object.put("client_code", client_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TLog.e("regist",object.toString());
        return object.toString();
    }

    /**
     * 注册结果
     *
     * @param array
     */
    public static ResultEntities getRegisterResult(JSONArray array) {
        ResultEntities resultEntities = new ResultEntities();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject ob = array.getJSONObject(i);
                StateEnties stateEnties = new StateEnties();
                stateEnties.setMsg(ob.getString("msg"));
                stateEnties.setState(ob.getInt("state"));
                if (stateEnties.getState()==200)
                {
                    JSONObject object = ob.getJSONObject("data");
                    resultEntities.setId(object.getString("id"));
                    resultEntities.setUserName(object.getString("username"));
                }
                resultEntities.setStateEnties(stateEnties);
            }

            return  resultEntities;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
