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
	 * ResultSet) et un bean Examen.
	 */
	private static Examen map( ResultSet resultSet ) throws SQLException {
		Examen examen = new Examen();

		examen.setIdExam(resultSet.getInt("id"));
		examen.setIdProf(resultSet.getInt("id_user"));
		examen.setNomExam(resultSet.getInt("nom"));

		return examen;
	}

	
	private static final String SQL_INSERT_EXAM = "INSERT INTO Examen (id_user, nom) VALUES (?, ?)";
		
	@Override
	public void creer(Examen examen) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_EXAM, true, examen.getIdProf(), examen.getNomExam());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Examen avec sa valeur */
	            examen.setIdExam( valeursAutoGenerees.getInt(1));
	        } else {
	            throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	//TMP
	private static final String SQL_SELECT_EXAM = "SELECT id, id_user, nom FROM Examen WHERE id = ?";
		
	@Override
	public Examen trouver(int idExam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Examen examen = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_EXAM, false, idExam );
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
	
	private static final String SQL_DELETE_EXAM = "DELETE FROM Examen WHERE id  = ?";
	
	@Override
	public void supprimer(int idExam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_EXAM, false, idExam );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				//Doit on faire quelque chose avec l'examen reçu?
				//examen = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	}
	
	//Requête permettant de mettre à jour un examen
	private static final String SQL_UPDATE_EXAM = "UPDATE Examen SET id_user = ?, nom = ? WHERE id = ?";
	
	@Override
	public void miseAJour(Examen examen) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_EXAM, false, examen.getIdProf(), examen.getNomExam(), examen.getIdExam() );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				//Doit on faire quelque chose avec l'examen reçu?
				//examen = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	}
}
