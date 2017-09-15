package DAO;

import java.net.UnknownHostException;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class DBUtil {

	MongoClient mongoClient;
	MongoDatabase db;
	MongoCollection<Document> coll;

	public DBUtil() throws UnknownHostException{
		mongoClient = new MongoClient("localhost",27017);
		db = mongoClient.getDatabase("WebScrapperDB");
	}

	public void insertData(String destination, String path){
		//		if(!collectionExists("WebScrapperInfo")){
		//			db.createCollection("WebScrapperInfo");
		//		}

		coll = db.getCollection("WebScrapperInfo");

		Document doc = new Document();

		doc.append("destination", destination);
		doc.append("path", path);

		coll.insertOne(doc);
	}

	public boolean collectionExists(final String collectionName) {
		MongoIterable<String> collectionNames = db.listCollectionNames();
		for (final String name : collectionNames) {
			if (name.equalsIgnoreCase(collectionName)) {
				return true;
			}
		}
		return false;
	}

}
