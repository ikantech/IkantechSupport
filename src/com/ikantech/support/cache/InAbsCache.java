package com.ikantech.support.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.ikantech.support.util.YiLRUMap;

public abstract class InAbsCache<K, V>
{
	protected static final int DEFAULT_CACHE_SIZE = 32;

	private HashMap<K, V> mHardCache;

	/**
	 * 当mHardCache的key大于Cache Size的时候，会根据LRU算法把最近没有被使用的key放入到这个缓存中。
	 * T使用了SoftReference，当内存空间不足时，此cache中的T会被垃圾回收掉
	 */
	private ConcurrentHashMap<K, SoftReference<V>> mSoftCache;

	private int mCacheSize;

	@SuppressWarnings("serial")
	public InAbsCache(int cacheSize)
	{
		if (cacheSize < 1)
		{
			cacheSize = DEFAULT_CACHE_SIZE;
		}
		mCacheSize = cacheSize;
		mSoftCache = new ConcurrentHashMap<K, SoftReference<V>>(mCacheSize / 2);

		mHardCache = new YiLRUMap<K, V>(mCacheSize)
		{
			@Override
			protected boolean removeEldestEntry(Entry<K, V> eldest)
			{
				// TODO Auto-generated method stub
				if (size() > mCacheSize)
				{
					// 当map的size大于mCacheSize时，把最近不常用的key放到mSoftCache中，从而实现延迟缓存从内存中清除
					mSoftCache.put(eldest.getKey(),
							new SoftReference<V>(eldest.getValue()));
				}
				return super.removeEldestEntry(eldest);
			}
		};
	}

	public abstract void cache(K key, V value);

	public abstract V get(K key);

	public abstract void removeCache(K key);

	public abstract boolean containsKey(K key);

	protected boolean memoryCacheContainsKey(K key)
	{
		boolean flag1 = false;
		boolean flag2 = false;
		synchronized (mHardCache)
		{
			flag1 = mHardCache.containsKey(key);
		}
		synchronized (mSoftCache)
		{
			flag2 = mSoftCache.containsKey(key);
		}
		return flag1 || flag2;
	}

	public void removeMemoryCache(K key)
	{
		synchronized (mHardCache)
		{
			mHardCache.remove(key);
		}
		synchronized (mSoftCache)
		{
			mSoftCache.remove(key);
		}
	}

	protected void cacheToMemory(K key, V t)
	{
		synchronized (mHardCache)
		{
			mHardCache.put(key, t);
		}
	}

	protected V getFromMemory(K key)
	{
		// 先从HardCache缓存中获取
		synchronized (mHardCache)
		{
			final V t = mHardCache.get(key);
			if (t != null)
			{
				// 如果找到的话，把元素移到linkedhashmap的最前面，从而保证在LRU算法中是最后被删除
				mHardCache.remove(key);
				mHardCache.put(key, t);
				return t;
			}
		}

		// 如果HardCache中找不到，到SoftCache中找
		synchronized (mSoftCache)
		{
			SoftReference<V> softT = mSoftCache.get(key);
			if (softT != null)
			{
				final V t = softT.get();
				if (t != null)
				{
					// 将SoftCache重新放入HardCache
					mSoftCache.remove(key);
					mHardCache.put(key, t);
					return t;
				}
				else
				{
					mSoftCache.remove(key);
				}
			}
		}
		return null;
	}
}
