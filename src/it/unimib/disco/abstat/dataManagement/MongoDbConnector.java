package it.unimib.disco.abstat.dataManagement;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;


public class MongoDbConnector {

	private MongoClient mongoClient;
	private MongoCollection<Document> collection ;
	private MongoCollection<Document> collectionExtra ;
	private MongoDatabase db; 
	
	public MongoDbConnector(String host, int port, String Db, String coll)
	{
		mongoClient = new MongoClient(host,port);
		db= mongoClient.getDatabase(Db);
		collection = db.getCollection(coll);
		collectionExtra=db.getCollection("Cardinality");
	}
	
	public int insertAkp(Akp akp){
		//Document doc = new Document("$set",akp.getDoc());
//		Document query=new Document("akpId",akp.getAkpId()).append("subjectType", akp.getSubjectType())
//				.append("objectType", akp.getObjectType())
//				.append("propertyType", akp.getPropertyType()); 
		//UpdateOptions upsert=new UpdateOptions();
		//upsert.upsert(true);
		//collection.updateOne(query, doc, upsert);
		//Document triples= new Document("$addToSet",akp.getTriplesDoc());
			
		//collection.updateOne(query, triples, upsert);
		collection.insertOne(akp.getDoc());
		return 0;
	}
	public void setFrequency(){
	
		//db.akp.aggregate({$group:{ _id:{akpId:"$akpId"},"frequency":{$sum:1}}})
		Document query=	new Document("$group", new Document("_id", new Document("akpId", "$akpId"))
.append("frequency",new Document("$sum",1)));
		AggregateIterable<Document> output = collection.aggregate(Arrays.asList(query));
		// caricamento in nuova collezione
		List<Document>insert=new ArrayList<Document>();
		for (Document d:output)
		{
			insert.add(d);
		}
		
		collectionExtra.insertMany(insert);
	
		// update colleczione originale
	/*	for (Document d:output){
		
			collection.updateMany(
					new Document("akpId",new Document("$eq",d.getString("akpId")))
					, new Document("$set",new Document("frequency",d.getInteger("frequency"))));
		}
		*/
	} 

	public void close(){
	 mongoClient.close();
	}
	
}
