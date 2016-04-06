package com.st.kotui.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class Persistence {

	private static Persistence perInstance;
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://10.11.11.35:3306/";
	private static final String DB_SCHEMA = "kotui";
	private static final String DB_URL_SCHEMA = DB_URL + DB_SCHEMA;

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "Ecp123";

	private Connection connection;

	public static Persistence get() {
		if (perInstance == null || perInstance.connectionCheck()) {
			try {
				perInstance = new Persistence();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return perInstance;
	}

	private Persistence() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(DB_URL_SCHEMA, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private boolean connectionCheck() {
		try {
			return this.connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return true;
	}

	@SuppressWarnings("finally")
	public JSONObject addResource(String message) {
		String query = SQL_Statements.addResource;
		JSONObject jo = new JSONObject();
		try {
			int key = -1;
			PreparedStatement prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prep.toString();
			prep.setString(1, message);

			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs != null && rs.next()) {
				key = rs.getInt(1);
			}
			System.out.println(key);
			int id = key;
			String messge = message;
			jo.put("id", id);
			jo.put("message", messge);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}

	@SuppressWarnings("finally")
	public JSONObject getResource() {
		String query = SQL_Statements.getResource;
		JSONObject jo = new JSONObject();
		try {

			PreparedStatement prep = connection.prepareStatement(query);

			prep.setInt(1, 2);

			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next()) {

				int id = rs.getInt("idtestTable");
				;
				String messge = rs.getString("message");
				;
				jo.put("id", id);
				jo.put("message", messge);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	@SuppressWarnings("finally")
	public JSONObject addUser(String username) {
		String query = SQL_Statements.addUser;
		JSONObject jo = new JSONObject();
		try {
			int key = -1;
			Date now = new Date();
			PreparedStatement prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			prep.setString(1, username);
			prep.setString(2, now.toString());
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs != null && rs.next()) {
				key = rs.getInt(1);
			}
			int id = key;
			String user = username;
			String userdate = now.toString();
			jo.put("id", id);
			jo.put("username", user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	@SuppressWarnings("finally")
	public JSONObject getUser(int id) {
		String query = SQL_Statements.getUserById;
		JSONObject jo = new JSONObject();
		try {

			PreparedStatement prep = connection.prepareStatement(query);

			prep.setInt(1, id);

			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next()) {

				int idUser = rs.getInt("id");
				;
				String user = rs.getString("username");
				;
				int status = rs.getInt("status");
				;
				jo.put("id", idUser);
				jo.put("username", user);
				jo.put("status", status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	@SuppressWarnings("finally")
	public JSONObject getUser(String username) {
		String query = SQL_Statements.getUserByUsername;
		JSONObject jo = new JSONObject();
		try {

			PreparedStatement prep = connection.prepareStatement(query);

			prep.setString(1, username);

			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next()) {

				int idUser = rs.getInt("id");
				;
				String user = rs.getString("username");
				;
				int status = rs.getInt("status");
				;
				jo.put("id", idUser);
				jo.put("username", user);
				jo.put("status", status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	@SuppressWarnings("finally")
	public void updateUser(String username, int status) {
		String query = SQL_Statements.updateUser;
		try {
			Date now = new Date();
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, status);
			prep.setString(2, now.toString());
			prep.setString(3, username);
			System.out.println("");
			int rowCount = prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public int getActiveUsersCount() {
		String query = SQL_Statements.getActiveUsersCount;
		int count = 0;
		try {
			PreparedStatement prep = connection.prepareStatement(query);

			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return count;
		}
	}
	
	@SuppressWarnings("finally")
	public JSONObject getGameByUserId(int userId) {
		String query = SQL_Statements.getGameByUserId;
		JSONObject game = new JSONObject();
		try {
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, userId);
			prep.setInt(2, userId);

			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next()) {
				game.put("id", rs.getInt("id"));
				game.put("user1ID", rs.getInt("user1ID"));
				game.put("user2ID", rs.getInt("user2ID"));
				game.put("user1Cards", rs.getString("user1Cards"));
				game.put("user2Cards", rs.getString("user2Cards"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return game;
		}
	}
	
	//test drive
	
	@SuppressWarnings("finally")
	public JSONObject getOpponent (String username)
	{
		String query = SQL_Statements.getRndOp;
		JSONObject jo = new JSONObject();
		try
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setString(1, username);
			System.out.println("BLAR");
			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next())
			{
				String user = rs.getString("username");
				
				jo.put("username", user);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	
	@SuppressWarnings("finally")
	public JSONObject getOpponent (int id)
	{
		String query = SQL_Statements.getRndOpById;
		JSONObject jo = new JSONObject();
		try
		{
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next())
			{
				String user = rs.getString("username");
				
				jo.put("username", user);
				jo.put("id", rs.getInt("id"));
				jo.put("status", rs.getInt("status"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	
	@SuppressWarnings("finally")
	public JSONArray getCards(int count) {
		String query = SQL_Statements.getCards;
		JSONArray jo = new JSONArray();
		try {

			PreparedStatement prep = connection.prepareStatement(query);

			prep.setInt(1, count);

			ResultSet rs = prep.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					JSONObject row = new JSONObject();
					row.put("id", rs.getString("id"));
					//row.put("name", rs.getString("name"));
					row.put("image", rs.getString("path"));
					jo.put(row);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	
	public void chooseCards(int gameId, boolean isUser1, JSONArray JSONCardIds) {
		String query = SQL_Statements.setGameCardsUser;
		try {

			if (isUser1) {
				query = query.replaceFirst("\\?", "user2Cards");
			} else {
				query = query.replaceFirst("\\?", "user1Cards");
			}

			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(2, gameId);
			prep.setString(1, JSONCardIds.toString());
			prep.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("finally")
	public JSONObject addGame(int userID1, int userID2) {
		String query = SQL_Statements.addGame;
		JSONObject jo = new JSONObject();
		try {
			int key = -1;
			Date now = new Date();
			PreparedStatement prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prep.toString();
			prep.setInt(1,userID1);
			prep.setInt(2,userID2);
			prep.setString(3, now.toString());

			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs != null && rs.next()) {
				key = rs.getInt(1);
			}
			int id = key;
			int user1 = userID1;
			int user2 = userID2;
			jo.put("id", id);
			jo.put("IdUser1", user1);
			jo.put("IdUser2", user2);
			jo.put("TimeCreated", now.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}
	
	@SuppressWarnings("finally")
	public JSONObject getCardById(int id) {
		String query = SQL_Statements.getCardById;
		JSONObject card = new JSONObject();
		try {
			PreparedStatement prep = connection.prepareStatement(query);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				card.put("id", rs.getInt("id"));
				card.put("name", rs.getString("name"));
				card.put("image", rs.getString("image"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return card;
		}
	}

}
