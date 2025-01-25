package com.hms.Repository;

import com.hms.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByUsername(String username);
  Optional<AppUser> findByEmail(String email);
  Optional<AppUser> findByPhone(String phone);
}
