package main.java.app.reducer;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.mapreduce.Reducer;
import com.google.appengine.tools.mapreduce.ReducerInput;
import main.java.app.HTTPInterfaces.AlgoliaAPI;
import main.java.app.Tools.HttpClient;


public class TweetWordCountReducer extends Reducer<String, Long, Entity> {

    private static final long serialVersionUID = 1L;

    @Override
    public void reduce(String key, ReducerInput<Long> values) {
        Long sum = new Long(0);

        while (values.hasNext()) {
            sum += values.next();
        }

        Key keyDb = KeyFactory.createKey("WordCount", key);
        Entity insert = new Entity(keyDb);
        insert.setProperty("value", sum);

        emit(insert);

        AlgoliaAPI.WordCountIndex body = new AlgoliaAPI.WordCountIndex();
        body.count = sum;
        body.word = key;
        HttpClient.getAlgoliaApiClient().putWordCount(key, body);

    }
}