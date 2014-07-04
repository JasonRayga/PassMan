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
		int id 			= Integer.parseInt(siteArray[0]);
		String site 	= siteArray[1];
		String password = siteArray[2];

		UserDataSite siteObj = new UserDataSite(id, site, password);

		sites.add(siteObj);

		return siteObj;
	}

	public UserDataSite getSiteById(int id) {
		for(UserDataSite site : sites) {
			if(site.get_id() == id) {
				return site;
			}
		}
		return null;
	}

}
