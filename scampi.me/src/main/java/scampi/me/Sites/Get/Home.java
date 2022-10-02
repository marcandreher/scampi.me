package scampi.me.Sites.Get;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import scampi.me.Main;
import scampi.me.Classes.Icon;
import scampi.me.Sites.Base;
import scampi.me.Utils.MySQL;
import spark.Request;
import spark.Response;
import spark.Route;

public class Home implements Route {
	
	public HashMap<String, Object> webMap = new HashMap<String, Object>();

	public Object handle(Request request, Response response) {
		Base base = new Base(webMap, request, response);
		base.setTitle("Url Shortener");
		webMap.put("domain", Main.cfg.getString("domain"));
		ArrayList<Icon> fontAwesomeIcons = new ArrayList<Icon>();

		int totalClicks = 0;
		int urlsTotal = 0;
		try {
			ResultSet getRs = MySQL.Query("SELECT * FROM `links`");
			while(getRs.next()) {
				totalClicks = totalClicks + getRs.getInt("clicks");
				urlsTotal++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			ResultSet getIconRs = MySQL.Query("SELECT * FROM `icons`");
			while(getIconRs.next()) {
				Icon i = new Icon();
				i.setID(getIconRs.getInt("id"));
				i.setColor(getIconRs.getString("btncolor"));
				i.setFontIcon(getIconRs.getString("fontawesome"));
				i.setName(getIconRs.getString("name"));
				fontAwesomeIcons.add(i);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		webMap.put("icons", fontAwesomeIcons);
		webMap.put("totalClicks", totalClicks);
		webMap.put("urlsTotal", urlsTotal);
		
		
		return base.renderTemplate("home.html");

		
	}
}
