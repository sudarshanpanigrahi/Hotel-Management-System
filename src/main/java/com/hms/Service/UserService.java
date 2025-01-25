package com.hms.Service;

import com.hms.Entity.AppUser;
import com.hms.Payload.DTO;
import com.hms.Payload.LoginDto;
import com.hms.Repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private JWTService js;
    private AppUserRepository ar;
    private ModelMapper mm;

    public UserService(JWTService js, AppUserRepository ar, ModelMapper mm) {
        this.js = js;
        this.ar = ar;
        this.mm = mm;
    }

    public DTO MapToDTO(AppUser au) {
        return mm.map(au, DTO.class);
    }

    public DTO signup(AppUser au) {
        AppUser save = ar.save(au);
        return MapToDTO(save);
    }

    public String login(LoginDto ld) {
        Optional<AppUser> byUsername = ar.findByUsername(ld.getUsername());
        if (byUsername.isPresent()) {
            AppUser appUser = byUsername.get();
            if (BCrypt.checkpw(ld.getPassword(), appUser.getPassword())) {
                String token = js.generateToken(appUser.getUsername());
                return token;
            }
            return null;
        }
        return null;
    }

    public void deleteUser(LoginDto ld) {

        Optional<AppUser> user = ar.findByUsername(ld.getUsername());
        if (user.isPresent()) {
            AppUser appUser = user.get();
            if (BCrypt.checkpw(ld.getPassword(), appUser.getPassword())) {
                ar.deleteById(appUser.getId());
            }
        }
    }
}
