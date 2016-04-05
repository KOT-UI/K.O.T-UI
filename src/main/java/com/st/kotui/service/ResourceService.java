package com.st.kotui.service;

import org.json.JSONObject;

import com.st.kotui.db.Persistence;

public class ResourceService {

	public JSONObject postResources(String message) {
		JSONObject jo = Persistence.get().addResource(message);
		return jo;
	}

	public JSONObject getResources() {
		JSONObject jo = Persistence.get().getResource();
		return jo;
	}
	public JSONObject addUserResources(String username) {
		JSONObject jo = Persistence.get().addUser(username);
		return jo;
	}

}
