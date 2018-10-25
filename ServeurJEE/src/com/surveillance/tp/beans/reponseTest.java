package com.surveillance.tp.beans;

public class reponseTest 
{
	private String JSON;
	private static reponseTest INSTANCE = null;
	
	private reponseTest()
	{}

	public static synchronized reponseTest getInstance()
	   {
	       if (INSTANCE == null)
	       {   
	    	   INSTANCE = new reponseTest();
	       }
	       return INSTANCE;
	   }
	
	public String getJSON() {
		return JSON;
	}

	public void setJSON(String jSON) {
		JSON = jSON;
	}
	
	
}