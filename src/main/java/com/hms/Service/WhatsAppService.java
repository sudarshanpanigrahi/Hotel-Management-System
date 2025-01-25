package com.hms.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class WhatsAppService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String fromWhatsAppNumber;

    @PostConstruct
    public void WhatsAppService() {
        // Initialize Twilio only once
        Twilio.init(accountSid, authToken);
    }

    public String sendWhatsAppMessageWithPdf(String toWhatsAppNumber, String messages) {
        try {
            // Twilio requires the file to be publicly accessible, so host it or use a public link
            Message message = Message.creator(
                            new PhoneNumber("whatsapp:" + toWhatsAppNumber),
                            new PhoneNumber("whatsapp:" + fromWhatsAppNumber),
                    messages)
                    .create();

            return "WhatsApp message sent successfully. Message SID: " + message.getSid();
        } catch (Exception e) {
            return "Error sending WhatsApp message: " + e.getMessage();
        }
    }
}
