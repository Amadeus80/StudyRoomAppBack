package com.studyroom.studyroomapp.utils.correo;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class Correo {
    private final static String remitente = "studyroomapp9";
    private final static String password = "batwfsmkiirwzbqw";

    public void sendEmail(List<String> destinatarios, String asunto, String cuerpo) {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", password);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        

        try {
            message.setFrom(new InternetAddress(remitente));
			for (String destinatario : destinatarios)
			{
				InternetAddress address = new InternetAddress(destinatario);
				message.addRecipient(Message.RecipientType.TO, address);   //Se podrían añadir varios de la misma manera
			}
            message.setSubject(asunto, "UTF-8");
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Correo enviado con éxito");
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
		}
    } 
}
