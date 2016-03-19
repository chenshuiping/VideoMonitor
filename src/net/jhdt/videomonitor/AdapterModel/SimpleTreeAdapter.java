package net.jhdt.videomonitor.AdapterModel;

import java.util.List;

import net.jhdt.videomonitor.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.jhdt.treelistwidget.Model.Node;
import cn.jhdt.treelistwidget.Model.TreeListViewAdapter;

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

	public SimpleTreeAdapter(ListView mTree, Context mContext, List<T> datas,
			int defaultExpandLevel) throws IllegalAccessException,
			IllegalArgumentException {
		super(mTree, mContext, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(Node node, int position, View convertView,
			ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
			viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (node.getIcon() == -1) {
			viewHolder.icon.setVisibility(View.GONE);
		} else {
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());
		}
		viewHolder.label.setText(node.getName());
		return convertView;
	}

	private final class ViewHolder {
		ImageView icon;
		TextView label;
	}
}
