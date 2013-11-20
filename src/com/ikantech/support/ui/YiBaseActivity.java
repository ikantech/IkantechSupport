package com.ikantech.support.ui;

import android.app.Activity;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.ikantech.support.proxy.YiDialogProxy;
import com.ikantech.support.proxy.YiLocalServiceBinderProxy;
import com.ikantech.support.proxy.YiToastProxy;
import com.ikantech.support.proxy.YiDialogProxy.YiDialogProxiable;
import com.ikantech.support.proxy.YiLocalServiceBinderProxy.YiLocalServiceServiceBinderProxiable;
import com.ikantech.support.proxy.YiToastProxy.YiToastProxiable;
import com.ikantech.support.service.YiLocalService.LocalServiceBinder;

public class YiBaseActivity extends Activity implements YiToastProxiable,
		YiLocalServiceServiceBinderProxiable, YiDialogProxiable {
	// Toast代理
	private YiToastProxy mToastProxy = null;
	// 本地Service绑定代理
	private YiLocalServiceBinderProxy mLocalServiceBinderProxy = null;
	// 对话框代理
	private YiDialogProxy mDialogProxy = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		if (mDialogProxy != null) {
			mDialogProxy.cancelMsgDialog();
		}
		super.onDestroy();
	}

	/*******************************************************************************
	 * ToastProxy
	 *******************************************************************************/
	@Override
	public void showToast(int resourceId) {
		initToastProxy();
		mToastProxy.showToast(resourceId);
	}

	@Override
	public void showToast(String text) {
		initToastProxy();
		mToastProxy.showToast(text);
	}

	protected void initToastProxy() {
		if (mToastProxy == null) {
			mToastProxy = new YiToastProxy(this);
		}
	}

	/*******************************************************************************
	 * LocalServiceBinderProxy
	 *******************************************************************************/
	protected void initLocalServiceBinderProxy() {
		if (mLocalServiceBinderProxy == null) {
			mLocalServiceBinderProxy = new YiLocalServiceBinderProxy(this);
		}
	}

	@Override
	public void installLocalServiceBinder() {
		// TODO Auto-generated method stub
		initLocalServiceBinderProxy();
		mLocalServiceBinderProxy.installLocalServiceBinder();
	}

	@Override
	public void installLocalServiceBinder(ServiceConnection connection) {
		// TODO Auto-generated method stub
		initLocalServiceBinderProxy();
		mLocalServiceBinderProxy.installLocalServiceBinder(connection);
	}

	@Override
	public void uninstallLocalServiceBinder() {
		// TODO Auto-generated method stub
		mLocalServiceBinderProxy.uninstallLocalServiceBinder();
	}

	@Override
	public LocalServiceBinder getLocalService() {
		// TODO Auto-generated method stub
		return mLocalServiceBinderProxy.getLocalService();
	}

	/*******************************************************************************
	 * DialogProxy
	 *******************************************************************************/
	protected void initDialogProxy() {
		if (mDialogProxy == null) {
			mDialogProxy = new YiDialogProxy(this);
		}
	}

	@Override
	public void showMsgDialog() {
		// TODO Auto-generated method stub
		initDialogProxy();
		mDialogProxy.showMsgDialog();
	}

	@Override
	public void showMsgDialog(int width, int height) {
		// TODO Auto-generated method stub
		initDialogProxy();
		mDialogProxy.showMsgDialog(width, height);
	}

	@Override
	public YiDialogProxy getMsgDialog() {
		// TODO Auto-generated method stub
		initDialogProxy();
		return mDialogProxy;
	}

	@Override
	public void cancelMsgDialog() {
		// TODO Auto-generated method stub
		initDialogProxy();
		mDialogProxy.cancelMsgDialog();
	}
}
