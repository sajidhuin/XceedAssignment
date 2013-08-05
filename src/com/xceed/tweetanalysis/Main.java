package com.xceed.tweetanalysis;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.TwitterException;

import com.mongodb.DBObject;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<Tweet> tweets = new TweetReader().getTweets();
			List<DBObject> dBObjects = new ArrayList<DBObject>();
			for (Tweet tweet : tweets)
				dBObjects.add(tweet.getBasicDBObject());
			
			System.out.println(MongoDao.getInstance().insert(dBObjects));
			
			System.out.println(MongoDao.getInstance().getTopRetweets());
			System.out.println(MongoDao.getInstance().getTopMentioned());
			System.out.println(MongoDao.getInstance().getTopFollowed());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
