package main.java.app.Servlets;

import com.googlecode.objectify.ObjectifyService;
import main.java.app.HTTPInterfaces.AlgoliaAPI;
import main.java.app.HTTPInterfaces.TwitterAPI;
import main.java.app.Tools.HeaderBuilder;
import main.java.app.Tools.HttpClient;
import main.java.app.Tools.TwitterAuth;
import main.java.app.models.Tweet;
import main.java.app.models.TweetCreator;
import main.java.app.models.User;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by pecheriere on 09/12/14.
 */
public class WorkerServlet extends HttpServlet {

    static {
        ObjectifyService.register(Tweet.class);
        ObjectifyService.register(TweetCreator.class);
        ObjectifyService.register(User.class);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String count = "800";

        User user = ObjectifyService.ofy().load().type(User.class).id(req.getParameter("user_id")).now();

        String reqToSign = "https://api.twitter.com/1.1/statuses/home_timeline.json?count=" + count;
        if (user.lastTweetFetched != null) {
            reqToSign += "&since_id=" + user.lastTweetFetched;
        }

        OAuthService service = TwitterAuth.oAuthService;
        OAuthRequest request = new OAuthRequest(Verb.GET, reqToSign);
        Token accessToken = new Token(req.getParameter("token"), req.getParameter("secret"), req.getParameter("rawResponse"));
        service.signRequest(accessToken, request);

        TwitterAPI twitterAPI = HttpClient.getTwitterApiClient();
        List<Tweet> tweets = twitterAPI.getHomeTimeLine(HeaderBuilder.getOAuthHeader(request), count, user.lastTweetFetched);


        if (tweets != null) {
            ObjectifyService.ofy().save().entities(tweets);
            user.lastTweetFetched = tweets.get(0).id_str;
            if (user.timeLine == null) {
                user.timeLine = new HashSet<String>();
            }
            for (Tweet tweet : tweets) {
                user.timeLine.add(tweet.id_str);
            }
            ObjectifyService.ofy().save().entities(user);
        }

        for (Tweet tweet : tweets) {
            HttpClient.getAlgoliaApiClient().putTweet(tweet.id_str, tweet);
        }

    }

}
