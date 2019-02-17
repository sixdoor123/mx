package com.baiyi.jj.skin.listener;

public interface ISkinLoader {
	void attach(ISkinUpdate observer);
	void detach(ISkinUpdate observer);
	void notifySkinUpdate();
//	void notifySkinDefault();
}
