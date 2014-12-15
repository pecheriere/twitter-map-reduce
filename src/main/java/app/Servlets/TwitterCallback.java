package main.java.app.Servlets;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.ObjectifyService;
import main.java.app.HTTPInterfaces.TwitterAPI;
import main.java.app.Tools.HeaderBuilder;
import main.java.app.Tools.HttpClient;
import main.java.app.Tools.TwitterAuth;
import main.java.app.models.User;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pecheriere on 09/12/14.
 */
public class TwitterCallback extends HttpServlet {

    static {
        ObjectifyService.register(User.class);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OAuthService service = TwitterAuth.oAuthService;
        Verifier v = new Verifier(req.getParameter("oauth_verifier"));
        Token token = service.getAccessToken(TwitterAuth.requestToken, v);
        resp.sendRedirect("https://steam-mantis-782.appspot.com");

        Map<String, String> query = getQueryMap(token.getRawResponse());

        String userId = query.get("user_id");

        log(userId);

        TwitterAPI twitterAPI = HttpClient.getTwitterApiClient();
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/users/show.json?user_id=" + userId);
        service.signRequest(token, request);

        User userDB = ObjectifyService.ofy().load().type(User.class).id(userId).now();

        User user = twitterAPI.getUser(HeaderBuilder.getOAuthHeader(request), userId);

        if (userDB != null) {
            user.lastTweetFetched = userDB.lastTweetFetched;
            user.timeLine = userDB.timeLine;
        }
        user.accessToken = token.getToken();
        user.secret = token.getSecret();
        user.rawResponse = token.getRawResponse();

        ObjectifyService.ofy().save().entities(user);

        TaskOptions taskOptions = TaskOptions.Builder
                .withUrl("/worker")
                .param("token", token.getToken())
                .param("secret", token.getSecret())
                .param("rawResponse", token.getRawResponse())
                .param("user_id", user.id_str);

        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(taskOptions);
    }

    public static Map<String, String> getQueryMap(String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

}
