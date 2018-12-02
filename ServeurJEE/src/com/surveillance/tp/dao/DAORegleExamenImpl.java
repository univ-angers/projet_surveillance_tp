package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.surveillance.tp.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;

import com.surveillance.tp.beans.RegleExam;
import com.surveillance.tp.beans.Utilisateur;

public class DAORegleExamenImpl implements DAORegleExamen{

	private DAOFactory daoFactory;

	DAORegleExamenImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des list_de_regle (un
	 * ResultSet) et un bean RegleExamen.
	 */
	private static RegleExam map( ResultSet resultSet ) throws SQLException {
		RegleExam regleExam = new RegleExam();

		regleExam.setIdRegle(resultSet.getInt("id_rule"));
		regleExam.setIdExam(resultSet.getInt("id_examen"));
		regleExam.setAttributs(resultSet.getString("attributs"));

		return regleExam;
	}

	private static final String SQL_INSERT_REGLE = "INSERT INTO list_of_rule (id_rule, id_examen) VALUES (?, ?)";

	@Override
	public void creer(RegleExam regleexam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_REGLE, false, regleexam.getIdRegle(), regleexam.getIdExam());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création de la règle de l'examen, aucune ligne ajoutée dans la table." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}		
	}
	
	private static final String SQL_INSERT_REGLE_ATT = "INSERT INTO list_of_rule (id_rule, id_examen, attributs) VALUES (?, ?, ?)";
	
	@Override
	public void creerAttribut(RegleExam regleexam, String jsTab) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_REGLE_ATT, false, regleexam.getIdRegle(), regleexam.getIdExam(), jsTab);
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création de la règle de l'examen, aucune ligne ajoutée dans la table." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}	
	}

	private static final String SQL_SELECT_REGLE = "SELECT id_rule, id_examen, attributs FROM list_of_rule WHERE id_examen = ?";

	@Override
	public ArrayList<RegleExam> trouver(int idExam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		RegleExam regle = null;
		ArrayList<RegleExam> listRegle = new ArrayList<RegleExam>();

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_REGLE, false, idExam );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while ( resultSet.next() ) {
				regle = map( resultSet );
				listRegle.add(regle);
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return listRegle;
	}

	private static final String SQL_DELETE_REGLE = "DELETE FROM list_of_rule WHERE id_rule  = ? AND id_examen = ?";

	@Override
	public void supprimer(int idRegle, int idExam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_REGLE, false, idRegle, idExam);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	//Supprime toutes les règles d'un examen précis
	private static final String SQL_DELETE_REGLE_EXAM = "DELETE FROM list_of_rule WHERE id_examen = ?";

	@Override
	public void supprimerExam(int idExam) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_REGLE_EXAM, false, idExam);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	private static final String SQL_UPDATE_REGLE = "UPDATE list_of_rule SET id_examen = ? WHERE id_rule = ?";

	@Override
	public void miseAJour(RegleExam regleexamen) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_REGLE, false, regleexamen.getIdExam(), regleexamen.getIdRegle() );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
}
