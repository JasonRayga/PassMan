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

			Typeface font = Typeface.createFromAsset(view.getResources().getAssets(), "FontAwesome.otf");
			_holder.icon.setTypeface(font);

			view.setTag(_holder);
		} else {
			_holder = (FontHolder)view.getTag();
		}

		String text = data[position];
		_holder.text1.setText(text);

		_setIcon(text);

		return view;
	}

	private void _setIcon(String text) {
		// set brand icons
		if(text.toLowerCase().contains("facebook")) {
			// facebook
			_holder.icon.setText(getContext().getString(R.string.icon_facebook));
		} else if(text.toLowerCase().contains("twitter")) {
			// twitter
			_holder.icon.setText(getContext().getString(R.string.icon_twitter));
		} else if(text.toLowerCase().contains("soundcloud")) {
			// soundcloud
			_holder.icon.setText(getContext().getString(R.string.icon_soundcloud));
		} else if(text.toLowerCase().contains("google")) {
			// google
			_holder.icon.setText(getContext().getString(R.string.icon_google));
		} else if(text.toLowerCase().contains("dropbox")) {
			// dropbox
			_holder.icon.setText(getContext().getString(R.string.icon_dropbox));
		} else if(text.toLowerCase().contains("youtube")) {
			// youtube
			_holder.icon.setText(getContext().getString(R.string.icon_youtube));
		} else if(text.toLowerCase().contains("android")) {
			// android
			_holder.icon.setText(getContext().getString(R.string.icon_android));
		}
	}

	static class FontHolder
	{
		TextView icon;
		TextView text1;
	}
}
