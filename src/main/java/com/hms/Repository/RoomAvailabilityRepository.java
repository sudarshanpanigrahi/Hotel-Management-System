package com.hms.Repository;

import com.hms.Entity.Property;
import com.hms.Entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {


    @Query("SELECT r FROM RoomAvailability r " +
            "WHERE r.property_Id.id = :propertyId " +
            "AND r.date BETWEEN :from AND :to " +
            "AND r.room_Type = :type")
    List<RoomAvailability> findByDate(@Param("from") LocalDate from,
                                      @Param("to") LocalDate to,
                                      @Param("type") String type,
                                      @Param("propertyId") long propertyId);

}