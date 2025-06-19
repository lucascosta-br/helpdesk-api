package com.lucascostabr.mail;

import com.lucascostabr.config.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Component
public class EnviaEmail implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(EnviaEmail.class);
    private final JavaMailSender mailSender;

    private String para;
    private String assunto;
    private String conteudo;
    private ArrayList<InternetAddress> destinatarios = new ArrayList<>();

    public EnviaEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public EnviaEmail setPara(String para) {
        this.para = para;
        this.destinatarios = getDestinatarios(para);
        return this;
    }

    public EnviaEmail comAssunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public EnviaEmail comConteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void enviar(EmailConfig config) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(config.getUsername());
            helper.setTo(destinatarios.toArray(new InternetAddress[0]));
            helper.setSubject(assunto);
            helper.setText(conteudo, true);
            mailSender.send(message);

            logger.info("Email enviado para %s com o assunto '%s'%n", para, assunto);
            reset();
        } catch (MessagingException e) {
            throw new RuntimeException("Error ao enviar o email",e);
        }
    }

    private void reset() {
        this.para = null;
        this.assunto = null;
        this.conteudo = null;
        this.destinatarios = null;
    }

    private ArrayList<InternetAddress> getDestinatarios(String para) {
        var seTiverEspacos = para.replaceAll("\\s", "");
        StringTokenizer tokenizer = new StringTokenizer(seTiverEspacos, ";");
        ArrayList<InternetAddress> destinatariosList = new ArrayList<>();

        while (tokenizer.hasMoreElements()) {
            try {
                destinatariosList.add(new InternetAddress(tokenizer.nextElement().toString()));
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }

        return destinatariosList;
    }

}
