package com.example.david.passman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.example.david.passman.data.FileReader;
import com.example.david.passman.data.UserData;

import java.util.ArrayList;


public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

	    if(id == R.id.action_settings) {
		    Intent intent = new Intent(this, Settings.class);
		    startActivity(intent);
	    }

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void onLogin(View v) {
        EditText password = (EditText) findViewById(R.id.password);
        String passwordStr = password.getText().toString();

		// TODO: get preferences to compare password

        if(passwordStr.equals("test")) {
	        _readSites();

            Intent intent = new Intent(this, SitesOverview.class);
            startActivity(intent);
        }
    }

	private void _readSites() {
		// check if file exists
		FileReader reader = new FileReader(this, FileReader.SITES);
		ArrayList<String> sites = reader.read();

		UserData userData = UserData.getInstance();
		userData.resetSites();

		String[] siteArray;
		for(String site : sites) {
			siteArray = site.split(",", -1);
			userData.addSite(siteArray);
		}
	}
}
