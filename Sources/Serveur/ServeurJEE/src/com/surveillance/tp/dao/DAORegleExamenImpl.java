package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.surveillance.tp.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.surveillance.tp.beans.RegleExam;

/**
 * Implémentation des méthodes de recherche et mise à jour dans la table list_of_rule
 */
public class DAORegleExamenImpl implements DAORegleExamen{

	private DAOFactory daoFactory;

	DAORegleExamenImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des list_of_rule (un
	 * ResultSet) et un bean RegleExamen.
	 */
	private static RegleExam map( ResultSet resultSet ) throws SQLException {
		RegleExam regleExam = new RegleExam();

		regleExam.setIdRegle(resultSet.getInt("id_rule"));
		regleExam.setIdExam(resultSet.getInt("id_examen"));
		regleExam.setAttributs(resultSet.getString("attributs"));

		return regleExam;
	}

	//Insertion d'une règle sans attribut
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
	
	//Insertion d'une règle avec attributs
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

	//Selection d'une règle à partir de l'id de l'examen
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

	//Suppression d'une règle à partir des id de la règle et de l'examen
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

	//Mise à jour des attributs
	private static final String SQL_UPDATE_REGLE = "UPDATE list_of_rule SET attributs = ? WHERE id_examen = ? AND id_rule = ?";

	@Override
	public void miseAJour(RegleExam regleexamen) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_REGLE, false, regleexamen.getAttributs(), regleexamen.getIdExam(), regleexamen.getIdRegle() );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
}
