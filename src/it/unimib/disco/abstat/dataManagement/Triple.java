package it.unimib.disco.abstat.dataManagement;

import java.util.StringTokenizer;

import org.bson.Document;

public class Triple {

	private String tripleId;
	private String subjectType;
	private String objectType;
	private String propertyType;
	
	public Triple(String inputLine){
		tripleId=inputLine;
		StringTokenizer st = new StringTokenizer(inputLine, "##");
		subjectType=st.nextElement().toString().replaceAll("<", "");
		propertyType=st.nextElement().toString();
		objectType=st.nextElement().toString().replaceAll(">", "");
		
	}
	public Document getDoc(){
		Document doc=new Document("tripleId", tripleId)
		.append("subjetType", subjectType)
	    .append("propertyType", propertyType)
	    .append("objectType", objectType);
		

		return doc;
	}
	
	public String toString() {
		String Result="{tripleId:\""+tripleId+"\" \n"+"subjecType:\""+subjectType+"\" \n"+"objectType:\""+objectType+"\" \n}";
		
		return Result;
	}
	
}
