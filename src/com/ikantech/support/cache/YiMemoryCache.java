package com.ikantech.support.cache;

//内存缓存
public class YiMemoryCache<K, V> extends InAbsCache<K, V>
{
	public YiMemoryCache(int cacheSize)
	{
		super(cacheSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cache(K key, V value)
	{
		// TODO Auto-generated method stub
		cacheToMemory(key, value);
	}

	@Override
	public V get(K key)
	{
		// TODO Auto-generated method stub
		return getFromMemory(key);
	}

	@Override
	public boolean containsKey(K key)
	{
		// TODO Auto-generated method stub
		return memoryCacheContainsKey(key);
	}

	@Override
	public void removeCache(K key)
	{
		// TODO Auto-generated method stub
		removeMemoryCache(key);
	}
}
