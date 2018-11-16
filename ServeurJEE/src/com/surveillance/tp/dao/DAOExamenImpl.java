package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.surveillance.tp.beans.Examen;

public class DAOExamenImpl implements DAOExamen {

	private DAOFactory daoFactory;

	DAOExamenImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Examen map( ResultSet resultSet ) throws SQLException {
		Examen examen = new Examen();

		//Temporaire, à voir plus tard
		examen.setIdExam(resultSet.getInt( "id" ));
		examen.setIdExam(resultSet.getInt( "idProf" ));
		examen.setIdExam(resultSet.getInt( "nomExam" ));

		return examen;
	}

	@Override
	public void creer(Examen examen) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, examen.getIdExam(), examen.getIdProf(), examen.getNomExam());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            //examen.setIdExam( valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}

	private static final String SQL_INSERT = "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES (?, ?, ?, NOW())";
	
	@Override
	public Examen trouver(String nomExam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Examen examen = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			//A VOIR
			//preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				examen = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return examen;
	}

}
