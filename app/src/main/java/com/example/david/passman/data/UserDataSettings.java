package com.example.david.passman.data;

public class UserDataSettings {

	private String _displayName;
	private String _password;
	private String _enctyptionKey;

	public String get_displayName() {
		return _displayName;
	}

	public String get_password() {
		return _password;
	}

	public String get_enctyptionKey() {
		return _enctyptionKey;
	}

	public UserDataSettings(String displayName, String password, String encryptionKey) {
		_displayName    = displayName;
		_password       = password;
		_enctyptionKey  = encryptionKey;
	}
}
