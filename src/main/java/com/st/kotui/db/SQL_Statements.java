package com.st.kotui.db;

public class SQL_Statements {

	public static final String getAllImages = "SELECT * FROM kotui.cards";
	public static final String getAllRndImages = "SELECT * FROM kotui.cards order by rand();";
	public static final String addUsers = "INSERT INTO `kotui`.`players` (`name`,`status`) VALUES(?,?);";
	public static final String getUser = "SELECT id,name,status FROM kotui.players Where id = ?";
	public static final String addResource = "INSERT INTO `kotui`.`testTable` (`message`) VALUES(?)";
	public static final String getResource = "SELECT * FROM `kotui`.`testTable` WHERE idtestTable=?";

	public static String prepareResource (String message){
		String query = addResource.replaceFirst("\\?",String.valueOf(message));
				return query;
	}
	
	public static String preparePlayerSelectById (int id){
		String query = getUser.replaceFirst("\\?",String.valueOf(id));
				return query;
	}
}
