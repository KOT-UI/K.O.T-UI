package com.st.kotui.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.st.kotui.db.Persistence;
import com.st.kotui.db.SQL_Statements;

public class GameService {
	
	public JSONObject createGame(int userID1, int userID2)
	{

				JSONObject created = Persistence.get().addGame(userID1, userID2);
				return created;

	}
	
	public JSONObject getGame(int userId)
	{
		JSONObject created = Persistence.get().getGameByUserId(userId);
		return created;
	}
	
	public void chooseCards(int gameId, int userId, JSONArray JSONCardIds)
	{
		JSONObject game = getGame(userId);
		boolean isUser1 = (game.getInt("user1ID") == userId);
		Persistence.get().chooseCards(game.getInt("id"), isUser1, JSONCardIds);
	}
	
	public JSONArray getCards(int gameId, int userId)
	{
		JSONObject game = getGame(userId);
		boolean isUser1 = (game.getInt("user1ID") == userId);
		String whichCards;
		if (isUser1) {
			whichCards = "user1Cards";
		} else {
			whichCards = "user2Cards";
		}
		JSONArray ja = new JSONArray(game.getString(whichCards));
		return ja;
	}

}