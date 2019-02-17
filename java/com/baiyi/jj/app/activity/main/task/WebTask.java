package com.baiyi.jj.app.activity.main.task;

        import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class WebTask {

    private String tag;
    private Call call;
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }
}
