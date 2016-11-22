package it.unimib.disco.abstat.dataManagement;

import java.util.List;
import java.util.StringTokenizer;

public class MongoWriter extends Thread{
private	String host="localhost";
private int port=27017;
private String Db="abstat";
private String mode;
private SharedFileReader sfr;

private MongoDbConnector mongo;
	public MongoWriter(SharedFileReader i,String c, String m){
	      mongo=new MongoDbConnector(host,port,Db,c);
	      mode=m;
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
				if (mode.equals("etl")){
					mongo.insertAkpAndTriple(akp);
				}
				else if (mode.equals("dbo/Film"))
				{
				mongo.insertAkpbySubject(akp, mode);
				}
				else if (mode.equals("akp")) 
					mongo.insertAkp(akp);
			
		}
		}
	// }
}
		
	
    public void close(){
    	mongo.close();
    }
}
