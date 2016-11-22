package it.unimib.disco.abstat.dataManagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.bson.Document;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class Akp {
private String akpId;
private String subjectType;
private String objectType;
private String propertyType;
private Triple t;

public Akp(String inputLine){
	StringTokenizer st = new StringTokenizer(inputLine, "[");
	
	t=new Triple(st.nextElement().toString());
		 
	StringTokenizer st2 = new StringTokenizer(st.nextElement().toString(), "##");
	if (st2.countTokens()>=3){
	subjectType=st2.nextElement().toString().replaceAll(" ","").replaceAll("<","").replaceAll(">", "").replaceAll("]", "").replace("http://", "").replace("dbpedia.org/ontology", "dbo").replace("dbpedia.org/property","dbp").replace("dbpedia.org/resource","dbr");
	propertyType=st2.nextElement().toString().replaceAll(" ","").replaceAll("<","").replaceAll(">", "").replaceAll("]", "").replace("http://", "").replace("dbpedia.org/ontology", "dbo").replace("dbpedia.org/property","dbp").replace("dbpedia.org/resource","dbr");
	objectType=st2.nextElement().toString().replaceAll(" ","").replaceAll("<","").replaceAll(">", "").replaceAll("]", "").replace("http://", "").replace("dbpedia.org/ontology", "dbo").replace("dbpedia.org/property","dbp").replace("dbpedia.org/resource","dbr");
	akpId=subjectType+"##"+propertyType+"##"+objectType;
	}
}
public String toString() {
	String Result="{akpId:\""+akpId+"\" \n"+"subjecType:\""+subjectType+"\" \n"+"objectType:\""+objectType+"\" \n}";
	
	return Result;
	}

public Document getDoc(){ //solo dati akp no triples
	Document doc=new Document("akpIdSub", akpId+"["+t.getSubjectType())
			.append("akpIdObj",akpId+"["+t.getObjectType())
			.append("akpId", akpId)
			.append("value", 1);
	return doc;
}
public Triple getTriple(){return t;}
public Document getAkp(){
	Document doc=new Document("akpId",akpId)
	.append("subjectType",this.subjectType)
	.append("objectType", this.objectType)
	.append("propertyType",this.propertyType)
			;
	
	return doc;
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

public String getAkpId(){ 
    return akpId;
}

}


