package main.java.app.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by pecheriere on 09/12/14.
 */

@Entity
public class Tweet {

    public Tweet() {}

    @Id public String id_str;
    public String created_at;
    public String favorited;
    public String text;
    public Long retweet_count;
    public Long favourites_count;
    public TweetCreator user;

}
