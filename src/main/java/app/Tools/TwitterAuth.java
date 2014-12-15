package main.java.app.Tools;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * Created by pecheriere on 09/12/14.
 */
public class TwitterAuth {

    static public OAuthService oAuthService = new ServiceBuilder()
            .provider(TwitterApi.class)
            .apiKey("API_KEY_TWITTER")
            .apiSecret("API_SECRET_TWITTER")
            .callback("https://steam-mantis-782.appspot.com/auth/callback")
            .build();

        static public Token requestToken;

}
