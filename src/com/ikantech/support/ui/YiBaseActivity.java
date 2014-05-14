package com.ikantech.support.ui;

import android.app.Activity;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.ikantech.support.proxy.YiActivityProxy;
import com.ikantech.support.proxy.YiDialogProxy;
import com.ikantech.support.proxy.YiDialogProxy.YiDialogProxiable;
import com.ikantech.support.proxy.YiLocalServiceBinderProxy.YiLocalServiceServiceBinderProxiable;
import com.ikantech.support.proxy.YiToastProxy.YiToastProxiable;
import com.ikantech.support.service.YiLocalService.YiLocalServiceBinder;

public class YiBaseActivity extends Activity implements YiToastProxiable,
		YiLocalServiceServiceBinderProxiable, YiDialogProxiable
{
	protected YiActivityProxy mActivityProxy;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mActivityProxy = new YiActivityProxy(this);
	}

	@Override
	protected void onDestroy()
	{
		mActivityProxy.onDestroy();
		super.onDestroy();
	}

	/*******************************************************************************
	 * ToastProxy
	 *******************************************************************************/
	@Override
	public void showToast(int resourceId)
	{
		mActivityProxy.showToast(resourceId);
	}

	@Override
	public void showToast(String text)
	{
		mActivityProxy.showToast(text);
	}

	/*******************************************************************************
	 * LocalServiceBinderProxy
	 *******************************************************************************/
	@Override
	public void installLocalServiceBinder()
	{
		// TODO Auto-generated method stub
		mActivityProxy.installLocalServiceBinder();
	}

	@Override
	public void installLocalServiceBinder(ServiceConnection connection)
	{
		// TODO Auto-generated method stub
		mActivityProxy.installLocalServiceBinder(connection);
	}

	@Override
	public void uninstallLocalServiceBinder()
	{
		// TODO Auto-generated method stub
		mActivityProxy.uninstallLocalServiceBinder();
	}

	@Override
	public YiLocalServiceBinder getLocalService()
	{
		// TODO Auto-generated method stub
		return mActivityProxy.getLocalService();
	}

	/*******************************************************************************
	 * DialogProxy
	 *******************************************************************************/
	@Override
	public void showMsgDialog()
	{
		// TODO Auto-generated method stub
		mActivityProxy.showMsgDialog();
	}

	@Override
	public void showMsgDialogWithSize(int width, int height)
	{
		// TODO Auto-generated method stub
		mActivityProxy.showMsgDialogWithSize(width, height);
	}

	@Override
	public void showProgressDialog()
	{
		// TODO Auto-generated method stub
		mActivityProxy.showProgressDialog();
	}

	@Override
	public void cancelProgressDialog()
	{
		// TODO Auto-generated method stub
		mActivityProxy.cancelProgressDialog();
	}

	@Override
	public YiDialogProxy getDialogProxy()
	{
		// TODO Auto-generated method stub
		return mActivityProxy.getDialogProxy();
	}

	@Override
	public void cancelMsgDialog()
	{
		// TODO Auto-generated method stub
		mActivityProxy.cancelMsgDialog();
	}
}
