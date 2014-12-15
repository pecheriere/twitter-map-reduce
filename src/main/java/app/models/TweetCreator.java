package main.java.app.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

/**
 * Created by pecheriere on 09/12/14.
 */

@Entity
public class TweetCreator {

    public TweetCreator() {};

    @Id public String id_str;
    @Parent Key<Tweet> parent;
    public String time_zone;
    public String name;
    public String profile_image_url;

}
