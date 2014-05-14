package com.ikantech.support.proxy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast代理
 * 
 * @author saint
 * 
 */
public class YiToastProxy
{
	private Context mContext;
	private Toast mToast;

	public YiToastProxy(Context context)
	{
		if (context == null)
		{
			throw new NullPointerException("context non-null");
		}
		mContext = context;
		mToast = null;
	}

	@SuppressLint("ShowToast")
	protected void initToast()
	{
		if (mToast == null)
		{
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
	}

	public void showToast(String text)
	{
		initToast();
		mToast.setText(text);
		mToast.show();
	}

	public void showToast(int resourceId)
	{
		initToast();
		mToast.setText(resourceId);
		mToast.show();
	}

	public interface YiToastProxiable
	{
		void showToast(int resourceId);

		void showToast(String text);
	}
}
