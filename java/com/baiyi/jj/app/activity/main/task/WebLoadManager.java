package com.baiyi.jj.app.activity.main.task;

import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class WebLoadManager {

    private final String TAG = "WebLoadManager";

    public WebLoadManager() {
    }

    private List<WebTask> webTasks = new ArrayList<>();
    private List<String> tagList = new ArrayList<>();

    public void addTask(WebTask task) {
        if (webTasks.size() >= 5) {
//            TLog.e(TAG, "remove000---------" + webTasks.get(0).getTag());
            Call call = webTasks.get(0).getCall();
            if (call.isExecuted()){
                call.cancel();
            }
            webTasks.remove(0);
            tagList.remove(0);
        }
        webTasks.add(task);
        tagList.add(task.getTag());
    }

    public void removeTask(String tag) {

        if (!Utils.isStringEmpty(webTasks)) {
//            TLog.e(TAG, "remove111---------" + webTasks.size());
            for (int i = 0; i < tagList.size(); i++) {
                if (tagList.size()>i && tagList.get(i).equals(tag)) {
                    tagList.remove(i);
                    webTasks.remove(i);
                    break;
                }
            }
//            TLog.e(TAG, "remove222---------" + webTasks.size());
        }
    }

    public void clearTask() {
        if (!Utils.isStringEmpty(webTasks)) {
            webTasks.clear();
            tagList.clear();
        }
    }
}
