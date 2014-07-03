package com.example.david.testapp.data;

import java.util.ArrayList;

public class UserData {
	private static UserData ourInstance = new UserData();

	public static UserData getInstance() {
		return ourInstance;
	}

	public ArrayList<UserDataSite> sites = new ArrayList<UserDataSite>();

	private UserData() {}

	public UserDataSite addSite(String[] siteArray) {
		int id 			= Integer.parseInt(siteArray[0]);
		String site 	= siteArray[1];
		String password = siteArray[2];

		UserDataSite siteObj = new UserDataSite(id, site, password);

		sites.add(siteObj);

		return siteObj;
	}
}
