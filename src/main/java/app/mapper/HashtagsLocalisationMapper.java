package main.java.app.mapper;

import java.util.StringTokenizer;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.Mapper;
import com.google.common.base.Pair;

public class HashtagsLocalisationMapper extends Mapper<Entity, Pair<String, String>, Pair<String, Long>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -811819075977295791L;

	@Override
	public void map(Entity value) {
		String localisation = ((String)value.getProperty("created_at"));
		StringTokenizer wordList = new StringTokenizer((String)value.getProperty("text"));
		
		if (wordList != null) {
			// Searches all hashtags in a String
			while (wordList.hasMoreTokens()) {
				String hashtag = new String(wordList.nextToken());
				
				if (hashtag.charAt(0) == '#') {
					emit(new Pair<String, String>(hashtag, localisation), new Pair<String, Long>(localisation, new Long(1)));
				}
			}
		}
	}
}
