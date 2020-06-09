package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import com.mongodb.MongoClient;


public class ConnectToDB {
	private static ConnectToDB obj;
	static MongoDatabase database;

	public ConnectToDB() {
	}

	public static ConnectToDB getInstance() {
		if (obj == null)
			obj = new ConnectToDB();
		return obj;
	}

	public static void connexion() {
		MongoClient client = new MongoClient("localhost", 27017);
		database = client.getDatabase("mydb");
	}

	public static MongoCollection<Document> getEtapCollection() {
		return database.getCollection("Etape");
	}

	public static MongoCollection<Document> getprocedureCollection() {
		return database.getCollection("Procedure");
	}

}