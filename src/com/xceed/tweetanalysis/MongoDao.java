package com.xceed.tweetanalysis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class MongoDao {

	private static final int MAX_TOP = 5;
	
	private static MongoDao instance = null;
	
	private DBCollection coll;
	
	private MongoDao() throws UnknownHostException {
		coll = new Mongo().getDB("xceed").getCollection("tweets");
	}
	
	public static MongoDao getInstance() throws UnknownHostException {
		if (instance == null)
			instance = new MongoDao();
		return instance;
	}
	
	public WriteResult insert(List<DBObject> dbObjects) {
		coll.drop();
		return coll.insert(dbObjects);
	}
	
	public List<MyDBObject> getTopRetweets() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return getTopObjects(getRankedMap(getAllMyObjects(), MyDBObject.class.getMethod("getRetweetCount")));
	}
	
	public List<MyDBObject> getTopMentioned() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return getTopObjects(getRankedMap(getAllMyObjects(), MyDBObject.class.getMethod("getUserMentionsCount")));
	}
	
	public List<MyDBObject> getTopFollowed() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return getTopObjects(getRankedMap(getAllMyObjects(), MyDBObject.class.getMethod("getHashtagsCount")));
	}
	
	private List<MyDBObject> getAllMyObjects() {
		List<MyDBObject> myObjectList = new ArrayList<MyDBObject>();
		
		DBCursor cursor = coll.find();
		try {
		   while(cursor.hasNext()) {
		       DBObject dbObject = cursor.next();
		       MyDBObject myObject = new MyDBObject();
		       myObject.setName((String) dbObject.get(MyDBObject.DB_NAME));
		       myObject.setText((String) dbObject.get(MyDBObject.DB_TEXT));
		       myObject.setCreatedAt((Long) dbObject.get(MyDBObject.DB_CREATED_AT));
		       myObject.setRetweetCount((Long) dbObject.get(MyDBObject.DB_RETWEET_COUNT));
		       myObject.setUserMentionsCount((Long) dbObject.get(MyDBObject.DB_USER_MENTIONS));
		       myObject.setHashtagsCount((Long) dbObject.get(MyDBObject.DB_HASH_TAGS));
		       myObjectList.add(myObject);
		   }
		} finally {
		   cursor.close();
		}
		
		return myObjectList;
	}
	
	private SortedMap<Long, SortedSet<MyDBObject>> getRankedMap (List<MyDBObject> myObjectList, Method method) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		SortedMap<Long, SortedSet<MyDBObject>> rankedMap = new ConcurrentSkipListMap<Long, SortedSet<MyDBObject>>(
				new Comparator<Long>() {
					@Override
					public int compare(Long o1, Long o2) {
						// TODO Auto-generated method stub
						if (o2 < o1) return -1;
						else if (o2 == o1) return 0;
						else return 1;
					}
				});
		
		for (MyDBObject myObject : myObjectList) {
			Long mapKey = (Long) method.invoke(myObject);
			SortedSet<MyDBObject> rankedSet = rankedMap.get(mapKey);
			if (rankedSet == null) {
				rankedSet = new ConcurrentSkipListSet<MyDBObject>(new Comparator<MyDBObject>() {
					@Override
					public int compare(MyDBObject o1, MyDBObject o2) {
						// TODO Auto-generated method stub
						if (o2.getCreatedAt() < o1.getCreatedAt()) return -1;
						else if (o2.getCreatedAt() == o1.getCreatedAt()) return 0;
						else return 1;
					}
				});
				rankedMap.put(mapKey, rankedSet);
			}
			rankedSet.add(myObject);
		}
		
		return rankedMap;
	}
	
	private List<MyDBObject> getTopObjects (SortedMap<Long, SortedSet<MyDBObject>> rankedMap) {
		List<MyDBObject> topObjectList = new ArrayList<MyDBObject>(MAX_TOP);
		
		Iterator<Long> keyIterator = rankedMap.keySet().iterator();
		while (keyIterator.hasNext()) {
			Iterator<MyDBObject> valIterator = rankedMap.get(keyIterator.next()).iterator();
			while (valIterator.hasNext())
				if (topObjectList.size() >= MAX_TOP)
					return topObjectList;
				else
					topObjectList.add(valIterator.next());
		}
		
		return topObjectList;
	}
	
}
