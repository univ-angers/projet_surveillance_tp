package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.surveillance.tp.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.surveillance.tp.beans.Regle;

/**
 * Implémentation des méthodes de recherche et mise à jour dans la table Rule
 */
public class DAORegleImpl implements DAORegle{

	private DAOFactory daoFactory;

	DAORegleImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}
	
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des Rule (un
	 * ResultSet) et un bean Regle.
	 */
	private static Regle map( ResultSet resultSet ) throws SQLException {
		Regle regle = new Regle();

		regle.setIdRegle(resultSet.getInt("id_rule"));
		regle.setDescription(resultSet.getString("description"));
		regle.setNiveauRegle(resultSet.getInt("id_niveau"));	
		regle.setIdWatcher(resultSet.getInt("id_watcher"));

		return regle;
	}
	
	//Insertion d'une règle
	private static final String SQL_INSERT_REGLE = "INSERT INTO Rule (description, id_niveau, id_watcher) VALUES (?, ?, ?)";
	
	@Override
	public void creer(Regle regle) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_REGLE, true, regle.getDescription(), regle.getNiveauRegle(), regle.getIdWatcher());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
				/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				regle.setIdRegle(valeursAutoGenerees.getInt(1));
			} else {
				throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}		
	}
	
	//Recherche d'une règle par rapport à sa description
	private static final String SQL_SELECT_REGLE_TYPE = "SELECT id_rule, description, id_niveau, id_watcher FROM Rule WHERE description = ?";

	@Override
	public Regle trouverSt(String typeRegle) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Regle regle = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_REGLE_TYPE, false, typeRegle );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				regle = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return regle;
	}
	
	//Recherche d'une règle par rapport à son ID
	private static final String SQL_SELECT_REGLE_ID = "SELECT id_rule, description, id_niveau, id_watcher FROM Rule WHERE id_rule = ?";

	@Override
	public Regle trouver(int idRegle) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Regle regle = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_REGLE_ID, false, idRegle );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				regle = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return regle;
	}

	//Suppression d'une règle par rapport à son ID
	private static final String SQL_DELETE_REGLE = "DELETE FROM Rule WHERE id_rule  = ?";

	@Override
	public void supprimer(int idRegle) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_REGLE, false, idRegle );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	//Mise à jour d'une règle par rapport à son ID
	private static final String SQL_UPDATE_REGLE = "UPDATE Rule SET description = ?, id_niveau = ?, id_watcher = ? WHERE id_rule = ?";

	@Override
	public void miseAJour(Regle regle) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_REGLE, false, regle.getDescription(), regle.getNiveauRegle(), regle.getIdWatcher(), regle.getIdRegle() );
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
}
