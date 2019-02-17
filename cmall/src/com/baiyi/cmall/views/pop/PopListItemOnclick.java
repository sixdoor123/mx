/**
 * 
 */
package com.baiyi.cmall.views.pop;

import com.baiyi.cmall.entity.SelectedInfo;

/**
 * Pop ListView itemµã»÷ÊÂ¼þ
 * @author tangkun
 *
 */
public interface PopListItemOnclick {
	public void setPopListItemOnclick(int position, SelectedInfo parentInfo, SelectedInfo childInfo, boolean isParent);

}
