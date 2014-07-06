package com.example.david.passman.helper;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.TextView;

import java.nio.charset.Charset;

public class FontHelper {
	private static FontHelper ourInstance = new FontHelper();

	public static FontHelper getInstance() {
		return ourInstance;
	}

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private static String utf8_encode(String input) {
		return new String(input.getBytes(UTF_8), UTF_8);
	}

	private final static String _fileIcon   = utf8_encode("\uf016");
	private final static String _eyeIcon    = utf8_encode("\uf06e");
	private final static String _folderIcon = utf8_encode("\uf07b");

	private final static String[][] _icons = {
			{ "google",     utf8_encode("\uf1a0"), "415E9B" },
			{ "facebook",   utf8_encode("\uf09a") },
			{ "dropbox",    utf8_encode("\uf16b") },
			{ "soundcloud", utf8_encode("\uf1be") },
			{ "twitter",    utf8_encode("\uf099") },
			{ "youtube",    utf8_encode("\uf167") },
			{ "android",    utf8_encode("\uf17b") },
			{ "bitbucket",  utf8_encode("\uF172") },
			{ "dribble",    utf8_encode("\uF17D") },
			{ "flickr",     utf8_encode("\uF16E") },
			{ "github",     utf8_encode("\uF09B") },
			{ "linux",      utf8_encode("\uF17C") },
			{ "kde",        utf8_encode("\uF17C") },
			{ "freebsd",    utf8_encode("\uF17C") },
			{ "ubuntu",     utf8_encode("\uF17C") },
			{ "renren",     utf8_encode("\uF18B") },
			{ "tumblr",     utf8_encode("\uF173") },
			{ "xing",       utf8_encode("\uF169") },
			{ "bitcoin",    utf8_encode("\uF15A") },
			{ "foursquare", utf8_encode("\uF180") },
			{ "gittip",     utf8_encode("\uF184") },
			{ "instagram",  utf8_encode("\uF16D") },
			{ "maxcdn",     utf8_encode("\uF136") },
			{ "skype",      utf8_encode("\uF17E") },
			{ "weibo",      utf8_encode("\uF18A") },
			{ "apple",      utf8_encode("\uF179") },
			{ "plus.google", utf8_encode("\uF0D5") },
			{ "linkedin",   utf8_encode("\uF0E1") },
			{ "pintrest",   utf8_encode("\uF0D2") },
			{ "stackexchange", utf8_encode("\uF16C") },
			{ "windows",    utf8_encode("\uF17A") }
	};

	private FontHelper() {	}

	public CharSequence getIcon(String text) {
		for(String[] icon : _icons) {
			if(text.toLowerCase().contains(icon[0])) {
				return icon[1];
			}
		}
		return _fileIcon;
	}

	public static void setIconFont(TextView textView) {
		AssetManager assetManager = textView.getRootView().getResources().getAssets();
		Typeface font = Typeface.createFromAsset(assetManager, "FontAwesome.otf");
		textView.setTypeface(font);
	}
}
