package main.java.app.HTTPInterfaces;

import main.java.app.models.Tweet;
import main.java.app.models.TweetCreator;
import retrofit.http.*;

/**
 * Created by pecheriere on 15/12/14.
 */
public interface AlgoliaAPI {

    public String APIKey = "APIKEY";
    public String AppId = "APPID";

    @Headers({
            "X-Algolia-API-Key:" + APIKey,
            "X-Algolia-Application-Id:" + AppId
    })
    @PUT("/indexes/wordCount/{objectId}")
    Object putWordCount(@Path("objectId") String objectId, @Body WordCountIndex wordCountIndex);

    public class WordCountIndex {
        public String word;
        public Long count;
    }

    @Headers({
            "X-Algolia-API-Key:" + APIKey,
            "X-Algolia-Application-Id:" + AppId
    })
    @PUT("/indexes/associatedHash/{objectId}")
    Object putAssociatedHash(@Path("objectId") String objectId, @Body AssociatedHashIndex associatedHashIndex);

    public class AssociatedHashIndex {
        public String hash;
        public String associated;
        public Long count;
    }

    @Headers({
            "X-Algolia-API-Key:" + APIKey,
            "X-Algolia-Application-Id:" + AppId
    })
    @PUT("/indexes/popularHash/{objectId}")
    Object putPopularHash(@Path("objectId") String objectId, @Body PopularHashIndex popularHashIndex);

    public class PopularHashIndex {
        public String hash;
        public String username;
        public Long count;
    }

    @Headers({
            "X-Algolia-API-Key:" + APIKey,
            "X-Algolia-Application-Id:" + AppId
    })
    @PUT("/indexes/tweets/{objectId}")
    Object putTweet(@Path("objectId") String objectId, @Body Tweet tweet);


}
