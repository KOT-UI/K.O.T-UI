package com.st.kotui.service;

import org.json.JSONObject;

import com.st.kotui.db.Persistence;

public class UserService {

	public JSONObject getUser(long id) {
		JSONObject jo = Persistence.get().
		return jo;
	}
	
	public JSONObject addUser(String username) {
		JSONObject existing = Persistence.get().getUser(username);
		//if (existing == )
		return jo;
	}

	public JSONObject getResources() {
		JSONObject jo = Persistence.get().getResource();
		return jo;
	}
	
}
