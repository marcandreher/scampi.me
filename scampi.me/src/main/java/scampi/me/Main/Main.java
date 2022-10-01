package scampi.me.Main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import scampi.me.Utils.Color;
import scampi.me.Utils.Config;
import scampi.me.Utils.Prefix;
import spark.Route;
import spark.Spark;

public class Main {
	
	public static Config cfg = null;
	public static Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_31);
	
	public static HashMap<String, Route> postRoutes = new HashMap<String, Route>();
	public static HashMap<String, Route> getRoutes = new HashMap<String, Route>();
	
	public static void main(String[] args) {
		System.out.println(Color.BLUE + "                                           _                        \r\n"
				+ "                                          (_)                       \r\n"
				+ "  ___    ___    __ _   _ __ ___    _ __    _       _ __ ___     ___ \r\n"
				+ " / __|  / __|  / _` | | '_ ` _ \\  | '_ \\  | |     | '_ ` _ \\   / _ \\\r\n"
				+ " \\__ \\ | (__  | (_| | | | | | | | | |_) | | |  _  | | | | | | |  __/\r\n"
				+ " |___/  \\___|  \\__,_| |_| |_| |_| | .__/  |_| (_) |_| |_| |_|  \\___|\r\n"
				+ "                                  | |                               \r\n"
				+ "                                  |_|                               \n"
				+ "By Marc Andre Herpers - 1.0 - MIT LICENSE\n");
		System.out.println(Prefix.INFO + "Loading config.json");
		try {
			cfg = new Config();
		} catch (Exception e) {
			System.out.println(Prefix.ERROR + "Failed to load config.json");
			System.exit(2);
		}
		
		Spark.port(cfg.getInt("port"));
		Spark.ipAddress(cfg.getString("ip"));
		System.out.println(Prefix.INFO + "Scampi is running on " + cfg.getString("ip") + ":" + cfg.getInt("port"));
		
		freemarkerCfg.setDefaultEncoding("UTF-8");
		freemarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		freemarkerCfg.setLogTemplateExceptions(false);
		freemarkerCfg.setWrapUncheckedExceptions(true);
		
		File staticFiles = new File("static/");
		if(!staticFiles.exists()) {
			staticFiles.mkdir();
		}
		
		File templateFiles = new File("templates/");
		if(!templateFiles.exists()) {
			templateFiles.mkdir();
		}
		
		Spark.externalStaticFileLocation("static/");
		
		try {
			freemarkerCfg.setDirectoryForTemplateLoading(templateFiles);
		} catch (IOException e) {
			System.out.println(Prefix.ERROR + "Failed to load template folder");
		}
		
		// INSERT ROUTES
		
				
		for (Map.Entry<String, Route> entry : getRoutes.entrySet())
			Spark.get(entry.getKey(), entry.getValue());
		for (Map.Entry<String, Route> entry : postRoutes.entrySet())
			Spark.post(entry.getKey(), entry.getValue());
		
		
	}
	

}