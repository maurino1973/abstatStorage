package it.unimib.disco.abstat.dataManagement;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;


public class MongoDbConnector {

	private MongoClient mongoClient;
	private MongoCollection<Document> collection ;
	private MongoCollection<Document> collectionSub;
	private MongoCollection<Document> collectionObj;
	private MongoCollection<Document> collectionResult ;

	private MongoDatabase db; 
	
	public MongoDbConnector(String host, int port, String Db, String coll)
	{
		mongoClient = new MongoClient(host,port);
		db= mongoClient.getDatabase(Db);
		collection = db.getCollection(coll);
		collectionSub=db.getCollection("CardinalitySub");
		collectionObj=db.getCollection("CardinalityObj");

		collectionResult=db.getCollection("ResultCardinality");
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
	public void setOccurrency(){
		System.out.println("inizio calcolo cardinalita'");
		
//		db.createCollection("Cardinality");
//		collectionExtra=db.getCollection("Cardinality");
		long startTime = System.currentTimeMillis();
		long startTimeTotal = System.currentTimeMillis();
		Document query;
		List<Document> list;
		AggregateIterable<Document> output; 
		long endTime   ;
		long totalTime ;
		/*	
		db.akp.mapReduce(

				function() {emit(this.akpIdSub, this.value);},
				function(keys, values) {return Array.sum(values)},
				{
				    out:"Cardinality"
				}
				*/
/*		//map Red
		String map = "function() {emit(this.akpIdSub, this.value)}";

   String reduce = "function(keys, values) {return Array.sum(values)}";


		
		// caricamento in nuova collezione
		MapReduceIterable<Document> output=collection.mapReduce(map,reduce);
	*/
		//aggregate

		// inser sub occurency
		query=	new Document("$group", new Document("_id","$akpIdSub").
				append("SubOccurrency",new Document("$sum",1))
				.append("akpId", new Document("$last","$akpId"))

				);
       Document query2= new Document("$out", "CardinalitySub");
       list=new ArrayList<Document>();
       list.add(query);
      list.add(query2);
		output = collection.aggregate(list).allowDiskUse(true);
		output.first();			
    	System.out.println("effettuata group by su subject");
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("operazione svolta in "+totalTime/1000+" secondi");

		
		// inser object occurency
		query=	new Document("$group", new Document("_id","$akpIdObj").
				append("ObjOccurrency",new Document("$sum",1))
				.append("akpId", new Document("$last","$akpId")));
		  query2= new Document("$out", "CardinalityObj");
		list=new ArrayList<Document>();
	       list.add(query);
	       list.add(query2);
	    	startTime = System.currentTimeMillis();

		output = collection.aggregate(list).allowDiskUse(true);
		output.first();			
		System.out.println("effettuata group by su object");        
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("operazione svolta in "+totalTime/1000+" secondi");
		collectionResult.drop();
		query=	new Document("$group", new Document("_id","$akpId").
				append("OccurrencySub",new Document("$sum",1))
				.append("minSubject", new Document("$min","$SubOccurrency"))
				.append("maxSubject", new Document("$max","$SubOccurrency"))
				.append("avgSubject", new Document("$avg","$SubOccurrency"))
				
				);

//		.append("minObject", new Document("$min","$ObjOccurrency"))
//		.append("maxObject", new Document("$max","$ObjOccurrency"))
//		.append("avgObject", new Document("$avg","$ObjOccurrency"))

		query2= new Document("$out", "ResultCardinality");
		list=new ArrayList<Document>();
	       list.add(query);
	       list.add(query2);
	    	startTime = System.currentTimeMillis();

		output = collectionSub.aggregate(list).allowDiskUse(true);
		output.first();			

		query=	new Document("$group", new Document("_id","$akpId").
				append("OccurrencyObj",new Document("$sum",1))
				.append("minObject", new Document("$min","$ObjOccurrency"))
				.append("maxObject", new Document("$max","$ObjOccurrency"))
				.append("avgObject", new Document("$avg","$ObjOccurrency"))
				
				);
		list=new ArrayList<Document>();
	       list.add(query);
		output = collectionObj.aggregate(list).allowDiskUse(true);
		for (Document d:output){
			Document update=new Document("$set", new Document("minObject",d.getInteger("minObject")).append("maxObject", d.getInteger("maxObject"))
					.append("avgObject", d.getDouble("avgObject"))
					.append("OccurrencyObj", d.getInteger("OccurrencyObj"))
					);
			Document query3=new Document("_id",d.getString("_id"));
		//	System.out.println(query3.toJson());

			collectionResult.updateOne(query3,update);
		}
		
    	System.out.println("effettuata query finale");
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println("operazione svolta in "+totalTime/1000+" secondi");
		totalTime = endTime - startTimeTotal;
		System.out.println("operazione completain "+totalTime/1000+" secondi");
	
	} 

	public void close(){
//		collection.drop();
	 mongoClient.close();
	}
	
}
