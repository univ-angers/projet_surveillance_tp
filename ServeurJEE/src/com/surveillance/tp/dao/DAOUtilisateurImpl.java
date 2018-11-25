package com.surveillance.tp.dao;

import static com.surveillance.tp.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.surveillance.tp.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

		utilisateur.setId(resultSet.getInt("id_user"));
		utilisateur.setNom(resultSet.getString("nom_user"));
		utilisateur.setPrenom(resultSet.getString("prenom"));
		utilisateur.setPassword(resultSet.getString("password"));
		utilisateur.setMail(resultSet.getString("mail"));
		utilisateur.setGroupe(resultSet.getString("groupe"));

		return utilisateur;
	}

	//Requête permettant d'insérer un utilisateur dans la BDD
	private static final String SQL_INSERT_ETUD = "INSERT INTO Utilisateur (prenom, nom_user, password, mail, groupe) VALUES (?, ?, ?, ?, ?)";

	@Override
	public void creer(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_ETUD, true, utilisateur.getPrenom(), utilisateur.getNom(), utilisateur.getPassword(), utilisateur.getMail(), "eleve");
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
	private static final String SQL_SELECT_UTILISATEUR = "SELECT id_user, prenom, nom_user, password, mail, groupe FROM Utilisateur WHERE mail = ?";

	@Override
	public Utilisateur trouver(String mail) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_UTILISATEUR, false, mail );
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

	private static final String SQL_DELETE_UTIL = "DELETE FROM Utilisateur WHERE id_user  = ?";

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
				//utilisateur = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DAOException( e );
		} finally {
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	}

	//Requête permettant de mettre à jour un examen
	private static final String SQL_UPDATE_UTIL = "UPDATE Utilisateur SET prenom = ?, nom_user = ?, password = ?, mail = ?, groupe = ? WHERE id_user = ?";

	@Override
	public void miseAJour(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE_UTIL, false, utilisateur.getPrenom(), utilisateur.getNom(), utilisateur.getPassword(), utilisateur.getMail(), utilisateur.getGroupe(), utilisateur.getId() );
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
