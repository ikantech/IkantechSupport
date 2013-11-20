package com.ikantech.support.ui;

import android.app.Application;
import android.os.Environment;

import com.ikantech.localservicesupport.R;
import com.ikantech.support.common.YiCrashHandler;
import com.ikantech.support.proxy.YiDialogProxy;

public class YiApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		if (openCrashHandler()) {
			initCrashHandler();
		}
		initialize();
	}

	protected void initCrashHandler() {
		YiCrashHandler crashHandler = YiCrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	protected void initialize() {
		YiCrashHandler.setLogPath(Environment.getExternalStorageDirectory()
				.getPath() + "yicrash/");
		YiDialogProxy.setMsgDialogLayoutRes(R.layout.dialog_template);
		YiDialogProxy.setMsgDialogTheme(R.style.Custom_Dialog);
	}

	protected boolean openCrashHandler() {
		return true;
	}
}