package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.Utilisateur;

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

		examen.setIdExam(resultSet.getInt("id_examen"));
		examen.setIdProf(resultSet.getInt("id_user"));
		examen.setMatiere(resultSet.getString("matiere"));
		examen.setDuree(resultSet.getTime("duree"));
		examen.setHeureDebut(resultSet.getTimestamp("heure_debut"));		

		return examen;
	}

	
	private static final String SQL_INSERT_EXAM = "INSERT INTO Examen (id_user, matiere, duree, heure_debut) VALUES (?, ?, ?, NOW())";
		
	@Override
	public void creer(Examen examen) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_EXAM, true, examen.getIdProf(), examen.getMatiere(), examen.getDuree());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec de la création de l'examen, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Examen avec sa valeur */
	            examen.setIdExam(valeursAutoGenerees.getInt(1));
	        } else {
	            throw new DAOException( "Échec de la création de l'examen en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	private static final String SQL_SELECT_EXAM = "SELECT id_examen, id_user, matiere, duree, heure_debut FROM Examen WHERE id_examen = ?";
		
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
	
	private static final String SQL_SELECT_EXAM_UTIL = "SELECT id_examen, id_user, matiere, duree, heure_debut FROM Examen WHERE id_user = ? and etat='on'";
	@Override
	public Examen trouverExamenUtil(int id_util) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Examen examen = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_EXAM_UTIL, false, id_util );
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
	
	
	private static final String SQL_DELETE_EXAM = "DELETE FROM Examen WHERE id_examen  = ?";
	
	@Override
	public void supprimer(int idExam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_EXAM, false, idExam );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	private static final String SQL_UPDATE2_EXAM = "UPDATE Examen SET etat='off' WHERE id_user  = ? and etat='on'";
	@Override
	public void updateExamen(int id_user) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE2_EXAM, false, id_user );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	
	//Requête permettant de mettre à jour un examen
	private static final String SQL_UPDATE_EXAM = "UPDATE Examen SET id_user = ?, matiere = ?, duree = ?, heure_debut = ? WHERE id_examen = ?";
	
	@Override
	public void miseAJour(Examen examen) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_EXAM, false, examen.getIdProf(), examen.getMatiere(),examen.getDuree(), examen.getHeureDebut(), examen.getIdExam() );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	

	private static final String SQL_SELECT_LISTE_EXAM = "SELECT id_examen, id_user, matiere, duree, heure_debut FROM Examen WHERE id_user= ?";

	@Override
	public 
	ArrayList<Examen>recupererExams(int id_user) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Examen> exams = new ArrayList<Examen>();
		
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion =  daoFactory.getConnection();
			System.out.println("DEBUG 1");
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_LISTE_EXAM,false,id_user);
			System.out.println("DEBUG 2");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Examen exam = null;
				exam= map( resultSet );
				exams.add(exam);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return exams;
	}
}
