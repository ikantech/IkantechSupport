package com.ikantech.support.ui;

import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;

import com.ikantech.support.R;
import com.ikantech.support.proxy.YiDialogProxy.YiDialogExtProxiable;
import com.ikantech.support.proxy.YiHandlerProxy;
import com.ikantech.support.proxy.YiHandlerProxy.YiHandlerProxiable;
import com.ikantech.support.util.YiUtils;

public abstract class YiUIBaseActivity extends YiBaseActivity implements
		YiHandlerProxiable, YiDialogExtProxiable
{
	private YiHandlerProxy mHandlerProxy;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initViews();
		initDatas();
		installListeners();
	}

	@Override
	protected void onDestroy()
	{
		uninstallListeners();
		super.onDestroy();
	}

	protected abstract void initViews();

	protected abstract void initDatas();

	protected abstract void installListeners();

	protected abstract void uninstallListeners();

	protected boolean isStringInvalid(String str)
	{
		return YiUtils.isStringInvalid(str);
	}

	protected boolean isStringInvalid(Editable editable)
	{
		return YiUtils.isStringInvalid(editable.toString());
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

	/*******************************************************************************
	 * HandlerProxy
	 *******************************************************************************/
	protected void initHandlerProxy()
	{
		if (mHandlerProxy == null)
		{
			mHandlerProxy = new YiHandlerProxy(this, this);
		}
	}

	@Override
	public Handler getHandler()
	{
		initHandlerProxy();
		return mHandlerProxy.getHandler();
	}
}
