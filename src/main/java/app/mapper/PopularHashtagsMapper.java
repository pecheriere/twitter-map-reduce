package main.java.app.mapper;

import java.util.StringTokenizer;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.common.base.Pair;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.Mapper;


public class PopularHashtagsMapper extends Mapper<Entity, Pair<Entity, String>, Pair<String, Long> >{
	/**
	 * 
	 */
	private static final long serialVersionUID = 595552899921537222L;

	public void map(Entity value) {
		StringTokenizer wordList = null;

		wordList = new StringTokenizer((String)value.getProperty("text"));
		
		if (wordList != null) {
			while (wordList.hasMoreTokens()) {
				String hashtag = new String(wordList.nextToken());
				
				if (hashtag.charAt(0) == '#') {
					EmbeddedEntity embedded = (EmbeddedEntity) value.getProperty("user");
					Key infoKey = embedded.getKey();
					Entity creator = new Entity(infoKey);
					creator.setPropertiesFrom(embedded);
					emit(new Pair<Entity, String>(creator, hashtag), new Pair<String, Long>(hashtag, new Long(1)));
				}
			}
		}
	}
}
