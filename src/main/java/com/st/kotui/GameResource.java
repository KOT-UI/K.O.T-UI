package com.st.kotui;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.st.kotui.service.GameService;

/**
 * Root resource (exposed at "gameresource" path)
 */

@Path("gameresource")
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
		JSONObject respJo = resource.createGame(User1, User2);
		return respJo.toString();
	//test drive
	}
}
