package com.baiyi.jj.skin.listener;

import java.util.List;

import android.view.View;

import com.baiyi.jj.skin.entity.DynamicAttr;

public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
