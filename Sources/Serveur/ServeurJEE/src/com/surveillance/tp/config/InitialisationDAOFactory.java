package com.surveillance.tp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.surveillance.tp.dao.DAOFactory;

/**
 * Création de la DAO Factory au lancement de l'application
 */
public class InitialisationDAOFactory implements ServletContextListener {
	private static final String ATT_DAO_FACTORY="daofactory";
    private DAOFactory daoFactory;

	@Override
	public void contextInitialized( ServletContextEvent event ) {
		// Récupération du ServletContext lors du chargement de l'application
		ServletContext servletContext = event.getServletContext();
		// Instanciation de notre DAOFactory
		daoFactory=DAOFactory.getInstance();
		// Enregistrement dans un attribut ayant pour portée toute l'application
		servletContext.setAttribute(ATT_DAO_FACTORY, daoFactory);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {}
}