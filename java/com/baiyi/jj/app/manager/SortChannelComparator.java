package com.baiyi.jj.app.manager;

import com.baiyi.jj.app.entity.ChannelItem;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class SortChannelComparator implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        ChannelItem item = (ChannelItem) lhs;
        ChannelItem item2 = (ChannelItem) rhs;
        return (item2.getChannel_index() - item.getChannel_index());
    }
}
