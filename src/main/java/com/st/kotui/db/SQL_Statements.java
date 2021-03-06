package com.st.kotui.db;
import java.util.*;

public class SQL_Statements {

	public static final String getAllImages = "SELECT * FROM kotui.cards";
	public static final String getAllRndImages = "SELECT * FROM kotui.cards order by rand();";
	public static final String addUser = "INSERT INTO kotui.user (`username`,`lastActive`) VALUES(?,?);";
	public static final String getUserById = "SELECT id,username,status FROM kotui.user Where id = ? ORDER BY id DESC LIMIT 1";
	public static final String getUserByUsername = "SELECT id,username,status FROM kotui.user Where username = ? ORDER BY id DESC LIMIT 1";
	public static final String getGameByUserId = "SELECT * FROM kotui.game WHERE (ended = 0 && (user1ID = ? || user2ID = ?))";
	public static final String getActiveUsersCount = "SELECT COUNT(id) FROM kotui.user WHERE status = 1";
	public static final String setGameCardsUser = "UPDATE kotui.game SET ? = ? WHERE id = ?";
	public static final String getGameCardsUser = "SELECT ? FROM kotui.game WHERE id = ?";
	public static final String updateUser = "UPDATE kotui.user SET status=?, lastActive=? WHERE username = ?";
	public static final String addResource = "INSERT INTO `kotui`.`testTable` (`message`) VALUES(?)";
	public static final String getResource = "SELECT * FROM `kotui`.`testTable` WHERE idtestTable=?";
    public static final String getRndOp = "SELECT * FROM kotui.user WHERE (status = 1 AND username <> '?') ORDER BY rand() LIMIT 1";
    public static final String getRndOpById = "SELECT * FROM kotui.user WHERE (status = 1 AND id <> ?) ORDER BY rand() LIMIT 1";
	public static final String getCards = "SELECT * FROM `kotui`.`picture` ORDER BY rand() LIMIT ?";

    public static final String addGame = "INSERT INTO kotui.game (user1ID,user2ID,started,user1Cards,user2Cards,user1Result,user2Result,user1Wrong,user2Wrong) VALUES(?,?,?,'','',-1,-1,'','')";

  //  public static final String addGame = "INSERT INTO kotui.game (user1ID,user2ID,started) VALUES(?,?,?)";


    public static final String getCardById = "SELECT * FROM `kotui`.`picture` WHERE id = ?";

   // public static final String addFirstAnswers = "UPDATE kotui.game SET firstAnswers=? WHERE id = ?";
   // public static final String addSecondAnswers = "UPDATE kotui.game SET secondAnswers=? WHERE id = ?";
    public static final String addResult = "UPDATE kotui.game SET user?Result=?, user?Wrong=? WHERE id=?";
    public static final String getResult = "SELECT user?Result, user?Wrong FROM kotui.game WHERE id=?";

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
	/*
	public static String prepareAnswersAdd (String userAnswers, int Gameid, int index)
	{
		if(Gameid==1)
		{
		String query = addFirstAnswers.replaceFirst("\\?", userAnswers)
				 .replaceFirst("\\?", String.valueOf(Gameid));		
		return query;
		}
		else
		{
			String query = addSecondAnswers.replaceFirst("\\?", userAnswers)
					 .replaceFirst("\\?", String.valueOf(Gameid));
			
			return query;
		}

	}*/
}
