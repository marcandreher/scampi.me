package scampi.me.Sites.Get;

import java.util.HashMap;

import scampi.me.Sites.Base;
import spark.Request;
import spark.Response;
import spark.Route;

public class Home implements Route {
	
	public HashMap<String, Object> webMap = new HashMap<String, Object>();

	public Object handle(Request request, Response response) {
		Base base = new Base(webMap, request, response);
		base.setTitle("Page Not Found");
		return base.renderTemplate("home.html");

		
	}
}
