package com.baiyi.jj.app.activity.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.baiyi.jj.app.application.CmsApplication;

/**
 * Created by Administrator on 2017/9/11 0011.
 */
public class BaseFragment extends Fragment{

    private Activity activity;

    @Override
    public Context getContext() {
        if (activity == null){
            return CmsApplication.getApp();
        }
        return activity;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        activity = getActivity();
    }
}
