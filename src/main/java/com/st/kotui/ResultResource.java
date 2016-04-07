package com.st.kotui;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.st.kotui.db.Persistence;
import com.st.kotui.service.GameService;
import com.st.kotui.service.ResourceService;
import com.st.kotui.service.ResultService;

/**
 * Root resource (exposed at "resultresource" path)
 */
@Path("results")

public class ResultResource {
	private ResultService resource = new ResultService();
	
	@POST
	@Path("compare")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String answersResults(String request) {
		try {
		JSONObject jo = new JSONObject(request);
		int gameID = jo.getInt("gameId");
		int userID = jo.getInt("userId");
		JSONArray cards = jo.getJSONArray("cards");
		String cardsname = new String();
		for(int i = 0;i < cards.length();i++) {
			if(i == (cards.length() - 1)) {
			cardsname = cardsname + cards.getJSONObject(i).getString("name");
			}else cardsname = cardsname + cards.getJSONObject(i).getString("name") + ",";
		}
		JSONObject bin = resource.tryCalcResult(gameID, userID, cardsname);
		return bin.toString();
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject error = new JSONObject();
			error.put("error", "Failed to calculate results.");
			return error.toString();
		}
	}
}
