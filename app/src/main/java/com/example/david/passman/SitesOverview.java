package com.example.david.passman;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.david.passman.data.FileReader;
import com.example.david.passman.data.UserData;
import com.example.david.passman.data.UserDataSite;

public class SitesOverview extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	public static final int ACTION_EDIT = 0x00000010;
	public static final int ACTION_NEW 	= 0x00000020;

	public static final int VIEW_OVERVIEW 	= 0;
	public static final int VIEW_FORM 		= 1;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment _navigationDrawerFragment;
	private DrawerLayout _drawerLayout;
	private ViewSwitcher _viewSwitcher;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    //private CharSequence mTitle;

	private UserDataSite 	_currentSite;
	private int 			_currentAction;

	private Boolean _setSiteNameFocus = false;
	private EditText _formSitename;
	private EditText _formPassword;
	private Button _formSitenameIcon;
	private UserData _userData;
	Button btnEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sites_overview);

		_userData 		= UserData.getInstance();
		_drawerLayout 	= (DrawerLayout) findViewById(R.id.drawer_layout);
		_viewSwitcher 	= (ViewSwitcher)findViewById(R.id.viewSwitcher);

		// init fragment
        _navigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);
		_navigationDrawerFragment.setUp(R.id.navigation_drawer, _drawerLayout);
		//mTitle = getTitle();

		_formSitename 		= (EditText) findViewById(R.id.form_sitename);
		_formPassword 		= (EditText) findViewById(R.id.form_password);
		_formSitenameIcon 	= (Button) findViewById(R.id.btn_icon);

		Typeface font = Typeface.createFromAsset(getResources().getAssets(), "FontAwesome.otf");
		btnEye = (Button)findViewById(R.id.btn_eye);
		btnEye.setTypeface(font);

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

		// switch view to form
		_viewSwitcher.setDisplayedChild(VIEW_FORM);

		_fillForm(position);
    }

	@Override
	public void onNavigationDrawerClosed() {
		if(_setSiteNameFocus) {
			// focus sitename
			_formSitename.requestFocus();

			// show keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(_formSitename, InputMethodManager.SHOW_IMPLICIT);

			_setSiteNameFocus = false;
		}
	}

	@Override
	public void onNavigationDrawerOpenOrClose() {
		// hide keyboard
		_hideSoftKeyboard();
	}

	private void _fillForm(int index) {
		// set action
		_currentAction = ACTION_EDIT;

		// get site data
		UserDataSite site = _userData.getSiteByIndex(index);
		_currentSite = site;

		// fill form
		if(_formSitename != null && _formPassword != null) {
			_formSitename.setText(site.get_site(), TextView.BufferType.EDITABLE);
			_formPassword.setText(site.get_password(), TextView.BufferType.EDITABLE);

			//_formSitenameIcon.setText();
		}
	}

	private void _resetForm() {
		_formSitename.setText("");
		_formPassword.setText("");
	}

    public void onSectionAttached(int number) {
		//UserDataSite site = _userData.getSiteById(number);
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
        if (!_navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.sites_overview, menu);
            //restoreActionBar();
        } else {
			getMenuInflater().inflate(R.menu.navigation_drawer, menu);
		}
		//return super.onCreateOptionsMenu(menu);
		return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

		switch(id) {
			case R.id.action_save:
				return _saveSiteAction();
			case R.id.action_delete:
				return _deleteSiteAction();
			case R.id.action_add:
				return _addNewSiteAction();
		}

	    return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

	private Boolean _saveSiteAction() {
		String sitename = _formSitename.getText().toString();
		String password = _formPassword.getText().toString();

		// validate form
		if(sitename.equals("") || password.equals("")) {
			Toast.makeText(this, "Please enter a Sitename and a Password.", Toast.LENGTH_SHORT).show();
			return true;
		}

		String dataStr 	= "";

		if(_currentAction == ACTION_NEW) {
			// initiualize new site
			int id = _userData.getNewId();
			_currentSite = _userData.addSite(id, sitename, password);
		} else if(_currentAction == ACTION_EDIT) {
			// update site
			_currentSite.set_site(sitename);
			_currentSite.set_password(password);
		}

		int i = 0;
		int itemIndex = 0;
		// parse sites
		for(UserDataSite site : _userData.sites) {
			if(i != 0) dataStr += "\n";
			dataStr += site.get_id() +","+ site.get_site() +","+ site.get_password();

			if(site.get_id() == _currentSite.get_id()) {
				itemIndex = i;
			}

			i++;
		}

		// write
		FileReader fileReader = new FileReader(this);
		fileReader.write("sites", dataStr);

		// hide keyboard
		_hideSoftKeyboard();

		// message
		Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show();

		// update navigation fragment
		_navigationDrawerFragment.updateItems(itemIndex);

		_currentAction = ACTION_EDIT;

		return true;
	}

	private Boolean _deleteSiteAction() {
		String dataStr = "";
		int i = 0;

		// remove site
		_userData.removeSite(_currentSite.get_id());

		// parse sites
		for(UserDataSite site : _userData.sites) {
			if(i != 0) dataStr += "\n";
			dataStr += site.get_id() +","+ site.get_site() +","+ site.get_password();

			i++;
		}

		// write
		FileReader fileReader = new FileReader(this);
		fileReader.write("sites", dataStr);

		// message
		Toast.makeText(this, "Deleted.", Toast.LENGTH_SHORT).show();

		_currentSite = null;

		// update navigation fragment
		_navigationDrawerFragment.updateItems(-1);

		// switch back to overview
		_viewSwitcher.setDisplayedChild(VIEW_OVERVIEW);

		return true;
	}

	private Boolean _addNewSiteAction() {
		// set action
		_currentAction = ACTION_NEW;

		// deselect navigation drawer
		_navigationDrawerFragment.deselectItems();

		// clear form entries
		_resetForm();

		// switch to form
		_viewSwitcher.setDisplayedChild(VIEW_FORM);

		// close drawer, set focus in onNavigationDrawerClosed() callback
		_setSiteNameFocus = true;
		_drawerLayout.closeDrawer(findViewById(R.id.navigation_drawer));

		return true;
	}

	private void _hideSoftKeyboard() {
		// remove focus
		_formSitename.clearFocus();
		_formPassword.clearFocus();

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(_formSitename.getWindowToken(), 0);
	}

	public void onTogglePassword(View v) {
		Boolean show = !(_formPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

		int selectionStart 	= _formPassword.getSelectionStart();
		int selectionEnd 	= _formPassword.getSelectionEnd();

		// InputType.TYPE_TEXT_VARIATION_PASSWORD is 128, however, I want 129 (different font on password)

		_formPassword.setInputType(show ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : 129);
		_formPassword.setTransformationMethod(show ? null : PasswordTransformationMethod.getInstance());
		_formPassword.setSelection(selectionStart, selectionEnd);
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
