package com.st.kotui.db;
import java.util.*;

public class SQL_Statements {

	public static final String getAllImages = "SELECT * FROM kotui.cards";
	public static final String getAllRndImages = "SELECT * FROM kotui.cards order by rand();";
	public static final String addUser = "INSERT INTO kotui.user (`username`,`lastActive`) VALUES(?,?);";
	public static final String getUserById = "SELECT id,username,status FROM kotui.user Where id = ?";
	public static final String getUserByUsername = "SELECT id,username,status FROM kotui.user Where username = ?";
	public static final String getGameByUserId = "SELECT id FROM kotui.game WHERE (ended = 0 && (user1ID = ? || user2ID = ?))";
	public static final String getActiveUsersCount = "SELECT COUNT(id) FROM kotui.user WHERE status <> 0";
	public static final String updateUser = "UPDATE kotui.user SET status=?, lastActive=? WHERE username = ?";
	public static final String addResource = "INSERT INTO `kotui`.`testTable` (`message`) VALUES(?)";
	public static final String getResource = "SELECT * FROM `kotui`.`testTable` WHERE idtestTable=?";
    public static final String getRndOp = "SELECT * FROM kotui.user WHERE (status = 1 AND username <> '?') ORDER BY rand() LIMIT 1";
    public static final String getRndOpById = "SELECT * FROM kotui.user WHERE (status = 1 AND id <> ?) ORDER BY rand() LIMIT 1";
	public static final String getCards = "SELECT * FROM `kotui`.`picture` LIMIT ?";
    public static final String addGame = "INSERT INTO kotui.game (user1ID,user2ID,started) VALUES(?,?,?)";
    
	public static String preparegetRndOp (String username){
		String query = getRndOp.replaceFirst("\\?",String.valueOf(username));
		return query;
	}
	
	public static String prepareResource (String message){
		String query = addResource.replaceFirst("\\?",String.valueOf(message));
		return query;
	}
	
	public static String prepareUserGet(long id){
		String query = getUserById.replaceFirst("\\?",String.valueOf(id));
		return query;
	}
	
	public static String prepareUserGet(String id){
		String query = getUserByUsername.replaceFirst("\\?",String.valueOf(id));
		return query;
	}
	
	public static String prepareUserAdd(String username) {
		Date now = new Date();
		String query = addUser.replaceFirst("\\?", username)
							  .replaceFirst("\\?", now.toString());
		return query;
	}
	
	public static String prepareUserUpdate(long id, int status) {
		Date now = new Date();
		String query = updateUser.replaceFirst("\\?", String.valueOf(status))
								 .replaceFirst("\\?", now.toString())
								 .replaceFirst("\\?", String.valueOf(id));
		return query;
	}
	public static String prepareGameAdd (int UserID1, int UserID2)
	{
		Date now = new Date();
		String query = addGame.replaceFirst("\\?", String.valueOf(UserID1))
				 .replaceFirst("\\?", String.valueOf(UserID2))
				 .replaceFirst("\\?", now.toString());
		
		return query;
		
	}
}
