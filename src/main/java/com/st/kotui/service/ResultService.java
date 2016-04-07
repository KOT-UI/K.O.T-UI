package com.st.kotui.service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.st.kotui.db.Persistence;

public class ResultService {
	
	private int otherPlayerId;
	public JSONObject addResult(int userId, int result, String wrong, Boolean isUser1) {
		
		JSONObject game =  Persistence.get().getGameByUserId(userId);
		int index;
		if(isUser1)index=1;
		else index=2;
		int gameID= game.getInt("id");
		
		JSONObject jo = Persistence.get().addResult(gameID, result, index, wrong);
		
		return jo;
	}
	/*
	public JSONArray getResult(int userId, Boolean isUser1)
	{
		JSONObject game = Persistence.get().getGameByUserId(userId);
		int index;
		if(isUser1)index=1;
		else index=2;
		int gameID= game.getInt("id");
		
		JSONArray jo = Persistence.get().getResult(userId, index);
		
		return jo;
	}*/
	//----------------------------------------------------//
	public JSONObject tryCalcResult(int userId, String answers)
	{
		JSONObject jo = new JSONObject();
		JSONObject jome = new JSONObject();
		JSONObject joyou = new JSONObject();
		JSONArray myWrongArray = new JSONArray();
		JSONArray youWrongArray = new JSONArray();
		JSONObject myWrongImg = new JSONObject();
		JSONObject youWrongImg = new JSONObject();
		String[] answer = answers.split(",");
	    boolean winner=false;
		
		String wrong = new String();
		String otherWrong = new String();
		
		JSONObject game =  Persistence.get().getGameByUserId(userId); //Gets Game
		boolean isUser1 = game.getInt("user1ID") == userId;
		Integer result;
		Integer otherResult;
		JSONArray cards;
		int otherPlayerIndex; //keeps the index of the other player
		int otherPlayerId;
		
		
		if(isUser1)
		{
		result = game.getInt("user1Result");
		otherResult = game.getInt("user2Result");
		wrong = game.getString("user1Wrong");
		otherWrong = game.getString("user2Wrong");
		cards = new JSONArray(game.getString("user1Cards")); //Gets chosen cards
		otherPlayerIndex = 2;
		otherPlayerId = game.getInt("user2ID");
		} else {
		result = game.getInt("user2Result");
		otherResult = game.getInt("user1Result");
		wrong = game.getString("user2Wrong");
		otherWrong = game.getString("user1Wrong");
		cards = new JSONArray(game.getString("user2Cards")); //Gets chosen cards' id#
		otherPlayerIndex = 1;
		otherPlayerId = game.getInt("user1ID");
		}
		
		
		if(result == -1)
		{
		
		int[] array = new int[cards.length()]; //keeps the id of cards
		String[] cardName = new String[cards.length()]; //keeps the name of cards
		String[] imagePath = new String[cards.length()]; //keeps the path of cards
		
		for (int i = 0; i < cards.length(); ++i) {
			array[i] = cards.getInt(i); //gets cards' id
		    JSONObject card = Persistence.get().getCardById(array[i]); //gets the card by its id
		    cardName[i] = card.getString("name");
			imagePath[i] = card.getString("path");
		    if(cardName[i]==answer[i])
		    {
		    	result++;
		    } else {
		    	myWrongImg.put("id", array[i]);
		    	myWrongImg.put("name", cardName[i]);
		    	myWrongImg.put("image", imagePath[i]);
		    	myWrongArray.put(myWrongImg);
		       if(wrong.isEmpty())
		       {
		       wrong+= array[i];
		       } else {
		    	   wrong+= "," + array[i];
		       }
		    }
		}
		addResult(userId, result, wrong, isUser1);
		} else {
			
		}
		
		if(otherResult == -1)
		{
		jo.put("error", "Other player has not finished");
		} else {
			if(result>otherResult) winner=true;
			else winner = false;
			jome.put("points", result);
			jome.put("winner",winner);
			jome.put("wrong",myWrongArray);
			joyou.put("points", otherResult);
			joyou.put("winner",!winner);
			youWrongArray = Persistence.get().getResult(otherPlayerId, otherPlayerIndex);
			joyou.put("wrong",youWrongArray);
			jo.put("jome",jome);
			jo.put("joyou", joyou);
		/*jo.put("result", result);
		jo.put("wrong", wrong);*/
		}
		
	
		
		return jo;
		
		// Test if correct
		
	}

}
