package com.example.david.passman.data;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader extends ContextWrapper {

	public FileReader(Context base) {
		super(base);
	}

	public void write(String filename, String data) {
		// write file
		try {
			FileOutputStream fs = openFileOutput(filename, Context.MODE_PRIVATE);
			fs.write(data.getBytes());
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> read(String filename) {
		try {
			InputStreamReader stream = null;
			try {
				stream = new InputStreamReader(openFileInput(filename));
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(stream != null) {
				BufferedReader inputReader = new BufferedReader(stream);
				String inputString;
				ArrayList<String> values = new ArrayList<String>();

				while ((inputString = inputReader.readLine()) != null) {
					values.add(inputString);
				}

				return values;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
