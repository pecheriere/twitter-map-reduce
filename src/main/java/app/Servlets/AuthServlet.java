package main.java.app.Servlets;

import main.java.app.Tools.TwitterAuth;
import org.scribe.oauth.OAuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pecheriere on 09/12/14.
 */
public class AuthServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OAuthService service = TwitterAuth.oAuthService;
        TwitterAuth.requestToken = service.getRequestToken();
        resp.sendRedirect(service.getAuthorizationUrl(TwitterAuth.requestToken));
    }

}
