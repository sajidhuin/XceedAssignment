package com.xceed.tweetanalysis;

import com.mongodb.BasicDBObject;

import twitter4j.Status;

public class Tweet {
	
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Tweet(Status status) {
		super();
		this.status = status;
	}
	
	public BasicDBObject getBasicDBObject() {
		return new BasicDBObject(MyDBObject.DB_NAME, status.getUser().getName()).
                append(MyDBObject.DB_TEXT, status.getText()).
                append(MyDBObject.DB_CREATED_AT, status.getCreatedAt().getTime()).
                append(MyDBObject.DB_RETWEET_COUNT, status.getRetweetCount()).
                append(MyDBObject.DB_USER_MENTIONS, (long) status.getUserMentionEntities().length).
                append(MyDBObject.DB_HASH_TAGS, (long) status.getHashtagEntities().length);
	}
	
}
