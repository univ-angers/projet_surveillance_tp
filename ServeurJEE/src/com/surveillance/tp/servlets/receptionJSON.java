package com.surveillance.tp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.surveillance.tp.beans.reponseTest;

public class receptionJSON extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
        reponseTest rep = reponseTest.getInstance();
        String res = rep.getJSON();
        
		System.out.println("DEBUG GET: " + res);
		
		/* Stockage du bean dans la request */
		request.setAttribute("ResultatJSON", res);
		
        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( "/WEB-INF/affichageJSON.jsp" ).forward( request, response );
        
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try{
	        StringBuilder sb = new StringBuilder();
	        BufferedReader br = request.getReader();
	        String str = null;
	        String resultat = "";
	        while ((str = br.readLine()) != null) {
	        	resultat = resultat + str;
	            sb.append(str);
	            System.out.println(str);
	        }
	        
	        System.out.println("DEBUG: " + resultat);
	        reponseTest rt = reponseTest.getInstance();
	        rt.setJSON(resultat);
	        
	        /*
	        JSONObject jObj = new JSONObject(sb.toString());
	        String name = jObj.getString("Name");
	        String pwd = jObj.getString("Pwd");
	        String command = jObj.getString("Command");

	        JSONObject json = new JSONObject();
	        response.setContentType("application/json");
	        response.setHeader("Cache-Control", "nocache");
	        response.setCharacterEncoding("utf-8");
	        PrintWriter out = response.getWriter();
	        out.print(json.toString());
	        */
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
}
