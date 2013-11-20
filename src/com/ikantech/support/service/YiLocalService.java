package com.ikantech.support.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;

/**
 * 用于处理耗时操作
 * 
 * @author saint
 * 
 */
public class YiLocalService extends Service
{
	private HandlerThread mWorkThread;
	private WorkHandler mWorkHandler;

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return new LocalServiceBinder();
	}

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		mWorkThread = new HandlerThread("XmppServiceWorkThread");
		mWorkThread.start();
		mWorkHandler = new WorkHandler(mWorkThread.getLooper());
	}

	@Override
	public void onDestroy()
	{
		// 停止WorkThread
		if (mWorkThread != null)
		{
			mWorkThread.quit();
			mWorkThread = null;
			mWorkHandler = null;
		}
		super.onDestroy();
	}

	public class LocalServiceBinder extends Binder
	{
		public void post(Runnable runnable)
		{
			mWorkHandler.post(runnable);
		}

		public void postDelayed(Runnable runnable, long delayMillis)
		{
			mWorkHandler.postDelayed(runnable, delayMillis);
		}
	}

	private class WorkHandler extends Handler
	{
		public WorkHandler(Looper looper)
		{
			super(looper);
		}
	}
}
