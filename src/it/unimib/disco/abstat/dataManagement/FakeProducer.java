package it.unimib.disco.abstat.dataManagement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FakeProducer extends Thread {
	private SharedFileReader sfr;
	private  InputStream inputstream;
	private 			BufferedReader in;
	   private MongoDbConnector mongo;
	   private String host="localhost";
	   private int port=27017;
	   private String Db="abstat";
	   private String coll;
       private boolean error=false;
	public FakeProducer(String filename,SharedFileReader s, String collection){
		try {
			inputstream = new FileInputStream(filename);
			in = new BufferedReader(new InputStreamReader(inputstream));
			coll=collection;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
     
		sfr=s;
}
public void run(){
	 
	  mongo=new MongoDbConnector(host,port,Db,coll);
	System.out.println("inizio caricamento dati");
	long startTimeTotal = System.currentTimeMillis();
	   int i=0;	
		String line = null;
		long startTime = System.currentTimeMillis();
		String singleBuf=null;
	   try {
			while((line = in.readLine()) != null) {
				singleBuf=line;
				i++;
			if (i%1000000==0)
				{
					long endTime   = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println("caricato "+i+ " righe in "+ totalTime/1000+"  secondi");
				}
			
			
				sfr.putSingle(singleBuf);
			
          }
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	if (coll.equals("akp")){ 
		  mongo.setOccurrency();
		  mongo.close();
	}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTimeTotal;
			System.out.println("Caricamento terminato in "+ totalTime/1000+" secondi");
	
	   System.out.println("finito di caricare i dati");
	   System.exit(0);  


		}

public void close(){
	 try {
		in.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
