package com.baiyi.jj.app.activity.main.util;

import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.pageview.PageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxy on 2016/10/20.
 */

public class MainViewUtils
{
    private static List<PageView> pageViews = null;

    private static MainViewUtils utils ;
    private  MainViewUtils(){
        if (!Utils.isStringEmpty(pageViews))
        {
            pageViews.clear();
        }
        else
        {
            pageViews = new ArrayList<PageView>();
        }
    }

    public static MainViewUtils getInstence()
    {
        if (utils == null)
        {
            utils = new MainViewUtils();
        }
        return  utils ;
    }

    public static void addpageViews(PageView pageView)
    {
        pageViews.add(pageView);
    }

    public List<PageView> getPageViews() {
        return pageViews;
    }
}
