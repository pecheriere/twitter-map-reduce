package main.java.app.mapper;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.Mapper;
import main.java.app.Tools.HttpClient;
import main.java.app.models.Tweet;
import main.java.app.models.TweetCreator;

import java.util.StringTokenizer;

public class TweetWordCountMapper extends Mapper<Entity, String, Long> {

    private static final long serialVersionUID = 1L;

    @Override
    public void map(Entity value) {
        StringTokenizer tokenizer = new StringTokenizer((String) value.getProperty("text"));

        EmbeddedEntity embeddedEntity = (EmbeddedEntity) value.getProperty("user");
        Key infoKey = embeddedEntity.getKey();
        Entity user = new Entity(infoKey);

        user.setPropertiesFrom(embeddedEntity);


//        Uncomment to index all tweets
//        Tweet tweet = new Tweet();
//        tweet.created_at = (String) value.getProperty("created_at");
//        tweet.favourites_count = (Long) value.getProperty("favourites_count");
//        tweet.retweet_count = (Long) value.getProperty("retweet_count");
//        tweet.favorited = (String) value.getProperty("favorited");
//        tweet.id_str = value.getKey().getName();
//        tweet.text = (String) value.getProperty("text");
//        TweetCreator tweetCreator = new TweetCreator();
//        tweetCreator.id_str = user.getKey().getName();
//        tweetCreator.name = (String) user.getProperty("name");
//        tweetCreator.profile_image_url = (String) user.getProperty("profile_image_url");
//        tweet.user = tweetCreator;
//
//        HttpClient.getAlgoliaApiClient().putTweet(tweet.id_str, tweet);

        while (tokenizer.hasMoreTokens()) {
            emit(tokenizer.nextToken(), new Long(1));
        }
    }

}