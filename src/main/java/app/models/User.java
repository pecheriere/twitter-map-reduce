package main.java.app.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Mapify;
import com.googlecode.objectify.annotation.Serialize;

import java.util.Set;

/**
 * Created by pecheriere on 10/12/14.
 */
@Entity
public class User {

    private User() {};

    @Id
    public String id_str;
    public String name;
    public String profile_image_url;
    public String location;
    public String created_at;
    public int followers_count;
    public String description;
    public String time_zone;
    public String screen_name;
    public String lastTweetFetched;
    public Set<String> timeLine;
    public String accessToken;
    public String secret;
    public String rawResponse;

}
