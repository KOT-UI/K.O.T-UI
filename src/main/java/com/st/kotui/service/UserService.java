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
	
	public JSONObject getUser(int id) {
		JSONObject jo = Persistence.get().getUser(id);
		return jo;
	}
	
	public int getActiveUsers() {
		int count = Persistence.get().getActiveUsersCount();
		return count;
	}
	
	public JSONObject addUser(String username) {
		if (!isValidUsername(username)) {
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
			} else {
				Persistence.get().updateUser(existing.getString("username"), 1);
				existing.put("status", 1);
				return existing;
			}
		} else {
			JSONObject created = Persistence.get().addUser(username);
			return created;
		}
	}
	
	public void updateUser(String username, int state) {
		Persistence.get().updateUser(username, state);
	}

	public JSONObject getResources() {
		JSONObject jo = Persistence.get().getResource();
		return jo;
	}
	
	public JSONObject getOpponent(String username){
		JSONObject jo= Persistence.get().getOpponent(username);
		return jo;
		
	}
	
	public JSONObject tryMakeGame(int id) {
		JSONObject thisPlayer = getUser(id);
		JSONObject game;
		if (thisPlayer.getInt("status") == 2) {
			game = new JSONObject();
			game = Persistence.get().getGameByUserId(id);
		} else {
			Persistence p = Persistence.get();
			JSONObject opp = Persistence.get().getOpponent(id);
			if (!opp.has("id")) {
				JSONObject error = new JSONObject();
				error.put("error", "No active users found!");
				return error;
			}
			p.updateUser(thisPlayer.getString("username"), 2);
			p.updateUser(opp.getString("username"), 2);
			game = Persistence.get().addGame(id, opp.getInt("id"));
		}
		return game;
	}


	public JSONObject getActiveUsersCount() {
		JSONObject jo = new JSONObject();
		try {
			int count = Persistence.get().getActiveUsersCount();
			jo.put("value", count);
		} catch (Exception e) {
			jo.put("error", "Couldn't get users count!");
		}
		return jo;
	}
	
}
//test drive
