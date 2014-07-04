package com.example.david.passman.data;

import java.util.ArrayList;

public class UserData {
	private static UserData ourInstance = new UserData();

	public static UserData getInstance() {
		return ourInstance;
	}

	public ArrayList<UserDataSite> sites = new ArrayList<UserDataSite>();

	private UserData() {}

	public void resetSites() {
		sites.clear();
	}

	public UserDataSite addSite(String[] siteArray) {
		int id;
		String site;
		String password;

		try {
			id 		 = Integer.parseInt(siteArray[0]);
			site 	 = siteArray[1];
			password = siteArray[2];
		} catch(Exception e) {
			throw new RuntimeException(e);
		}

		UserDataSite siteObj = new UserDataSite(id, site, password);

		sites.add(siteObj);

		return siteObj;
	}

	public UserDataSite addSite(int id, String site, String password) {
		String[] values = { Integer.toString(id), site, password };

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

}
