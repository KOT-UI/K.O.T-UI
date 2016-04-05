package com.st.kotui.service;

import org.json.JSONObject;

import com.st.kotui.db.Persistence;

public class UserService {

	public boolean isValidUsername(String username) {
		if (username.length() > 3 && username.matches("[a-zA-z]+")) {
			return true;
		} else {
			return false;
		}
	}
	
	public JSONObject getUser(long id) {
		/*JSONObject jo = Persistence.get().getUser(id);
		return jo;*/
		return null;
	}
	
	public JSONObject addUser(String username) {
		/*if (!isValidUsername(username)) {
			JSONObject error = new JSONObject();
			error.put("error", "Username is not valid.");
			return error;
		}
		JSONObject existing = Persistence.get().getUser(username);
		if (existing.has("status")) {
			Integer status = (Integer)existing.get("status");
			if (status != 0) {
				JSONObject error = new JSONObject();
				error.put("error", "User is already logged in.");
				return error;
			}
		} else {
			JSONObject created = Persistence.get().addUser(username);
			return created;
		}*/
		return null;
	}
	
	public JSONObject updateUser(long id) {
		/*JSONObject jo = Persistence.get().updateUser(id);
		return jo;*/
		return null;
	}

	public JSONObject getResources() {
		JSONObject jo = Persistence.get().getResource();
		return jo;
	}
	
}
