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

import com.st.kotui.service.GameService;

/**
 * Root resource (exposed at "gameresource" path)
 */

@Path("game")
public class GameResource {
	private GameService resource = new GameService();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveIt(String request) {
		JSONObject jo = new JSONObject(request);
		int User1 = jo.getInt("IdUser1");
		int User2 = jo.getInt("IdUser2");
		JSONObject game = resource.createGame(User1, User2);
		JSONObject respJo = new JSONObject();
		respJo.put("id", game.getInt("id"));
		return respJo.toString();
	//test drive
	}
	
	@POST
	@Path("cards")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String chooseCards(String request) {
		JSONObject respJo = new JSONObject();
		try {
			JSONObject jo = new JSONObject(request);
			int gameId = jo.getInt("gameId");
			int userId = jo.getInt("userId");
			JSONArray JSONCardIds = jo.getJSONArray("cardIds");
			resource.chooseCards(gameId, userId, JSONCardIds);
		} catch (Exception e) {
			e.printStackTrace();
			respJo.put("error", "Failed to update cards!");
		}
		return respJo.toString();
	}
	
	@GET
	@Path("cards")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getCards(
			@QueryParam("gameId") int gameId,
			@QueryParam("userId") int userId) {
		JSONObject respJo = new JSONObject();
		try {
			JSONArray carr = resource.getCards(gameId, userId);
			respJo.put("cards", carr);
			if (carr.length() == 0) {
				respJo.put("status", "waiting");
			} else {
				respJo.put("status", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			respJo.put("error", "Failed to get cards!");
		}
		return respJo.toString();
	}
}
