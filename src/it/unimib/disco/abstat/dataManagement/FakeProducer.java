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
	private 			BufferedReader in ;



	public FakeProducer(String filename,SharedFileReader s){
		try {
			inputstream = new FileInputStream(filename);
			in = new BufferedReader(new InputStreamReader(inputstream));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sfr=s;
}
public void run(){
	   int i=0;	
	   int count=0;
		String line = null;
		long startTime = System.currentTimeMillis();
		String singleBuf=null;
	   try {
			while((line = in.readLine()) != null) {
				singleBuf=line;
				i++;
			if (i%10000==0)
				{
					long endTime   = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println("caricato riga "+i+ "in "+ totalTime/1000+" secondi");
				}
				sfr.putSingle(singleBuf);
			
          }
			System.out.println("finito!!!");
				System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
