package scampi.me.Sites.Get;

import java.util.HashMap;

import scampi.me.Main;
import scampi.me.Sites.Base;
import spark.Request;
import spark.Response;
import spark.Route;

public class SimpleTemplate implements Route{
	
	private String template;
	private String title;
	
	private HashMap<String, Object> webMap = new HashMap<String, Object>();
	
	public SimpleTemplate(String template, String title) {
		this.template = template;
		this.title = title;
	}

	public Object handle(Request request, Response response) throws Exception {
		Base base = new Base(webMap, request, response);
		base.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1));
		webMap.put("domain", Main.cfg.getString("domain"));
		return base.renderTemplate(template);
	}

}
