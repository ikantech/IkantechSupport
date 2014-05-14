package com.ikantech.support.adapter;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

public abstract class PinnedHeaderExpandableListViewAdapter extends
		PinnedHeaderExpandableListAdapter
{
	/** 缓存所有group 的views */
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, View> groupViews = new HashMap<Integer, View>();

	@Override
	public View getGroupView(int groupPosition)
	{
		return groupViews.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent)
	{
		View view = getGroupHeaderView(groupPosition, isExpanded, convertView,
				parent);
		groupViews.put(Integer.valueOf(groupPosition), view);
		return view;
	}

	public abstract View getGroupHeaderView(int groupPosition,
			boolean isExpanded, View convertView, ViewGroup parent);

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}
}
