package za.co.immedia.pinnedheaderlistview;

import android.view.View;
import android.view.ViewGroup;

public interface PinnedSectionedHeaderAdapter
{
	public boolean isSectionHeader(int position);

	public int getSectionForPosition(int position);

	public View getSectionHeaderView(int section, View convertView,
			ViewGroup parent);

	public int getSectionHeaderViewType(int section);

	public int getCount();
}
