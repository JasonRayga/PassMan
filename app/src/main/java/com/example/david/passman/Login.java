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
	        /*String sites = "" +
			        "1,facebook.com,geheimesp4ssw0rd\n" +
			        "2,google.com,n0chgh3im3ere5pW\n" +
					"3,amazon.de,amazon99#\n" +
					"4,ebay.de,ebay99123#"
					;


		    // write file
			FileReader reader = new FileReader(this);
			reader.write("sites", sites);*/

	        _readSites();

            Intent intent = new Intent(this, SitesOverview.class);
            startActivity(intent);
        }
    }

	private void _readSites() {
		FileReader reader = new FileReader(this);
		ArrayList<String> sites = reader.read("sites");

		UserData userData = UserData.getInstance();
		userData.resetSites();

		String[] siteArray;
		for(String site : sites) {
			siteArray = site.split(",", -1);
			userData.addSite(siteArray);
		}
	}
}
