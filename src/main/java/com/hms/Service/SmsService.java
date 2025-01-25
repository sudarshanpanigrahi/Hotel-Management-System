package com.hms.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    public String sendSms(String to, String message) {
        try {
            if (!to.startsWith("+")) {
                throw new IllegalArgumentException("Phone number must be in E.164 format (e.g., +91XXXX....)");
            }
            Message sms = Message.creator(
                    new PhoneNumber(to),                // To phone number
                    new PhoneNumber(twilioPhoneNumber), // From Twilio phone number
                    message                            // Message body
            ).create();

            return "Message sent successfully: " + sms.getSid();
        } catch (Exception e) {
            return "Error sending SMS: " + e.getMessage();
        }
    }
}
