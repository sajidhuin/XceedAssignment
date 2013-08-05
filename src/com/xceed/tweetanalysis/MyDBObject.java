package com.xceed.tweetanalysis;

public class MyDBObject {
	
	public static final String DB_NAME = "name";
	public static final String DB_TEXT = "text";
	public static final String DB_CREATED_AT = "created_at";
	public static final String DB_RETWEET_COUNT = "retweet_count";
	public static final String DB_USER_MENTIONS = "user_mentions";
	public static final String DB_HASH_TAGS = "hashtags";
	
	private String name;
    
	private String text;
    
	private long createdAt;
    
	private long retweetCount;
    
	private long userMentionsCount;
    
	private long hashtagsCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(long retweetCount) {
		this.retweetCount = retweetCount;
	}

	public long getUserMentionsCount() {
		return userMentionsCount;
	}

	public void setUserMentionsCount(long userMentionsCount) {
		this.userMentionsCount = userMentionsCount;
	}

	public long getHashtagsCount() {
		return hashtagsCount;
	}

	public void setHashtagsCount(long hashtagsCount) {
		this.hashtagsCount = hashtagsCount;
	}

	public MyDBObject() {
		super();
	}
	
	public MyDBObject(String name, String text, long createdAt,
			long retweetCount, long userMentionsCount, long hashtagsCount) {
		super();
		this.name = name;
		this.text = text;
		this.createdAt = createdAt;
		this.retweetCount = retweetCount;
		this.userMentionsCount = userMentionsCount;
		this.hashtagsCount = hashtagsCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (createdAt ^ (createdAt >>> 32));
		result = prime * result
				+ (int) (hashtagsCount ^ (hashtagsCount >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (retweetCount ^ (retweetCount >>> 32));
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result
				+ (int) (userMentionsCount ^ (userMentionsCount >>> 32));
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
		MyDBObject other = (MyDBObject) obj;
		if (createdAt != other.createdAt)
			return false;
		if (hashtagsCount != other.hashtagsCount)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (retweetCount != other.retweetCount)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (userMentionsCount != other.userMentionsCount)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MyDBObject [name=" + name + ", text=" + text + ", createdAt="
				+ createdAt + ", retweetCount=" + retweetCount
				+ ", userMentionsCount=" + userMentionsCount
				+ ", hashtagsCount=" + hashtagsCount + "]";
	}
	
}
