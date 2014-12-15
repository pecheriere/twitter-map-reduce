package main.java.app.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class AssociatedHashtagsResult {
	
private AssociatedHashtagsResult(String id, String assoc, Long value) {this.id_hashtag = id; this.associated = assoc; this.count = value;}
	
	@Id public String id_hashtag;
	public String associated;
	public Long count;
}
