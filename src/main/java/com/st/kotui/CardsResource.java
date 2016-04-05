package com.st.kotui;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.st.kotui.service.CardsService;
import com.st.kotui.service.ResourceService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("cards")
public class CardsResource {

	private CardsService service = new CardsService();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIt(@DefaultValue("30")
						@QueryParam("count")
						final String count) {
		int c = 30;
		try {
			c = Integer.parseInt(count);
		} catch (Exception e) {
			JSONObject error = new JSONObject();
			error.put("error", "Invalid ?count value.");
		}
		JSONArray respJo = service.getCards(c);
		return respJo.toString();
	}

}
