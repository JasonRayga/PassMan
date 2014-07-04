package com.example.david.passman;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.david.passman.data.FileReader;
import com.example.david.passman.data.UserData;
import com.example.david.passman.data.UserDataSite;

public class SitesOverview extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    //private CharSequence mTitle;

	private int _curId;
	EditText form_sitename;
	EditText form_password;
	Button btn_eye;
	UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sites_overview);

		userData = UserData.getInstance();

		// init fragment
        mNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);
		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		//mTitle = getTitle();

		form_sitename = (EditText) findViewById(R.id.form_sitename);
		form_password = (EditText) findViewById(R.id.form_password);

		Typeface font = Typeface.createFromAsset(getResources().getAssets(), "FontAwesome.otf");
		btn_eye = (Button)findViewById(R.id.btn_eye);
		btn_eye.setTypeface(font);

		ActionBar actionBar = getActionBar();
		if(actionBar != null) {
			actionBar.setTitle(getString(R.string.string_app_name));
		}
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
		int id = position + 1;
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
				.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(id))
                .commit();

		_fillForm(id);
    }

	private void _fillForm(int id) {
		_curId = id;

		// get site data
		UserDataSite site = userData.getSiteById(id);

		// fill form
		if(form_sitename != null && form_password != null) {
			form_sitename.setText(site.get_site(), TextView.BufferType.EDITABLE);
			form_password.setText(site.get_password(), TextView.BufferType.EDITABLE);
		}
	}

    public void onSectionAttached(int number) {
		//UserDataSite site = userData.getSiteById(number);
		//mTitle = site.get_site();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
		if(actionBar != null) {
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			actionBar.setDisplayShowTitleEnabled(true);
			//actionBar.setTitle(mTitle);
		}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.sites_overview, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

		if (id == R.id.action_save) {
			this._saveSite();

			return true;
		}

	    return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

	private void _saveSite() {
		String sitename = form_sitename.getText().toString();
		String password = form_password.getText().toString();
		String dataStr 	= "";

		// update user data
		UserDataSite userDataSite = userData.getSiteById(_curId);
		userDataSite.set_site(sitename);
		userDataSite.set_password(password);

		int i = 0;
		// parse existing sites
		for(UserDataSite site : userData.sites) {
			if(i != 0) dataStr += "\n";

			dataStr += site.get_id() +","+ site.get_site() +","+ site.get_password();

			i++;
		}

		// write
		FileReader fileReader = new FileReader(this);
		fileReader.write("sites", dataStr);

		// message
		Toast.makeText(this, "Saved " + sitename, Toast.LENGTH_SHORT).show();

		// update navigation fragment
		mNavigationDrawerFragment.updateItems(_curId - 1);
	}

	public void onTogglePassword(View v) {
		Boolean show = !(form_password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

		int selectionStart 	= form_password.getSelectionStart();
		int selectionEnd 	= form_password.getSelectionEnd();

		form_password.setInputType(show ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD);
		form_password.setTransformationMethod(show ? null : PasswordTransformationMethod.getInstance());
		form_password.setSelection(selectionStart, selectionEnd);
	}

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_overview, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((SitesOverview) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
