package com.hms.Repository;

import com.hms.Entity.AppUser;
import com.hms.Entity.Property;
import com.hms.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByAppUser(AppUser user);
  Optional<Review> findByAppUserAndProperty(AppUser appuser, Property property);
}