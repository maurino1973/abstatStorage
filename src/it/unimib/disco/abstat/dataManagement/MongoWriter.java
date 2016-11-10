package it.unimib.disco.abstat.dataManagement;

import java.util.List;
import java.util.StringTokenizer;

public class MongoWriter extends Thread{
private	String host="localhost";
private int port=27017;
private String Db="abstat";
private String coll="akp";
private SharedFileReader sfr;

private MongoDbConnector mongo;
	public MongoWriter(SharedFileReader i){
	      mongo=new MongoDbConnector(host,port,Db,coll);
	      sfr=i;
	}
	public void run(){
	 for (;;){
	/*	List<String> buf=sfr.get();
		for (String t : buf) {
	*/
	 String t=sfr.getSingle();

			//per ogni riga crei tanti akp quanti sono nella riga i esima e scriviSeNonEsiste l'apk su mongodb
			StringTokenizer st = new StringTokenizer(t, "[");
			String triple=st.nextElement().toString()+"[";
			
			StringTokenizer st2 = new StringTokenizer(st.nextElement().toString(), ",");	
		while (st2.hasMoreElements())
		{
			String inputline=triple+st2.nextElement().toString();
			Akp akp=new Akp(inputline);
			mongo.insertAkp(akp);
		}
		}
	// }
}
		
	public void CreateFrequency(){
		mongo.setFrequency();
	}
	
    public void close(){
    	mongo.close();
    }
}
