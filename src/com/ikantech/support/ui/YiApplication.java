package com.ikantech.support.ui;

import android.app.Application;

import com.ikantech.localservicesupport.R;
import com.ikantech.support.common.YiCrashHandler;
import com.ikantech.support.proxy.YiDialogProxy;
import com.ikantech.support.utils.YiFileUtils;
import com.ikantech.support.utils.YiLog;

public class YiApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		initialize();
		if (openCrashHandler()) {
			initCrashHandler();
		}
	}

	protected void initCrashHandler() {
		YiCrashHandler crashHandler = YiCrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	protected void initialize() {
		YiLog.ENABLE_DEBUG = true;
		YiLog.ENABLE_ERROR = true;
		YiLog.ENABLE_INFO = true;
		YiLog.ENABLE_WARN = true;
		YiLog.ENABLE_VERBOSE = true;

		YiFileUtils.register(this);
		YiCrashHandler.setLogPath(YiFileUtils.getStorePath() + "yicrash/");
		YiDialogProxy.setMsgDialogLayoutRes(R.layout.dialog_template);
		YiDialogProxy.setMsgDialogTheme(R.style.Custom_Dialog);
	}

	protected boolean openCrashHandler() {
		return true;
	}
}