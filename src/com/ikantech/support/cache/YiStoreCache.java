package com.ikantech.support.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ikantech.support.util.YiFileUtils;
import com.ikantech.support.util.YiLog;
import com.ikantech.support.util.YiUtils;

public class YiStoreCache extends YiMemoryCache<String, Bitmap>
{
	private static final int JPG_FILE_FORMAT = 1;

	private static final int PNG_FILE_FORMAT = 2;

	public static String IMAGE_CACHE_DIR = "ikantech/";

	private static final String SUB_CACHE_DIR = "cache/";

	public YiStoreCache(int cacheSize)
	{
		super(cacheSize);
	}

	private static String getCacheDir()
	{
		return YiFileUtils.getStorePath() + IMAGE_CACHE_DIR + SUB_CACHE_DIR;
	}

	public static String convertToFileName(String key)
	{
		return getCacheDir() + UUID.nameUUIDFromBytes(key.getBytes())
				+ YiFileUtils.FILE_SUFFIX;
	}

	@Override
	public void cache(String key, Bitmap value)
	{
		if (value == null || YiUtils.isStringInvalid(key))
		{
			return;
		}
		cacheToMemory(key, value);
		cacheToStore(key, value);
	}

	public Bitmap cache(String key, byte[] value)
	{
		if (value == null || value.length < 1 || YiUtils.isStringInvalid(key))
		{
			return null;
		}

		cacheRawData(key, value);

		Bitmap ret = BitmapFactory.decodeByteArray(value, 0, value.length);

		if (ret != null)
		{
			cacheToMemory(key, ret);
		}
		return ret;
	}

	public static void cacheRawData(String key, byte[] value)
	{
		if (value == null || value.length < 1 || YiUtils.isStringInvalid(key))
		{
			return;
		}

		FileOutputStream fOut = null;
		File f = new File(convertToFileName(key));
		try
		{
			File dir = new File(getCacheDir());
			if (!dir.exists())
			{
				dir.mkdirs();
			}

			if (f.exists())
			{
				f.delete();
			}
			f.createNewFile();

			fOut = new FileOutputStream(f);
			fOut.write(value, 0, value.length);
			fOut.flush();
		}
		catch (IOException e)
		{
			if (f.exists())
			{
				f.delete();
			}
			YiLog.getInstance().e(e, "store bitmap to store device failed.");
		}
		finally
		{
			try
			{
				if (fOut != null)
				{
					fOut.close();
					fOut = null;
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	public static String cacheToStore(String key, Bitmap value)
	{
		if (value == null || YiUtils.isStringInvalid(key))
		{
			return null;
		}
		FileOutputStream fOut = null;
		File f = new File(convertToFileName(key));
		try
		{
			File dir = new File(getCacheDir());
			if (!dir.exists())
			{
				dir.mkdirs();
			}

			if (f.exists())
			{
				f.delete();
			}
			f.createNewFile();

			fOut = new FileOutputStream(f);

			int format = getFileFormat(key);
			if (format == JPG_FILE_FORMAT)
			{
				value.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			}
			else if (format == PNG_FILE_FORMAT)
			{
				value.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			}

			fOut.flush();
			return f.getAbsolutePath();
		}
		catch (IOException e)
		{
			if (f.exists())
			{
				f.delete();
			}
			YiLog.getInstance().e(e, "store raw data to store device failed.");
		}
		finally
		{
			try
			{
				if (fOut != null)
				{
					fOut.close();
					fOut = null;
				}
			}
			catch (Exception e)
			{
			}
		}
		return null;
	}

	public static byte[] getRawCacheData(String key)
	{
		FileInputStream fIn = null;
		File f = new File(convertToFileName(key));
		ByteArrayOutputStream fout = null;
		try
		{
			fIn = new FileInputStream(f);
			fout = new ByteArrayOutputStream(fIn.available());

			byte[] buffer = new byte[1024];
			int readed = 0;
			while ((readed = fIn.read(buffer, 0, buffer.length - 1)) != -1)
			{
				fout.write(buffer, 0, readed);
			}
			byte[] ret = fout.toByteArray();
			return ret;
		}
		catch (Exception e)
		{
			YiLog.getInstance().e(e, "get raw data from store device failed.");
		}
		finally
		{
			try
			{
				if (fIn != null)
				{
					fIn.close();
				}
				if (fout != null)
				{
					fout.close();
				}
			}
			catch (Exception e2)
			{
				// TODO: handle exception
			}
		}
		return null;
	}

	@Override
	public Bitmap get(String key)
	{
		if (YiUtils.isStringInvalid(key))
		{
			return null;
		}
		Bitmap ret = getFromMemory(key);
		if (ret == null)
		{
			ret = getFromStore(key);
			if (ret != null)
			{
				cacheToMemory(key, ret);
			}
		}
		return ret;
	}

	public Bitmap getFromPath(String path)
	{
		if (YiUtils.isStringInvalid(path))
		{
			return null;
		}
		Bitmap ret = getFromMemory(path);
		if (ret == null)
		{
			ret = BitmapFactory.decodeFile(path);
			if (ret != null)
			{
				cacheToMemory(path, ret);
			}
		}
		return ret;
	}

	private Bitmap getFromStore(String key)
	{
		if (YiUtils.isStringInvalid(key))
		{
			return null;
		}
		String fileName = convertToFileName(key);
		return BitmapFactory.decodeFile(fileName);
	}

	@Override
	public boolean containsKey(String key)
	{
		if (YiUtils.isStringInvalid(key))
		{
			return false;
		}
		boolean ret = false;
		File file = new File(convertToFileName(key));
		ret = file.exists();

		return memoryCacheContainsKey(key) || ret;
	}

	@Override
	public void removeCache(String key)
	{
		if (YiUtils.isStringInvalid(key))
		{
			return;
		}

		removeMemoryCache(key);

		File file = new File(convertToFileName(key));
		if (file.exists())
		{
			file.delete();
		}
	}

	private static int getFileFormat(String filename)
	{
		if (filename.toUpperCase(Locale.getDefault()).endsWith(".PNG"))
		{
			return PNG_FILE_FORMAT;
		}
		return JPG_FILE_FORMAT;
	}
}
