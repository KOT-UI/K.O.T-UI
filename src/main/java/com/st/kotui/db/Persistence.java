package com.st.kotui.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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
			long key = -1L;
			PreparedStatement prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prep.toString();
			prep.setString(1, message);

			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs != null && rs.next()) {
				key = rs.getLong(1);
			}
			System.out.println(key);
			long id = key;
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

	public JSONObject getResource() {
		String query = SQL_Statements.getResource;
		JSONObject jo = new JSONObject();
		try {

			PreparedStatement prep = connection.prepareStatement(query);

			prep.setInt(1, 2);

			ResultSet rs = prep.executeQuery();
			if (rs != null && rs.next()) {

				long id = rs.getInt("idtestTable");
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
	public JSONObject addUser(String username) {
		String query = SQL_Statements.addUser;
		JSONObject jo = new JSONObject();
		try {
			long key = -1L;
			Date now = new Date();
			PreparedStatement prep = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prep.toString();
			prep.setString(1, username);
			prep.setString(2, now.toString());

			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			if (rs != null && rs.next()) {
				key = rs.getLong(1);
			}
			System.out.println(key);
			long id = key;
			String user = username;
			String userdate = now.toString();
			jo.put("id", id);
			jo.put("username", user);
			jo.put("date", userdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return jo;
		}
	}

}
