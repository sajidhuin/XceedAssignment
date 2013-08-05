package com.xceed.tweetanalysis;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetReader {
	
	// For some reason the API was returning back 100 if we specify 114.
	private static final int TWEETS_PER_PAGE = 114;
	
	public List<Tweet> getTweets() throws TwitterException {
		Twitter twitter = new TwitterFactory().getInstance();
        List<Status> statuses = twitter.getHomeTimeline(new Paging(1, TWEETS_PER_PAGE));
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (Status status : statuses)
        	tweets.add(new Tweet(status));
        	
		return tweets;
	}
}
