package main.java.app.mapper;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.Mapper;
import com.google.common.base.Pair;
import main.java.app.HTTPInterfaces.AlgoliaAPI;
import main.java.app.Tools.HttpClient;

public class AssociatedHashtagsMapper extends Mapper<Entity, Pair<String, String>, Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4836639602710328810L;

	public void map(Entity value) {
		StringTokenizer wordList = null;
		ArrayList<String> hashtagList = new ArrayList<String>();
		
		wordList = new StringTokenizer((String)value.getProperty("text"));
		if (wordList != null) {
			// Searches all hashtag in a String
			while (wordList.hasMoreTokens()) {
				String word = wordList.nextToken();
				
				if (word.charAt(0) == '#') {
					hashtagList.add(word);
				}
			}
			
			// Searches all possible hashtag pairs without duplicate
			for (int left = 0; left < hashtagList.size() - 1; ++left) {
				for (int right = left + 1; right < hashtagList.size(); ++right) {
					Pair<String, String> resultKey = null;
					
					// Alphabetic order (or not)
					if (hashtagList.get(left).compareTo(hashtagList.get(right)) > 0) {
						resultKey = new Pair<String, String>(hashtagList.get(left), hashtagList.get(right));
					} else {
						resultKey = new Pair<String, String>(hashtagList.get(right), hashtagList.get(left));
					}
					emit(resultKey, (long) 1);
				}
			}
		}
	}
}
