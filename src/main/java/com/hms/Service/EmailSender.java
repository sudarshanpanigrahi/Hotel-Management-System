package com.hms.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private JavaMailSender jms;

    public EmailSender(JavaMailSender jms) {
        this.jms = jms;
    }

    public void sendOtp( String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP for Verification");
        message.setText(otp + "\n Its only Valid for 5 Minutes");
        jms.send(message);
    }
}
