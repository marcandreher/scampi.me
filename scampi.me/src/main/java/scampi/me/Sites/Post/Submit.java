package scampi.me.Sites.Post;

import java.net.URL;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.Base64;

import scampi.me.Utils.MySQL;
import spark.Request;
import spark.Response;
import spark.Route;

public class Submit implements Route {

	public Object handle(Request request, Response response) throws Exception {
		String url = request.queryParams("url");

		try {
			new URL(url);
		} catch (Exception e) {
			return null;
		}

		Boolean freeUid = false;
		while (freeUid == false) {
			String uid = generateRandomBase64Token(15);
			ResultSet r = MySQL.Query("SELECT * FROM `links` WHERE `uid` = ?", uid);
			while (!r.next()) {
				if (request.queryParams("sec") == null) {
					MySQL.Exec("INSERT INTO `links`(`url`, `uid`) VALUES (?,?)", url, uid);
				} else {
					MySQL.Exec("INSERT INTO `links`(`url`, `uid`, `waiting`) VALUES (?,?,?)", url, uid,
							request.queryParams("sec"));
				}

				return uid + "\"" + url;
			}
		}

		return null;
	}

	public static String generateRandomBase64Token(int byteLength) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] token = new byte[byteLength];
		secureRandom.nextBytes(token);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(token); // base64 encoding
	}

}
