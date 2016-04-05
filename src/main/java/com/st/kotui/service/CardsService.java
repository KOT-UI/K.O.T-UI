package com.st.kotui.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.st.kotui.db.Persistence;

public class CardsService {
	public JSONArray getCards(int count) {
		JSONArray jo = Persistence.get().getCards(count);
		return jo;
	}
}
