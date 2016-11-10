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
private int minCardSubject;
private int maxCardSubject;
private int minCardObject;
private int maxCardObject;
private int minCardSubProperties;
private int maxCardSubProperties;
private int frequency;
private Document triple;

public Akp(String inputLine){
	StringTokenizer st = new StringTokenizer(inputLine, "[");
	Triple t=new Triple(st.nextElement().toString());
		triple=t.getDoc(); 
	
	StringTokenizer st2 = new StringTokenizer(st.nextElement().toString(), "##");
	subjectType=st2.nextElement().toString();
	propertyType=st2.nextElement().toString();
	objectType=st2.nextElement().toString().replaceAll("]", "");
	akpId=subjectType+"##"+propertyType+"##"+objectType;
	}

public String toString() {
	String Result="{akpId:\""+akpId+"\" \n"+"subjecType:\""+subjectType+"\" \n"+"objectType:\""+objectType+"\" \n}";
	
	return Result;
	}
public Document getDoc(){ //solo dati akp no triples
	Document doc=new Document("akpId", akpId)
	.append("subjetType", subjectType)
    .append("propertyType", propertyType)
    .append("objectType", objectType)
    .append("triple", triple);
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

