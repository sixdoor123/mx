package com.baiyi.jj.app.adapter.base;

import android.view.View;
/**
 * 
 * @author tangkun
 *
 */
public interface GdapterTypeRender {

    /**
     * ����getView����
     * @return
     */
    public View getConvertView();

    /**
     * ���item�и����ؼ����¼������簴ť����¼���
     */
    public void setEvents();

    /**
     * ��ָ��position��item�������ݵ�����
     * @param position
     */
    public void setDatas(int position, boolean isLoading);
    

}
