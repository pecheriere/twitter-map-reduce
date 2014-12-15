package main.java.app.reducer;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.Reducer;
import com.google.appengine.tools.mapreduce.ReducerInput;
import com.google.common.base.Pair;


public class HashtagsLocalisationReducer extends Reducer<Pair<String, String>, Pair<String, Long>, Entity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6132312417521337205L;

	@Override
	public void reduce(Pair<String, String> key, ReducerInput<Pair<String, Long>> values) {
		Entity insert = new Entity("HashtagLocalisationResult");
		long result = 0;
		
		while (values.hasNext()) {
			result += values.next().second.longValue();
		}
		insert.setProperty("id_hashtag", key.first);
		insert.setProperty("localisation", key.second);
		insert.setProperty("number", new Long(result));
		emit(insert);
	}

}