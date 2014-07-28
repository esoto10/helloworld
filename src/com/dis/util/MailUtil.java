package com.dis.util;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class MailUtil {
	
	
	 public static void enviarMail(String destinatario, String nombreDestinatario, String asunto, String cuerpo){
	       
		 try
	        {
	            // Propiedades de la conexión
	            Properties props = new Properties();
	            props.setProperty("mail.smtp.host", "smtp.gmail.com");
	            props.setProperty("mail.smtp.starttls.enable", "true");
	            props.setProperty("mail.smtp.port", "587");
	            props.setProperty("mail.smtp.user", "agenciadevuelos@gmail.com"); 
	            props.setProperty("mail.smtp.auth", "true");

	            // Preparamos la sesion
	            Session session = Session.getDefaultInstance(props);

	            // Construimos el mensaje
	            MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("agenciadevuelos@gmail.com","Sistema de Agencia de Viajes")); 
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario,"Sr. "+nombreDestinatario)); 
	            message.setSubject(asunto,"UTF-8");
	            message.setText(cuerpo,"UTF-8","html");
	            
	            // Lo enviamos.
	            Transport t = session.getTransport("smtp");
	            t.connect("agenciadevuelos@gmail.com", "agencia123");
	            t.sendMessage(message, message.getAllRecipients());

	            // Cierre.
	            t.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	 
	 
}
