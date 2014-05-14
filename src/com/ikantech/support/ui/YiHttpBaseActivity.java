package com.ikantech.support.ui;

import android.content.ServiceConnection;
import android.os.Bundle;

import com.ikantech.support.listener.YiHttpResponseListener;
import com.ikantech.support.net.YiHttpResponse;

public abstract class YiHttpBaseActivity extends YiUIBaseActivity implements
		YiHttpResponseListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		installLocalServiceBinder(onInstallLocalServiceBinder());
	}

	@Override
	protected void onDestroy()
	{
		uninstallLocalServiceBinder();
		super.onDestroy();
	}

	@Override
	public void onHttpResponse(final YiHttpResponse response)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				onUIHttpResponse(response);
			}
		});
	}

	protected abstract ServiceConnection onInstallLocalServiceBinder();

	protected abstract void onUIHttpResponse(YiHttpResponse response);
}
