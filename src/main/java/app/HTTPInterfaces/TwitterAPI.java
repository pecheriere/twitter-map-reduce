package main.java.app.HTTPInterfaces;

import main.java.app.models.Tweet;
import main.java.app.models.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by pecheriere on 02/12/14.
 */
public interface TwitterAPI {

    @GET("/statuses/home_timeline.json")
    List<Tweet> getHomeTimeLine(@Header("Authorization") String authorizationHeader, @Query("count") String count, @Query("since_id") String sinceId);

    @GET("/users/show.json")
    User getUser(@Header("Authorization") String authorizationHeader, @Query("user_id") String userId);

}
