package za.co.immedia.pinnedheaderlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class PinnedHeaderExpandableListView extends ExpandableListView
		implements OnScrollListener, OnGroupCollapseListener,
		OnGroupExpandListener, OnChildClickListener
{
	private OnScrollListener mOnScrollListener;
	private OnGroupCollapseListener mOnGroupCollapseListener;
	private OnGroupExpandListener mOnGroupExpandListener;
//	private OnChildClickListener mOnChildClickListener;

	private ExpandableListAdapter mAdapter;
	private View mCurrentHeader = null;
	private float mHeaderOffset;
	private boolean mShouldPin = true;
	private int mCurrentSection = -1;
	private int mWidthMode;

	private int[] groupPositionInAllItem;

	@SuppressWarnings("unused")
	private int mHeightMode;

	public PinnedHeaderExpandableListView(Context context)
	{
		super(context);
		super.setOnScrollListener(this);
		super.setOnGroupCollapseListener(this);
		super.setOnGroupExpandListener(this);
		super.setOnChildClickListener(this);
	}

	public PinnedHeaderExpandableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		super.setOnScrollListener(this);
		super.setOnGroupCollapseListener(this);
		super.setOnGroupExpandListener(this);
		super.setOnChildClickListener(this);
	}

	public PinnedHeaderExpandableListView(Context context, AttributeSet attrs,
			int defStyle)
	{
		super(context, attrs, defStyle);
		super.setOnScrollListener(this);
		super.setOnGroupCollapseListener(this);
		super.setOnGroupExpandListener(this);
		super.setOnChildClickListener(this);
	}

	public void setPinHeaders(boolean shouldPin)
	{
		mShouldPin = shouldPin;
	}

	@Override
	public void setAdapter(ExpandableListAdapter adapter)
	{
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		mCurrentHeader = null;
		mAdapter = (ExpandableListAdapter) adapter;
		calculateGroupPosition();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		// TODO Auto-generated method stub
		if (mOnScrollListener != null)
		{
			mOnScrollListener.onScroll(view, firstVisibleItem,
					visibleItemCount, totalItemCount);
		}

		if (mAdapter == null || mAdapter.getGroupCount() == 0 || !mShouldPin
				|| (firstVisibleItem < getHeaderViewsCount()))
		{
			mCurrentHeader = null;
			mHeaderOffset = 0.0f;
			for (int i = firstVisibleItem; i < firstVisibleItem
					+ visibleItemCount; i++)
			{
				View header = getChildAt(i);
				if (header != null)
				{
					header.setVisibility(VISIBLE);
				}
			}
			return;
		}

		firstVisibleItem -= getHeaderViewsCount();

		int section = calculateCurrentGroupPosition(firstVisibleItem);
		mCurrentHeader = getSectionHeaderView(section);
//		ensurePinnedHeaderLayout(mCurrentHeader);

		mHeaderOffset = 0.0f;

		for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++)
		{
			int g = calculateCurrentGroupPosition(i);
			if (section + 1 == g)
			{
				View header = getChildAt(i - firstVisibleItem);
				float headerTop = header.getTop();
				float pinnedHeaderHeight = mCurrentHeader.getMeasuredHeight();
				header.setVisibility(VISIBLE);
				if (pinnedHeaderHeight >= headerTop && headerTop > 0)
				{
					mHeaderOffset = headerTop - header.getHeight();
				}
				else if (headerTop <= 0)
				{
					header.setVisibility(INVISIBLE);
				}
			}
		}

		invalidate();
	}

	private void calculateGroupPosition()
	{
		if (mAdapter == null)
		{
			return;
		}

		int itemPostitons = 0;
		int groupCount = mAdapter.getGroupCount();

		groupPositionInAllItem = new int[groupCount];

		for (int i = 1; i < groupCount; i++)
		{
			// 打开的才计算childCount
			if (isGroupExpanded(i - 1))
			{
				itemPostitons = itemPostitons
						+ mAdapter.getChildrenCount(i - 1) + 1;
				groupPositionInAllItem[i] = itemPostitons;
			}
			else
			{
				itemPostitons = itemPostitons + 1;
				groupPositionInAllItem[i] = itemPostitons;
			}
		}
	}

	private int calculateCurrentGroupPosition(int firstVisibleItem)
	{
		firstVisibleItem++;
		int result = 0;
		int groupCount = mAdapter.getGroupCount();

		for (int i = 0; i < groupCount; i++)
		{
			if (i + 1 < groupCount)
			{
				if (firstVisibleItem >= groupPositionInAllItem[i]
						&& firstVisibleItem <= groupPositionInAllItem[i + 1])
				{
					result = i;
					break;
				}
			}
			else
			{
				if (firstVisibleItem >= groupPositionInAllItem[i])
				{
					result = i;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		// TODO Auto-generated method stub
		if (mOnScrollListener != null)
		{
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	private View getSectionHeaderView(int section)
	{
		boolean shouldLayout = section != mCurrentSection;

		if (shouldLayout)
		{
			View view = mAdapter.getGroupView(section, isGroupExpanded(section),
					null, this);
			// a new section, thus a new header. We should lay it out again
			ensurePinnedHeaderLayout(view);
			mCurrentSection = section;
			return view;
		}
		return mCurrentHeader;
	}

	private void ensurePinnedHeaderLayout(View header)
	{
		if (header.isLayoutRequested())
		{
			int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
					mWidthMode);

			int heightSpec;
			ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
			if (layoutParams != null && layoutParams.height > 0)
			{
				heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height,
						MeasureSpec.EXACTLY);
			}
			else
			{
				heightSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			}
			header.measure(widthSpec, heightSpec);
			header.layout(0, 0, header.getMeasuredWidth(),
					header.getMeasuredHeight());
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		if (mAdapter == null || !mShouldPin || mCurrentHeader == null)
			return;
		int h = getDividerHeight() + mCurrentHeader.getMeasuredHeight();
		int saveCount = canvas.save();
		canvas.translate(0, mHeaderOffset);
		canvas.clipRect(0, 0, getWidth(), h);
		
		mCurrentHeader.draw(canvas);

		Drawable drawable = getDivider();
		if (getDividerHeight() > 0 && drawable != null)
		{
			drawable.setBounds(0, h - getDividerHeight(), getWidth(), h);
			drawable.draw(canvas);
		}

		canvas.restoreToCount(saveCount);
	}

	@Override
	public void setOnScrollListener(OnScrollListener l)
	{
		mOnScrollListener = l;
	}

	@Override
	public void setOnGroupCollapseListener(
			OnGroupCollapseListener onGroupCollapseListener)
	{
		mOnGroupCollapseListener = onGroupCollapseListener;
	}

	@Override
	public void setOnGroupExpandListener(
			OnGroupExpandListener onGroupExpandListener)
	{
		mOnGroupExpandListener = onGroupExpandListener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
	}

	@Override
	public void onGroupExpand(int groupPosition)
	{
		// TODO Auto-generated method stub
		calculateGroupPosition();
		if (mOnGroupExpandListener != null)
		{
			mOnGroupExpandListener.onGroupExpand(groupPosition);
		}
	}

	@Override
	public void onGroupCollapse(int groupPosition)
	{
		// TODO Auto-generated method stub
		calculateGroupPosition();
		if (mOnGroupCollapseListener != null)
		{
			mOnGroupCollapseListener.onGroupCollapse(groupPosition);
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id)
	{
		if(childPosition == getFirstVisiblePosition()) {
			if (isGroupExpanded(groupPosition))
			{
				collapseGroup(groupPosition);
			}else {
				expandGroup(groupPosition);
			}
		}
		return false;
	}
	
}
