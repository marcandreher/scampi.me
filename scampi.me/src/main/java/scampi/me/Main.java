package scampi.me;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import scampi.me.Sites.Get.Home;
import scampi.me.Sites.Get.Link;
import scampi.me.Sites.Get.SimpleTemplate;
import scampi.me.Sites.Post.Submit;
import scampi.me.Sites.Post.SubmitAction;
import scampi.me.Utils.Color;
import scampi.me.Utils.Config;
import scampi.me.Utils.MySQL;
import scampi.me.Utils.Prefix;
import spark.Route;
import spark.Spark;

public class Main {
	
	public static Config cfg = null;
	public static Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_31);
	public static MySQL mysql = null;
	
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
				+ "By Marc Andre Herpers - 1.1 - MIT LICENSE\n");
		System.out.println(Prefix.INFO + "Loading config.json");
		try {
			cfg = new Config();
		} catch (Exception e) {
			System.out.println(Prefix.ERROR + "Failed to load config.json");
			System.exit(2);
		}
		
		Spark.port(cfg.getInt("port"));
		Spark.ipAddress(cfg.getString("ip"));
		System.out.println(Prefix.INFO + "Scampi for Servers is running on " + cfg.getString("ip") + ":" + cfg.getInt("port"));
		
		mysql = new MySQL(cfg.getString("mysqlusername"), cfg.getString("mysqlpassword"),
				cfg.getString("mysqldatabase"), cfg.getString("mysqlip"),
				cfg.getInt("mysqlport"));
		
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
		
		File simpleTemplateFiles = new File("templates/simple");
		if(!simpleTemplateFiles.exists()) {
			simpleTemplateFiles.mkdir();
		}
		
		Spark.externalStaticFileLocation("static/");
		
		try {
			freemarkerCfg.setDirectoryForTemplateLoading(templateFiles);
		} catch (IOException e) {
			System.out.println(Prefix.ERROR + "Failed to load template folder");
		}
		
		
		// SIMPLE TEMPLATES
		for(File f : simpleTemplateFiles.listFiles()) {
				String title = f.getName().replace(".html", "");
				getRoutes.put("/"+title, new SimpleTemplate("simple/"+f.getName(), title));
				System.out.println(Prefix.INFO + "Loaded SimpleTemplate "+f.getName() +" on /" + title);
		}

		
		// INSERT ROUTES
		getRoutes.put("/", new Home());
		getRoutes.put("/l/:uid", new Link());
		postRoutes.put("/create", new Submit());
		postRoutes.put("/createac", new SubmitAction());
		
	
				
		for (Map.Entry<String, Route> entry : getRoutes.entrySet())
			Spark.get(entry.getKey(), entry.getValue());
		for (Map.Entry<String, Route> entry : postRoutes.entrySet())
			Spark.post(entry.getKey(), entry.getValue());
		
		
	}
	

}