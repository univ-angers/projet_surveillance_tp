package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet qui déconnecte l'utilisateur
 */
public class Logout extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();  
        session.invalidate(); 
		response.sendRedirect("/ServeurJEE/home");
	}
}
