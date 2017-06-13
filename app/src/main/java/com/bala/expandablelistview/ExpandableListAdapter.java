package com.bala.expandablelistview;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/*
 * 继承 Android 系统自带的 BaseExpandableListAdapter
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{

	private Context _context;
	private List<String> header;
	private HashMap<String, List<String>> child;

	public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
		this._context = context;
		this.header = listDataHeader;
		this.child = listChildData;
	}
	
	@Override
	public int getGroupCount() {
		return this.header.size();
	}

	/*
	 * 子布局的数量
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.child.get(this.header.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.header.get(groupPosition);
	}

	/*
	 * getChildView 中会调用到这个
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.child.get(this.header.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		String headerTitle = (String) getGroup(groupPosition);
		
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.header, parent, false);
		}

		TextView header_text = (TextView) convertView.findViewById(R.id.header);
		header_text.setText(headerTitle);

		/*
		 * 设置展开和关闭的效果
		 */
		if (isExpanded) {
			header_text.setTypeface(null, Typeface.BOLD);
			header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up, 0);
		} else {
			header_text.setTypeface(null, Typeface.NORMAL);
			header_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
		}

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition); // 每个子布局的条目

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.childs, parent, false);
		}

		TextView child_text = (TextView) convertView.findViewById(R.id.child);

		child_text.setText(childText);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
