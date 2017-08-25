package com.xushuai.lianxi.utils;

import android.app.Application;

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
	}
}