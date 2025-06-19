package com.lucascostabr.service;

import com.lucascostabr.config.EmailConfig;
import com.lucascostabr.mail.EnviaEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnviaEmailService {

    @Autowired
    private EnviaEmail enviaEmail;

    @Autowired
    private EmailConfig emailConfig;

    public void enviaEmail(String para, String assunto, String conteudo) {
        enviaEmail
                .setPara(para)
                .comAssunto(assunto)
                .comConteudo(conteudo)
                .enviar(emailConfig);
    }

}
