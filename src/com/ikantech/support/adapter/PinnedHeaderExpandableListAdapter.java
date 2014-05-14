
package com.ikantech.support.adapter;

import android.view.View;
import android.widget.BaseExpandableListAdapter;

abstract class PinnedHeaderExpandableListAdapter extends BaseExpandableListAdapter {

    /**
     * 根据groupPosition 获取GroupView
     * 
     * @param groupPosition 第几个group
     * @return
     */
    public abstract View getGroupView(int groupPosition);
}