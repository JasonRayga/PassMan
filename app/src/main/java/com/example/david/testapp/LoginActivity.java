package com.example.david.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.example.david.testapp.data.UserData;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void onLogin(View v) {
        EditText password = (EditText) findViewById(R.id.password);
        String passwordStr = password.getText().toString();

        if(passwordStr.equals("test")) {

	        // dummy users data
	        String sites = "" +
			        "1,facebook.com,geheimesp4ssw0rd\n" +
			        "2,google.com,n0chgh3im3ere5pW";


		    // write file
		    try {
			    FileOutputStream fs = openFileOutput("sites", Context.MODE_PRIVATE);
			    fs.write(sites.getBytes());
			    fs.close();
		    } catch (Exception e) {
			    e.printStackTrace();
			    System.out.println("ERROR!");
		    }

	        _readUserDataFromFile();

            Intent intent = new Intent(this, Overview.class);
            startActivity(intent);
        }
    }

	private void _readUserDataFromFile() {
		// read file
		try {
			InputStreamReader stream = null;
			try {
				stream = new InputStreamReader(openFileInput("sites"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(stream != null) {
				BufferedReader inputReader = new BufferedReader(stream);
				String inputString;
				String[] siteArray;
				UserData userData = UserData.getInstance();

				while ((inputString = inputReader.readLine()) != null) {
					siteArray = inputString.split(",", -1);
					// siteArray[0] = id
					// siteArray[1] = site
					// siteArray[2] = password
					userData.addSite(siteArray);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
