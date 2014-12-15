package main.java.app.reducer;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.mapreduce.Reducer;
import com.google.appengine.tools.mapreduce.ReducerInput;
import com.google.common.base.Pair;
import main.java.app.HTTPInterfaces.AlgoliaAPI;
import main.java.app.Tools.HttpClient;

public class AssociatedHashtagsReducer extends Reducer<Pair<String, String>, Long, Entity> {
    /**
     *
     */
    private static final long serialVersionUID = -4142767214316864812L;

    public void reduce(Pair<String, String> key, ReducerInput<Long> values) {
        Long wordCount = new Long(0);

        while (values.hasNext()) {
            wordCount += values.next();
        }

        Key keyDb = KeyFactory.createKey("AssociatedHashtags", key.first);
        Entity insert = new Entity(keyDb);
        insert.setProperty("hashtag", key.first);
        insert.setProperty("associated", key.second);
        insert.setProperty("count", wordCount);
        emit(insert);

        keyDb = KeyFactory.createKey("AssociatedHashtags", key.second);
        Entity insert_b = new Entity(keyDb);
        insert_b.setProperty("hashtag", key.second);
        insert_b.setProperty("associated", key.first);
        insert_b.setProperty("count", wordCount);
        emit(insert_b);

        AlgoliaAPI.AssociatedHashIndex body = new AlgoliaAPI.AssociatedHashIndex();
        body.hash = key.first;
        body.associated = key.second;
        body.count = wordCount;
        HttpClient.getAlgoliaApiClient().putAssociatedHash(key.first + ":" +key.second, body);

    }
}