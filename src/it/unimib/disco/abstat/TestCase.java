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
		long startTime = System.currentTimeMillis();
      SharedFileReader sfr=new SharedFileReader();		
      FakeProducer f=new FakeProducer("data/parte_aa_aa",sfr); 
      MongoWriter m=new MongoWriter(sfr);
      MongoWriter m1=new MongoWriter(sfr);
      MongoWriter m2=new MongoWriter(sfr);
      MongoWriter m3=new MongoWriter(sfr);
      MongoWriter m4=new MongoWriter(sfr);
      MongoWriter m5=new MongoWriter(sfr);
      MongoWriter m6=new MongoWriter(sfr);
      MongoWriter m7=new MongoWriter(sfr);
      MongoWriter m8=new MongoWriter(sfr);
      MongoWriter m9=new MongoWriter(sfr);
      f.start();
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

      /*
     MongoWriter m10=new MongoWriter(sfr);
     m10.CreateFrequency();          
        */

		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime/1000);

	}

}
