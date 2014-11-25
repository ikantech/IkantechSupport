package com.ikantech.support.util;

import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

public class YiUtils
{
	private YiUtils()
	{

	}

	public static boolean isStringInvalid(String str)
	{
		if (str == null || str.length() < 1)
		{
			return true;
		}
		return false;
	}

	public static boolean isInRangeOfView(View view, MotionEvent ev)
	{
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		if (ev.getRawX() < x || ev.getRawX() > (x + view.getWidth())
				|| ev.getRawY() < y || ev.getRawY() > (y + view.getHeight()))
		{
			return false;
		}
		return true;
	}

	/**
	 * send broadcast
	 * 
	 * @param context
	 *            app context
	 * @param action
	 *            广播Action Name
	 * @param params
	 *            广播参数
	 */
	public static void broadcast(Context context, String action,
			Map<String, String> params)
	{
		Intent intent = new Intent(action);
		if (params != null)
		{
			Set<String> keys = params.keySet();
			for (String string : keys)
			{
				intent.putExtra(string, params.get(string));
			}
		}
		context.sendBroadcast(intent);
	}
}
