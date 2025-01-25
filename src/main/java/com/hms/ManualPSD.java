package com.hms;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ManualPSD {

    public static void main(String[] args) {
        String psd = "hey";

        // Create an instance of BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Hash the password
        String hashedPassword = encoder.encode(psd);

        // Print the hashed password
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
