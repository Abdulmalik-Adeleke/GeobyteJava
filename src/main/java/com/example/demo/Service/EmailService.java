package com.example.demo.Service;

import com.example.demo.Interface.IEmailSender;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailSender {

    // private final static Logger LOGGER = (Logger) LoggerFactory
    // .getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    private final JavaMailSender mailSender;


    @Override
    @Async
    public void sendEmail(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("admin@geobytenavbar.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
           // LOGGER.error("failed to send email", e);
            //throw new IllegalStateException("failed to send email");
        }
    }
    
}
