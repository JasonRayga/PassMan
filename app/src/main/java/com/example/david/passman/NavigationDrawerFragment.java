package com.example.david.passman;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.david.passman.adapter.FontArrayAdapter;
import com.example.david.passman.data.UserData;
import com.example.david.passman.data.UserDataSite;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks _callbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle _btnToggle;

    private DrawerLayout _layout;
    private ListView _listView;
    private View _containerView;

    private int _currentPosition = 0;
    private boolean _fromSavedInstanceState;
    private boolean _userLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        _userLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            _currentPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            _fromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        // selectItem(_currentPosition);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _listView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		});

		updateItems(-1);

		return _listView;
    }

	public void updateItems(int selectedItemIndex) {
		// get user data
		UserData userData = UserData.getInstance();
		String[] sites = new String[userData.sites.size()];
		int i = 0;
		for(UserDataSite site : userData.sites) {
			sites[i] = site.get_site();
			i++;
		}

		_listView.setAdapter(new FontArrayAdapter(
				getActionBar().getThemedContext(),
				R.layout.fragment_navigation_site_list_item,
				sites));

		if(selectedItemIndex > -1) {
			// select item
			_listView.setItemChecked(selectedItemIndex, true);
		}
	}

	public void deselectItems() {
		_listView.setItemChecked(_currentPosition, false);
	}

    public boolean isDrawerOpen() {
        return _layout != null && _layout.isDrawerOpen(_containerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        _containerView = getActivity().findViewById(fragmentId);
        _layout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        _layout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        _btnToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
				_layout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.string_navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.string_avigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()

				_callbacks.onNavigationDrawerClosed();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!_userLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    _userLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

			@Override
			public void onDrawerStateChanged(int newState) {
				if(newState == 2) {
					// open/close drawer
					_callbacks.onNavigationDrawerOpenOrClose();
				}
			}
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!_userLearnedDrawer && !_fromSavedInstanceState) {
            _layout.openDrawer(_containerView);
        }

        // Defer code dependent on restoration of previous instance state.
        _layout.post(new Runnable() {
            @Override
            public void run() {
                _btnToggle.syncState();
            }
        });

        _layout.setDrawerListener(_btnToggle);
    }

    private void selectItem(int position) {
        _currentPosition = position;
        if (_listView != null) {
            _listView.setItemChecked(position, true);
        }
        if (_layout != null) {
            _layout.closeDrawer(_containerView);
        }
        if (_callbacks != null) {
            _callbacks.onNavigationDrawerItemSelected(position);
        }
	}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _callbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _callbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, _currentPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        _btnToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (_layout != null && isDrawerOpen()) {
            //inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		return _btnToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.string_app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);

		void onNavigationDrawerClosed();

		void onNavigationDrawerOpenOrClose();
    }
}
