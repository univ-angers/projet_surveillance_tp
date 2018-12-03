package com.surveillance.tp.utilitaire;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mailUtil {

	/**
	 * Créé une chaine de 30 caractères aléatoires
	 * @return
	 */
	public static String creationChaine30()
	{
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String token = "";
		for(int i=0;i<30;i++)
		{
			int x = (int)Math.floor(Math.random() * 62);
			token += chars.charAt(x);
		}		
		return token;
	}

	/**
	 * Envoie un mail de reset de mot de passe au destinataire
	 * Source: http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
	 * @param destination
	 * @param token
	 * @return
	 */
	public static boolean envoyerReset(String destination, String token)
	{
		//adresse mail = projetsurvtp@gmail.com
		//mdp mail = projsurv2018

		final String MAILER_VERSION = "Java";
		boolean result = false;
		
		try 
		{
			final String IP_SERVEUR = InetAddress.getLocalHost().getHostAddress();

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("projetsurvtp@gmail.com","projsurv2018");
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("projetsurvtp@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destination));
			message.setSubject("Récupératon mot de passe");
			message.setText("Cliquez sur ce lien pour réinitialiser votre mot de passe \n" +
					//A voir
					"http://" + IP_SERVEUR + ":8080/ServeurJEE/receptionReset?" + token );

			Transport.send(message);

			System.out.println("Mail envoyé");
			result = true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return result;
	}
}
