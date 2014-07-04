package com.example.david.passman.adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.david.passman.R;

public class FontArrayAdapter extends ArrayAdapter<String> {
	Context context;
	int layoutResourceId;
	String data[] = null;

	public FontArrayAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		this.layoutResourceId = resource;
		this.context = context;
		this.data = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		FontHolder holder;

		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(layoutResourceId, parent, false);

			holder = new FontHolder();
			holder.icon = (TextView)view.findViewById(R.id.icon);
			holder.text1 = (TextView)view.findViewById(R.id.text1);

			Typeface font = Typeface.createFromAsset(view.getResources().getAssets(), "FontAwesome.otf");
			holder.icon.setTypeface(font);

			view.setTag(holder);
		} else {
			holder = (FontHolder)view.getTag();
		}

		String text = data[position];
		holder.text1.setText(text);
		//holder.icon.setText("fa-folder");
		//holder.icon.setText("&#xf07b;");

		return view;
	}

	static class FontHolder
	{
		TextView icon;
		TextView text1;
	}
}
