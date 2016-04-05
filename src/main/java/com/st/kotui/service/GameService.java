package com.st.kotui.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONObject;

import com.st.kotui.db.Persistence;
import com.st.kotui.db.SQL_Statements;

public class GameService {
	
	public JSONObject createGame(int userID1, int userID2)
	{

				JSONObject created = Persistence.get().addGame(userID1, userID2);
				return created;

	}

}