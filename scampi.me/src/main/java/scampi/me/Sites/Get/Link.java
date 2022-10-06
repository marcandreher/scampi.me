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

public class Link implements Route {

	public HashMap<String, Object> webMap = new HashMap<String, Object>();

	public Object handle(Request request, Response response) throws Exception {
		ResultSet r = MySQL.Query("SELECT * FROM `links` WHERE `uid` = ?", request.params(":uid"));
		while (r.next()) {
			MySQL.Exec("UPDATE `links` SET `clicks` = `clicks` + 1 WHERE `uid` = ?", request.params(":uid"));
			ResultSet actionRS = MySQL.Query("SELECT * FROM `actions` WHERE `uid` = ?", request.params(":uid"));
			ArrayList<Icon> actionLi = new ArrayList<Icon>();
			while (actionRS.next()) {
				Icon i = new Icon();
				i.setLink(actionRS.getString("link"));
				i.setText(actionRS.getString("text"));
				ResultSet iconRS = MySQL.Query("SELECT * FROM `icons` WHERE `id` = ?", actionRS.getInt("iconid") + "");
				while (iconRS.next()) {
					i.setColor(iconRS.getString("btncolor"));
					i.setFontIcon(iconRS.getString("fontawesome"));
				}
				actionLi.add(i);
			}

			if (!actionLi.isEmpty()) {

				Base base = new Base(webMap, request, response);
				System.out.println("t");
				base.setTitle("Unlock your Link");
				webMap.put("domain", Main.cfg.getString("domain"));
				webMap.put("url", r.getString("url"));
				webMap.put("actionList", actionLi);
				return base.renderTemplate("unlock.html");
			}

			if (r.getInt("waiting") == 0) {
				response.redirect(r.getString("url"));
				return null;
			} else {
				Base base = new Base(webMap, request, response);
				base.setTitle("Your URL is ready soon");
				webMap.put("domain", Main.cfg.getString("domain"));
				webMap.put("url", r.getString("url"));
				webMap.put("waiting", r.getInt("waiting"));
				return base.renderTemplate("link.html");
			}
		}
		response.redirect(Main.cfg.getString("domain"));
		return null;
	}

}
