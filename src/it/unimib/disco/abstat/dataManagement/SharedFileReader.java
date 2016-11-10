package it.unimib.disco.abstat.dataManagement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SharedFileReader {
private boolean empty;
private List<String> buf;
private String singleBuf;

public SharedFileReader(){
	empty=true;
}

public synchronized  List<String>get(){
	while (empty==true){
	try {
		wait();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	empty=true;

	notify();
	List<String> returnBuf=buf;

	return returnBuf;
	
}


public synchronized void put( List<String> g){

	   try {
					  while (empty==false){
					
						  wait();
					}
					  empty=false;
					  buf=g;
					  notify();
					  
					 
					
			} catch ( InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
				
public synchronized String getSingle(){
	while (empty==true){
	try {
		wait();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	empty=true;
	notifyAll();

	return singleBuf;
	
}
public synchronized void putSingle(String g){

	   try {
					  while (empty==false){
					
						  wait();
					}
					  empty=false;
					  singleBuf=g;
					  notifyAll();
					
			} catch ( InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
				



}
