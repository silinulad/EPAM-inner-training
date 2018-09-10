package model;
/**
 * Created by Silin on 08.2018.
 */

/**
 * Post Bean with Builder pattern
 */
import java.sql.Timestamp;

public class Post {

	private long		postId;
	private User		author;
	private String		body;
	private Timestamp	time;
	
	public long getPostId() {
		return postId;
	}
	
	public void setPostId(long postId) {
		this.postId = postId;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + (int) (postId ^ (postId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (postId != other.postId)
			return false;
		return true;
	}
	private Post(Builder builder) {
		postId = builder.postId;
		author = builder.author;
		body = builder.body;
		time = builder.time;
	}
	public static class Builder {

		private long		postId;
		private User		author;
		private String		body;
		private Timestamp	time;

		public Builder setPostId(long postId) {
			this.postId = postId;
			return this;
		}

		public Builder setAuthor(User author) {
			this.author = author;
			return this;
		}

		public Builder setBody(String body) {
			this.body = body;
			return this;
		}

		public Builder setTime(Timestamp time) {
			this.time = time;
			return this;
		}

		public Post build() {
			if (body == null) {
				throw new IllegalArgumentException("Post required parameters are empty");
			}
			return new Post(this);
		}
	}

}
