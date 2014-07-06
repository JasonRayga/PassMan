package com.example.david.passman.data;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.*;
import java.util.ArrayList;

public class FileReader extends ContextWrapper {

	public final static String SITES = "sites";

	private String _filename;

	public FileReader(Context base, String filename) {
		super(base);
		_filename = filename;
	}

	public Boolean fileExists() {
		File f = new File(_filename);
		return f.exists() && !f.isDirectory();
	}

	public void write(String data) {
		// write file
		try {
			FileOutputStream fs = openFileOutput(_filename, Context.MODE_PRIVATE);
			fs.write(data.getBytes());
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> read() {
		try {
			InputStreamReader stream;
			try {
				stream = new InputStreamReader(openFileInput(_filename));
			} catch (Exception e) {
				write("");
				stream = new InputStreamReader(openFileInput(_filename));
			}

			BufferedReader inputReader = new BufferedReader(stream);
			String inputString;
			ArrayList<String> values = new ArrayList<String>();

			while ((inputString = inputReader.readLine()) != null) {
				values.add(inputString);
			}

			return values;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
