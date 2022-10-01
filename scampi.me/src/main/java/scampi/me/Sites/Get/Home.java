package scampi.me.Sites.Get;

import java.sql.ResultSet;
import java.util.HashMap;

import scampi.me.Sites.Base;
import scampi.me.Utils.MySQL;
import spark.Request;
import spark.Response;
import spark.Route;

public class Home implements Route {
	
	public HashMap<String, Object> webMap = new HashMap<String, Object>();

	public Object handle(Request request, Response response) {
		Base base = new Base(webMap, request, response);
		base.setTitle("Page Not Found");
		MySQL.Exec("UPDATE `links` SET `clicks` = `clicks` WHERE `uid` = ?", "ee");
		
		String uid = "";
		if(!(request.queryParams("uid") == null)) {
			try {
				uid = request.queryParams("uid");
				ResultSet getRs = MySQL.Query("SELECT * FROM `links` WHERE `uid` = ?", uid);
				while(getRs.next()) {
					webMap.put("uid", uid);
					webMap.put("url", getRs.getString("url"));
				}
			}catch(Exception e) {
				
			}
		}
		int totalClicks = 0;
		int siteClicks = 0;
		int urlsTotal = 0;
		try {
			ResultSet getRs = MySQL.Query("SELECT * FROM `links`");
			while(getRs.next()) {
				if(!(getRs.getInt("id") == 9)) {
					totalClicks = totalClicks + getRs.getInt("clicks");
					urlsTotal++;
				}else {
					siteClicks = getRs.getInt("clicks");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		webMap.put("totalClicks", totalClicks);
		webMap.put("siteClicks", siteClicks);
		webMap.put("urlsTotal", urlsTotal);
		
		
		return base.renderTemplate("home.html");

		
	}
}
