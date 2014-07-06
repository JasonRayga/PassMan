package com.example.david.passman.data;

public class UserDataSite {

	private String _site;
	private String _password;
	private String _username;
	private int _id;
	private String icon;

	public String get_site() {
		return _site;
	}

	public String get_password() {
		return _password;
	}

	public String get_username() {
		return _username;
	}

	public int get_id() {
		return _id;
	}

	public String getIcon() {
		return icon;
	}

	public void set_site(String _site) {
		this._site = _site;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public UserDataSite(int id) {
		_id = id;
	}

	public UserDataSite(int id, String site, String password, String username) {
		_id = id;
		_site = site;
		_password = password;
		_username = username;
	}
}
