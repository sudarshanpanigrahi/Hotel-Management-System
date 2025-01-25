package com.hms.Repository;

import com.hms.Entity.Images;
import com.hms.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> findByProperty(Property property);
}