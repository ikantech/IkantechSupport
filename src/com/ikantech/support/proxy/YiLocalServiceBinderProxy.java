package com.ikantech.support.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.ikantech.support.service.YiLocalService;
import com.ikantech.support.service.YiLocalService.YiLocalServiceBinder;
import com.ikantech.support.util.YiLog;

/**
 * LocalService绑定代理类。 便于绑定LocalService，减少冗余代码
 * 
 * @author saint
 * 
 */
public class YiLocalServiceBinderProxy
{
	private YiLocalServiceBinder mService = null;
	private Context mContext = null;
	private ServiceConnection mExConnection = null;

	private ServiceConnection mConnection = new ServiceConnection()
	{

		public void onServiceConnected(ComponentName name, IBinder service)
		{
			mService = (YiLocalServiceBinder) service;
			if (mExConnection != null)
			{
				mExConnection.onServiceConnected(name, service);
			}
			YiLog.getInstance().i("Bind Success:" + mService);
		}

		public void onServiceDisconnected(ComponentName name)
		{
			// TODO Auto-generated method stub
			mService = null;
			if (mExConnection != null)
			{
				mExConnection.onServiceDisconnected(name);
			}
		}
	};

	public YiLocalServiceBinderProxy(Context context)
	{
		if (context == null)
		{
			throw new NullPointerException("context non-null");
		}
		mContext = context;
	}

	public void installLocalServiceBinder()
	{
		Intent service = new Intent(mContext, YiLocalService.class);
		mContext.bindService(service, mConnection, Context.BIND_AUTO_CREATE);
	}

	public void installLocalServiceBinder(ServiceConnection connection)
	{
		mExConnection = connection;
		installLocalServiceBinder();
	}

	public void uninstallLocalServiceBinder()
	{
		mContext.unbindService(mConnection);
	}

	public YiLocalServiceBinder getLocalService()
	{
		if (mService == null)
		{
			throw new NullPointerException("mService is null");
		}
		return mService;
	}

	public interface YiLocalServiceServiceBinderProxiable
	{
		void installLocalServiceBinder();

		void installLocalServiceBinder(ServiceConnection connection);

		void uninstallLocalServiceBinder();

		YiLocalServiceBinder getLocalService();
	}
}
