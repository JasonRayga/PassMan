package com.example.david.passman.data;

public class UserDataSettings {

	private String _displayName;
	private String _password;

	public String get_displayName() {
		return _displayName;
	}

	public String get_password() {
		return _password;
	}

	public UserDataSettings(String displayName, String password) {
		_displayName = displayName;
		_password = password;
	}
}
