package main.java.app.reducer;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.mapreduce.Reducer;
import com.google.appengine.tools.mapreduce.ReducerInput;
import com.google.common.base.Pair;
import main.java.app.HTTPInterfaces.AlgoliaAPI;
import main.java.app.Tools.HttpClient;


public class PopularHashtagsReducer extends Reducer<Pair<Entity, String>, Pair<String, Long>, Entity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2701576239269824881L;

	@Override
	public void reduce(Pair<Entity, String> key, ReducerInput<Pair<String, Long>> values) {
		Long result = new Long(0);

		String dbId = key.first.getKey().getName()+":"+ key.second;
		Key keyDb = KeyFactory.createKey("PopularHashtagResult", dbId);
        Entity insert = new Entity(keyDb);
		
		while (values.hasNext()) {
			result += values.next().second;
		}
		insert.setProperty("id_user", (String)key.first.getProperty("id_str"));
		insert.setProperty("username", (String)key.first.getProperty("name"));
		insert.setProperty("hashtag", key.second);
		insert.setProperty("count", result);

		AlgoliaAPI.PopularHashIndex popularHashIndex = new AlgoliaAPI.PopularHashIndex();
		popularHashIndex.count = result;
		popularHashIndex.hash = key.second;
		popularHashIndex.username = (String)key.first.getProperty("name");

		HttpClient.getAlgoliaApiClient().putPopularHash(dbId, popularHashIndex);

		emit(insert);
	}
}