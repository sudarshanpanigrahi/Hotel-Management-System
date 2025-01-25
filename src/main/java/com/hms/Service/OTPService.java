package com.hms.Service;

import com.twilio.rest.api.v2010.account.incomingphonenumber.Mobile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService {

    private final Map<String,OTPDetails> otpStore =
            new HashMap<>();
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000;
    private EmailSender es;

    public OTPService(EmailSender es) {
        this.es = es;
    }


    public String GenerateOtp(String email){
        String otp = String.format("%06d", new Random().nextInt(999999));
        OTPDetails otd = new OTPDetails(otp, System.currentTimeMillis());
        otpStore.put(email,otd);
        es.sendOtp(email,otp);
        return "OTP Send";
    }

    public Boolean Validate(String email,String otp){
     OTPDetails otd = otpStore.get(email);

     if(otd == null) {return false;}
    if(System.currentTimeMillis()-otd.getTimestamp()>OTP_EXPIRATION_TIME){

        otpStore.remove(email);
            return false;
    }
        return otd.getOtp().equals(otp);
    }

    private static class OTPDetails{
        final private String otp;
        final private long timestamp;

        public OTPDetails(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }

        public String getOtp() {
            return otp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}

