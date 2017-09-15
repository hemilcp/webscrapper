package DAO;

import java.net.UnknownHostException;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * DBUtil class serves as the DAO for the project.
 * @author hemil
 *
 */
public class DBUtil {

	MongoClient mongoClient;
	MongoDatabase db;
	MongoCollection<Document> coll;

	/**
	 * Constructor creates a connection to the local host mongoDB. 
	 * Create a database if not present.
	 * @throws UnknownHostException
	 */
	public DBUtil() throws UnknownHostException{
		mongoClient = new MongoClient("localhost",27017);
		db = mongoClient.getDatabase("WebScrapperDB");
	}

	/**
	 * insert the result of the path taken for the particular endpoint.
	 * @param destination - the wiki page from which the ultimate path to philosophy page is found.
	 * @param path - the string list of path taken.
	 */
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
