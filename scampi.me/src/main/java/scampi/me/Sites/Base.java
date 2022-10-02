package scampi.me.Sites;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Template;
import scampi.me.Main;
import scampi.me.Utils.Prefix;
import spark.Request;
import spark.Response;

public class Base {
	private Map<String,Object> freeMap = null;
	private Request req = null;
	
	public Base(Map<String,Object> freeMap, Request req, Response res) {
		this.freeMap = freeMap;
		this.req = req;
	}
	
	public void setTitle(String title) {
		freeMap.put("titlebar", title);
	}
	
	public String renderTemplate(String template) {
		System.out.println(Prefix.INFO +  req.requestMethod() +": \"" +req.pathInfo() + "\"");
		
		try {
			Template templateFree = Main.freemarkerCfg.getTemplate(template);
			Writer out = new StringWriter();
			templateFree.process(freeMap, out);;
			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}