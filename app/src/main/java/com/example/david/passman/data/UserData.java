package com.example.david.passman.data;

import android.content.SharedPreferences;
import com.example.david.passman.encryption.RandomGenerator;

import java.util.ArrayList;

public class UserData {
	private static UserData ourInstance = new UserData();

	public static UserData getInstance() {
		return ourInstance;
	}

	public ArrayList<UserDataSite> sites = new ArrayList<UserDataSite>();
	public UserDataSettings settings;

	private UserData() {}

	public void resetSites() {
		sites.clear();
	}

	public UserDataSite addSite(String[] siteArray) {
		int id = 0;
		String site = "";
		String password = "";
		String username = "";

		try {
			id = Integer.parseInt(siteArray[0]);
		} catch(Exception e) {
			throw  new RuntimeException(e);
		}

		try {
			site = siteArray[1];
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			password = siteArray[2];
		} catch(Exception e) {
			e.printStackTrace();
		}

		try {
			username = siteArray[3];
		} catch(Exception e) {
			e.printStackTrace();
		}

		UserDataSite siteObj = new UserDataSite(id, site, password, username);

		sites.add(siteObj);

		return siteObj;
	}

	public UserDataSite addSite(int id, String site, String password, String username) {
		String[] values = { Integer.toString(id), site, password, username };

		return addSite(values);
	}

	public Boolean removeSite(int id) {
		int i = 0;
		for(UserDataSite site : sites) {
			if(site.get_id() == id) {
				sites.remove(i);
				return true;
			}
			i++;
		}
		return false;
	}

	public UserDataSite getSiteById(int id) {
		for(UserDataSite site : sites) {
			if(site.get_id() == id) {
				return site;
			}
		}
		return null;
	}

	public UserDataSite getSiteByIndex(int index) {
		UserDataSite site = null;
		try {
			site = sites.get(index);
		} catch(Exception e) {
			e.fillInStackTrace();
		}

		return site;
	}

	public int getNewId() {
		int id = 0;
		for(UserDataSite site : sites) {
			id = Math.max(id, site.get_id());
		}

		return ++id;
	}

	public UserDataSettings updateSettings(SharedPreferences sp) {
		String displayName      = sp.getString("display_name", "John Doe");
		String password         = sp.getString("master_password", "test");
		String encryptionKey    = sp.getString("encryption_key", RandomGenerator.getInstance().get(32));

		settings = new UserDataSettings(displayName, password, encryptionKey);
		return settings;
	}

}
