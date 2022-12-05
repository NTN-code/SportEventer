package com.spring.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String emailTo, String subject, String htmlMsg) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setContent(htmlMsg, "text/html");

        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setFrom(username);
        helper.setTo(emailTo);
        helper.setSubject(subject);

        mailSender.send(message);
    }
}