package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.surveillance.tp.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.Utilisateur;

public class DAOUtilisateurImpl implements DAOUtilisateur {

	private DAOFactory daoFactory;

	DAOUtilisateurImpl( DAOFactory daoFactory ) {
		this.daoFactory = daoFactory;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Utilisateur map( ResultSet resultSet ) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();

		utilisateur.setId(resultSet.getInt("id"));
		utilisateur.setNom(resultSet.getString("nom"));
		utilisateur.setPrenom(resultSet.getString("prenom"));
		utilisateur.setNumero_etudiant(resultSet.getString("numero_etudiant"));

		return utilisateur;
	}

	//Requête permettant d'insérer un utilisateur dans la BDD
	private static final String SQL_INSERT_ETUD = "INSERT INTO Utilisateur (prenom, nom, numero_etudiant) VALUES (?, ?, ?)";

	@Override
	public void creer(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_ETUD, true, utilisateur.getPrenom(), utilisateur.getNom(), utilisateur.getNumero_etudiant());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if ( valeursAutoGenerees.next() ) {
				/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				utilisateur.setId(valeursAutoGenerees.getInt(1));
			} else {
				throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
		}
	}

	//Requête permettant de rechercher un utilisateur dans la BDD par son ID
	private static final String SQL_SELECT_UTILISATEUR = "SELECT id, prenom, nom, numero_etudiant FROM Utilisateur WHERE id = ?";

	@Override
	public Utilisateur trouver(int idUtil) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_UTILISATEUR, false, idUtil );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				utilisateur = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
		return utilisateur;
	}

	private static final String SQL_DELETE_UTIL = "DELETE FROM Utilisateur WHERE id  = ?";

	@Override
	public void supprimer(int idUtil) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE_UTIL, false, idUtil );
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
	private static final String SQL_UPDATE_UTIL = "UPDATE Utilisateur SET prenom = ?, nom = ?, numero_etudiant = ? WHERE id = ?";

	@Override
	public void miseAJour(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_UTIL, false, utilisateur.getPrenom(), utilisateur.getNom(), utilisateur.getNumero_etudiant(), utilisateur.getId() );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if ( resultSet.next() ) {
				//Doit on faire quelque chose avec l'utilisateur reçu?
				//examen = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	}

}
