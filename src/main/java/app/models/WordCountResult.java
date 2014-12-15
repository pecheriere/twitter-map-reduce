package main.java.app.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class WordCountResult {
	
	private WordCountResult(String id, Long value) {this.id_str = id; this.value = value;}
	
	@Id public String id_str;
	public Long value;
	
}
