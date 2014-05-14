package com.ikantech.support.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class YiFileUtils
{
	public static final String FILE_SUFFIX = ".ik";

	private static String mStorePath = null;
	private static Context mContext = null;

	private YiFileUtils()
	{
	}

	public static void register(Context context)
	{
		mContext = context;
	}

	public static String getStorePath()
	{
		if (mStorePath == null)
		{
			String path = Environment.getExternalStorageDirectory().getPath();
			if (path == null
					|| !Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED))
			{
				path = mContext.getFilesDir().getPath();
			}
			if (!path.endsWith("/"))
			{
				path = path + "/";
			}
			mStorePath = path;
		}
		return mStorePath;
	}

	public static void deleteFile(File file)
	{
		if (file.exists())
		{
			if (file.isDirectory())
			{
				File[] files = file.listFiles();
				for (File file2 : files)
				{
					if (file2.isDirectory())
					{
						deleteFile(file2);
					}
					else
					{
						file2.deleteOnExit();
					}
				}
			}
			file.deleteOnExit();
		}
	}
}
