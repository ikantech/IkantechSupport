package com.ikantech.support.app;

import android.app.Application;

import com.ikantech.support.R;
import com.ikantech.support.common.YiCrashHandler;
import com.ikantech.support.proxy.YiDialogProxy;
import com.ikantech.support.util.YiFileUtils;
import com.ikantech.support.util.YiLog;

public class YiApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		initialize();
		if (openCrashHandler())
		{
			initCrashHandler();
		}
	}

	protected void initCrashHandler()
	{
		YiCrashHandler crashHandler = YiCrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
	}

	protected void initialize()
	{
		YiLog.ENABLE_DEBUG = true;
		YiLog.ENABLE_ERROR = true;
		YiLog.ENABLE_INFO = true;
		YiLog.ENABLE_WARN = true;
		YiLog.ENABLE_VERBOSE = true;

		YiFileUtils.register(this);

		// set crash log store path
		YiCrashHandler.setLogPath(YiFileUtils.getStorePath() + "ikantech/yicrash/");

		// set dialog layout
		YiDialogProxy.setMsgDialogLayoutRes(R.layout.yi_dialog_template);
		YiDialogProxy.setMsgDialogTheme(R.style.Custom_Dialog_Dim);
		YiDialogProxy
				.setProgressDialogLayoutRes(R.layout.yi_progress_dialog_template);
		YiDialogProxy.setProgressDialogTheme(R.style.Custom_Dialog_Dim);
	}

	protected boolean openCrashHandler()
	{
		return true;
	}
}