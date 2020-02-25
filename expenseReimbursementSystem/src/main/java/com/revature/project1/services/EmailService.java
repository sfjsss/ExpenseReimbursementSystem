package com.revature.project1.services;

import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class EmailService {

	public void sendEmail(String recipient, String subject, String content) throws MessagingException {
		
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String senderEmail = "tianyulidev@gmail.com";
		String password = "revature1!";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, password);
			}
		});
		
		Message message = prepareMessage(session, senderEmail, recipient, subject, content);
		Transport.send(message);
	}
	
	private static Message prepareMessage(Session session, String senderEmail, String recipient, String subject, String content) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(content);
			return message;
			
		} catch (Exception e) {
			Logger.getRootLogger().log(null, Level.SEVERE, e);
		}
		return null;
	}
}
