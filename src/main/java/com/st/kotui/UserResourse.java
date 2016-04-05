package com.st.kotui;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.st.kotui.service.UserService;

/**
 * Root resource (exposed at "userresource" path)
 */
@Path("userresource")

public class UserResourse {
	private UserService resource = new UserService();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		JSONObject respJo = resource.getResources();
		return respJo.toString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveIt(String request) {
		JSONObject jo = new JSONObject(request);
		String username = jo.getString("username");
		JSONObject respJo = resource.addUser(username);
		return respJo.toString();
	//test drive
	}
}
