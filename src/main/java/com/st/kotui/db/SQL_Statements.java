package com.st.kotui.db;
import java.util.*;

public class SQL_Statements {

	public static final String getAllImages = "SELECT * FROM kotui.cards";
	public static final String getAllRndImages = "SELECT * FROM kotui.cards order by rand();";
	public static final String addUser = "INSERT INTO kotui.user (`username`,`lastActive`) VALUES(?,?);";
	public static final String getUser = "SELECT id,username,status FROM kotui.user Where id = ?";
	public static final String updateUser = "UPDATE kotui.user SET status='?', lastActive='?' WHERE id = ?";
	public static final String addResource = "INSERT INTO `kotui`.`testTable` (`message`) VALUES(?)";
	public static final String getResource = "SELECT * FROM `kotui`.`testTable` WHERE idtestTable=?";


	public static String prepareResource (String message){
		String query = addResource.replaceFirst("\\?",String.valueOf(message));
		return query;
	}
	
	public static String prepareUserSelectById (long id){
		String query = getUser.replaceFirst("\\?",String.valueOf(id));
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
}
