package main.java.app.Servlets;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.QueryKeys;
import main.java.app.models.Tweet;
import main.java.app.models.TweetCreator;
import main.java.app.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by pecheriere on 09/12/14.
 */
public class CronJobsServlet extends HttpServlet {

    static {
        ObjectifyService.register(User.class);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Work in a testing env, may consume too much RAM in a production environment with many users.
        QueryKeys <User> usersKeys = ObjectifyService.ofy().load().type(User.class).keys();

        for (Key<User> key : usersKeys) {
            User user = ObjectifyService.ofy().load().type(User.class).id(key.getName()).now();
            TaskOptions taskOptions = TaskOptions.Builder
                    .withUrl("/worker")
                    .param("token", user.accessToken)
                    .param("secret", user.secret)
                    .param("rawResponse", user.rawResponse)
                    .param("user_id", user.id_str);

            Queue queue = QueueFactory.getDefaultQueue();
            queue.add(taskOptions);
        }
        
    }

}
