package com.ikantech.support.widget;

import android.content.ServiceConnection;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ikantech.support.R;
import com.ikantech.support.proxy.YiActivityProxy;
import com.ikantech.support.proxy.YiDialogProxy;
import com.ikantech.support.proxy.YiDialogProxy.YiDialogExtProxiable;
import com.ikantech.support.proxy.YiDialogProxy.YiDialogProxiable;
import com.ikantech.support.proxy.YiHandlerProxy;
import com.ikantech.support.proxy.YiHandlerProxy.YiHandlerProxiable;
import com.ikantech.support.proxy.YiLocalServiceBinderProxy.YiLocalServiceServiceBinderProxiable;
import com.ikantech.support.proxy.YiToastProxy.YiToastProxiable;
import com.ikantech.support.service.YiLocalService.YiLocalServiceBinder;

public abstract class YiFragment extends Fragment implements
		YiHandlerProxiable, YiToastProxiable, YiDialogProxiable,
		YiDialogExtProxiable, YiLocalServiceServiceBinderProxiable
{
	private YiHandlerProxy mHandlerProxy;
	protected YiActivityProxy mActivityProxy;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mActivityProxy = new YiActivityProxy(getActivity());
	}

	@Override
	public void onDestroy()
	{
		mActivityProxy.onDestroy();
		super.onDestroy();
	}

	/*******************************************************************************
	 * HandlerProxy
	 *******************************************************************************/
	protected void initHandlerProxy()
	{
		if (mHandlerProxy == null)
		{
			mHandlerProxy = new YiHandlerProxy(getActivity(), this);
		}
	}

	@Override
	public Handler getHandler()
	{
		initHandlerProxy();
		return mHandlerProxy.getHandler();
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
	
	public void showMsgDialog(int res)
	{
		showMsgDialog(getString(res));
	}

	public void showMsgDialog(String detials)
	{
		showMsgDialog(null, detials, getString(R.string.str_ok), null, null,
				null);
	}

	public void showMsgDialog(String detials, String btnLeft)
	{
		showMsgDialog(null, detials, btnLeft, null, null, null);
	}

	public void showMsgDialog(String title, String detials, String btnLeft)
	{
		showMsgDialog(title, detials, btnLeft, null, null, null);
	}

	public void showMsgDialog(String detials, String btnLeft,
			View.OnClickListener btnLeftListener)
	{
		showMsgDialog(null, detials, btnLeft, null, btnLeftListener, null);
	}

	public void showMsgDialog(String title, String detials, String btnLeft,
			String btnRight, View.OnClickListener btnLeftListener,
			View.OnClickListener btnRightListener)
	{
		mActivityProxy.showMsgDialog(title, detials, btnLeft, btnRight,
				btnLeftListener, btnRightListener);
	}

	public void showProgressDialog(int resid)
	{
		showProgressDialog(getString(resid), null, true);
	}

	public void showProgressDialog(String msg)
	{
		showProgressDialog(msg, null, true);
	}

	public void showProgressDialog(String msg, OnCancelListener listener,
			boolean cancelable)
	{
		mActivityProxy.showProgressDialog(msg, listener, cancelable);
	}
}
