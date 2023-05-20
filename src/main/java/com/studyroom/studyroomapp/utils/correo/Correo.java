package com.studyroom.studyroomapp.utils.correo;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.stereotype.Component;

@Component
public class Correo {
    private final static String remitente = "studyroomapp9@gmail.com";
    private final static String password = "batwfsmkiirwzbqw";

    public void sendEmail(String subject, String message, String[] to){
        Email email = EmailBuilder.startingBlank()
            .from(remitente)
            .toMultiple(to)
            .withSubject(subject)
            .withPlainText(message)
            .buildEmail();
        
        Mailer mailer = MailerBuilder
            .withSMTPServer("smtp.gmail.com", 587, remitente, password)
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .buildMailer();
        
        mailer.sendMail(email);

        System.out.println("Correo enviado con éxito");
    }
}
