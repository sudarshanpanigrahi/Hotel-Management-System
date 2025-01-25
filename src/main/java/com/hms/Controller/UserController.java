package com.hms.Controller;

import com.hms.Entity.AppUser;
import com.hms.Payload.DTO;
import com.hms.Payload.LoginDto;
import com.hms.Payload.jwtToken;
import com.hms.Repository.AppUserRepository;
import com.hms.Service.JWTService;
import com.hms.Service.OTPService;
import com.hms.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Security")
public class UserController {

    private UserService us;
    private JWTService js;
    private AppUserRepository ar;
    private OTPService os;
    public UserController(UserService us, JWTService js, AppUserRepository ar, OTPService os) {
        this.us = us;
        this.js = js;
        this.ar = ar;
        this.os = os;
    }

    //USER SIGN-UP

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AppUser au){
        Optional<AppUser> user = ar.findByUsername(au.getUsername());
        if(user.isPresent()){
            return ResponseEntity.status(226).body("Username already exists.");
        }
        Optional<AppUser> byEmail = ar.findByEmail(au.getEmail());
        if(byEmail.isPresent()){
            return ResponseEntity.status(226).body("Email already exists.");
        }
        Optional<AppUser> byPhone = ar.findByPhone(au.getPhone());
        if(byPhone.isPresent()){
            return ResponseEntity.status(226).body("Phone number already exists.");
        }
        au.setPassword(BCrypt.hashpw(au.getPassword(),BCrypt.gensalt(10)));
        au.setRole("ROLE_USER");
        DTO dt = us.signup(au);
        return ResponseEntity.status(201).body(dt);
    }

    //OWNER SIGN-UP

    @PostMapping("/property/signup")
    public ResponseEntity<?> OwnerSignup(@RequestBody AppUser au){
        Optional<AppUser> user = ar.findByUsername(au.getUsername());
        if(user.isPresent()){
            return ResponseEntity.status(226).body("Username already exists.");
        }
        Optional<AppUser> byEmail = ar.findByEmail(au.getEmail());
        if(byEmail.isPresent()){
            return ResponseEntity.status(226).body("Email already exists.");
        }
        Optional<AppUser> byPhone = ar.findByPhone(au.getPhone());
        if(byPhone.isPresent()){
            return ResponseEntity.status(226).body("Phone number already exists.");
        }
        au.setPassword(BCrypt.hashpw(au.getPassword(),BCrypt.gensalt(10)));
        au.setRole("ROLE_OWNER");
        DTO dt = us.signup(au);
        return ResponseEntity.status(201).body(dt);
    }

                                                                  //Generate USER LOGIN OTP
    @PostMapping("/login-OTP")
    public ResponseEntity<?> loginOtp(@RequestParam String email){
        String otp = os.GenerateOtp(email);
        return new ResponseEntity<>(otp, HttpStatus.OK);
    }


                                                                  //USER LOGIN Through Valid OTP
    @PostMapping("/login-Valid")
    public ResponseEntity<?> loginValid(@RequestParam String email,@RequestParam String otp){
        Boolean validate = os.Validate(email,otp);
        if(validate){
            String s = js.generateToken(email);
            return ResponseEntity.status(200).body(s);
        }
        return new ResponseEntity<>("Invalid OTP", HttpStatus.INTERNAL_SERVER_ERROR);
    }

                                                                //Simply Login through Encrypted Password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto ld){
        String val = us.login(ld);
        jwtToken jk = new jwtToken();
        jk.setToken(val);
        jk.setType("JWT");
        return new ResponseEntity<>(jk, HttpStatus.OK);
    }

    @DeleteMapping("/Admin")
    public String deleteUser(@RequestBody LoginDto ld){
        us.deleteUser(ld);
        return "User deleted";
    }

    @GetMapping("/getAll")
    public List<AppUser> getALl(){
        return ar.findAll();
    }

}
