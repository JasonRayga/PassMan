package com.example.david.passman.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.david.passman.R;
import com.example.david.passman.helper.FontHelper;

public class FontArrayAdapter extends ArrayAdapter<String> {
	Context context;
	int layoutResourceId;
	String data[] = null;

	private FontHolder _holder;

	public FontArrayAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		this.layoutResourceId = resource;
		this.context = context;
		this.data = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(layoutResourceId, parent, false);

			_holder = new FontHolder();
			_holder.icon = (TextView)view.findViewById(R.id.icon);
			_holder.text1 = (TextView)view.findViewById(R.id.text1);

			FontHelper.setIconFont(_holder.icon);

			view.setTag(_holder);
		} else {
			_holder = (FontHolder)view.getTag();
		}

		String text = data[position];
		_holder.text1.setText(text);

		// set icon
		_holder.icon.setText(FontHelper.getInstance().getIcon(text));

		return view;
	}

	static class FontHolder
	{
		TextView icon;
		TextView text1;
	}
}
