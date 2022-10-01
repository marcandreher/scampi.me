package scampi.me.Sites.Get;

import java.sql.ResultSet;

import scampi.me.Utils.MySQL;
import spark.Request;
import spark.Response;
import spark.Route;

public class Link implements Route {

	public Object handle(Request request, Response response) throws Exception {
		// TODO Auto-generated method stub
		ResultSet r = MySQL.Query("SELECT * FROM `links` WHERE `uid` = ?", request.params(":uid"));
		while(r.next()) {
			MySQL.Exec("UPDATE `links` SET `clicks` = `clicks` + 1 WHERE `uid` = ?", request.params(":uid"));
			response.redirect(r.getString("url"));
		}
		response.redirect("/");
		return null;
	}

}
