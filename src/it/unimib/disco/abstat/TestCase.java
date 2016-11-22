package it.unimib.disco.abstat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import it.unimib.disco.abstat.dataManagement.Akp;
import it.unimib.disco.abstat.dataManagement.FakeProducer;
import it.unimib.disco.abstat.dataManagement.MongoDbConnector;
import it.unimib.disco.abstat.dataManagement.MongoWriter;
import it.unimib.disco.abstat.dataManagement.SharedFileReader;
import it.unimib.disco.abstat.dataManagement.Triple;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TestCase {
	
	public static void main(String[] args) {
    SharedFileReader sfr=new SharedFileReader();	
    boolean fillAkp=true;
    MongoDbConnector mongo;
	String host="localhost";
	int port=27017;
	String Db="abstat";
	String coll="akp";

    long startTimeTotal = System.currentTimeMillis();
	
//      FakeProducer f=new FakeProducer("data/allData",sfr); 
    if (fillAkp){
    	   FakeProducer f=new FakeProducer("data/allData",sfr, coll); 
    	    f.start();
    	 
    	MongoWriter m=new MongoWriter(sfr,coll,"");
      MongoWriter m1=new MongoWriter(sfr,coll,"");
      MongoWriter m2=new MongoWriter(sfr,coll,"");
      MongoWriter m3=new MongoWriter(sfr,coll,"");
      MongoWriter m4=new MongoWriter(sfr,coll,"");
      MongoWriter m5=new MongoWriter(sfr,coll,"");
      MongoWriter m6=new MongoWriter(sfr,coll,"");
      MongoWriter m7=new MongoWriter(sfr,coll,"");
      MongoWriter m8=new MongoWriter(sfr,coll,"");
      MongoWriter m9=new MongoWriter(sfr,coll,"");
    
      m.start();
      m1.start();
      m2.start();
      m3.start();
      m4.start();
      m5.start();
      m6.start();
      m7.start();
      m8.start();
      m9.start();
    }
    else {    
    mongo=new MongoDbConnector(host,port,Db,coll);
	  mongo.setOccurrency();
	  mongo.close();
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTimeTotal;
		System.out.println("Caricamento terminat in "+ totalTime/1000+" secondi");
    }

    	
    

	}

}
