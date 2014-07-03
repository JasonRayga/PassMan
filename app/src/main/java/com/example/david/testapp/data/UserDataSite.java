package com.example.david.testapp.data;

public class UserDataSite {

	private String _site;
	private String _password;
	private int _id;

	public String get_site() {
		return _site;
	}

	public String get_password() {
		return _password;
	}

	public int get_id() {
		return _id;
	}

	public UserDataSite(int id, String site, String password) {
		_id = id;
		_site = site;
		_password = password;
	}
}
