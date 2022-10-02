package scampi.me.Sites.Post;

import java.security.SecureRandom;
import java.util.Base64;

import scampi.me.Utils.MySQL;
import spark.Request;
import spark.Response;
import spark.Route;

public class SubmitAction implements Route {

	public Object handle(Request request, Response response) throws Exception {
		String uid = request.queryParams("uid");
		String ac = request.queryParams("id");
		String text = request.queryParams("text");
		String link = request.queryParams("link");
		
		MySQL.Exec("INSERT INTO `actions`(`uid`, `iconid`, `text`, `link`) VALUES (?,?,?,?)", uid,ac,text,link);
		
		return "Action created";
	}

	public static String generateRandomBase64Token(int byteLength) {
	    SecureRandom secureRandom = new SecureRandom();
	    byte[] token = new byte[byteLength];
	    secureRandom.nextBytes(token);
	    return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
	}

}
