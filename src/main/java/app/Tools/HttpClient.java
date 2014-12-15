package main.java.app.Tools;

import main.java.app.HTTPInterfaces.AlgoliaAPI;
import main.java.app.HTTPInterfaces.TwitterAPI;
import retrofit.RestAdapter;

/**
 * Created by pecheriere on 10/12/14.
 */
public class HttpClient {

    static TwitterAPI twitterAPI;
    static AlgoliaAPI algoliaAPI;

    public static TwitterAPI getTwitterApiClient() {
        if (twitterAPI == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.twitter.com/1.1")
                    .build();
            twitterAPI = restAdapter.create(TwitterAPI.class);
        }
        return  twitterAPI;
    }

    public static AlgoliaAPI getAlgoliaApiClient() {
        if (twitterAPI == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://YN2MPPL3E2.algolia.io/1")
                    .build();
            algoliaAPI = restAdapter.create(AlgoliaAPI.class);
        }
        return  algoliaAPI;
    }
}
