package com.code405.util;


import com.code405.entity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Created by birthright on 24.02.17.
 */
@Component
public class CreateEmailMessageHelper {
    @Autowired
    private Environment environment;
    @Autowired
    private MailSender mailSender;

    private void sendEmail(String subject, String text,
                           String from, String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        mailSender.send(simpleMailMessage);
    }


    public void sendResetPasswordEmail(String appUrl, String token, User user) {
        String subject = "Портал кфу - сброс пароля";
        String from = environment.getProperty("smtp.username");
        String to = user.getEmail();
        String confirmationUrl =
                appUrl + "/login?reset_password&token=" + token + "&u=" + user.getId();
        String text = "Перейдите по ссылке, чтобы сбросить пароль.\r\n" + confirmationUrl;
        sendEmail(subject, text, from, to);
    }
}
