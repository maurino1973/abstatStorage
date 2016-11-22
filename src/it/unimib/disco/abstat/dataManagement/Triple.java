package it.unimib.disco.abstat.dataManagement;

import java.util.StringTokenizer;

import org.bson.Document;

public class Triple {

	private String tripleId;
	private String subjectType;
	private String objectType;
	private String propertyType;
	
	public Triple(String inputLine){
		tripleId=inputLine.replaceAll("<", "").replaceAll(">", "").replaceAll(" ", "");
		StringTokenizer st = new StringTokenizer(tripleId, "##");
		if (st.countTokens()>=3){
		subjectType=st.nextElement().toString().replace("dbpedia.org/ontology", "dbo").replace("dbpedia.org/property","dbp").replace("http://","").replace("dbpedia.org/resource","dbr");
		propertyType=st.nextElement().toString().replace("dbpedia.org/ontology", "dbo").replace("dbpedia.org/property","dbp").replace("http://","").replace("dbpedia.org/resource","dbr");
		objectType=st.nextElement().toString().replace("dbpedia.org/ontology", "dbo").replace("dbpedia.org/property","dbp").replace("http://","").replace("dbpedia.org/resource","dbr");
		}
	}
	public Document getDoc(){
		Document doc=new Document("tripleId", tripleId)
		.append("subjetType", subjectType)
	    .append("propertyType", propertyType)
	    .append("objectType", objectType);
		

		return doc;
	}
	
	public Document getTripleDoc(){
		Document doc=new Document("tripleId", tripleId)
		.append("subjetTriple", subjectType)
	    .append("propertyTriple", propertyType)
	    .append("objectTriple", objectType);
		

		return doc;
	}
	
	public String toString() {
		String Result="{tripleId:\""+tripleId+"\" \n"+"subjecType:\""+subjectType+"\" \n"+"objectType:\""+objectType+"\" \n}";
		
		return Result;
	}
	public String getTripleId() {
		return tripleId;
	}
	public void setTripleId(String tripleId) {
		this.tripleId = tripleId;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
}
