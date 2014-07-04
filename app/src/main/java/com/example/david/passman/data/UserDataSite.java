package com.example.david.passman.data;

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

	public void set_site(String _site) {
		this._site = _site;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public UserDataSite(int id, String site, String password) {
		_id = id;
		_site = site;
		_password = password;
	}
}
