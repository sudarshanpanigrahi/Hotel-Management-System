package com.hms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "room_availability")
public class RoomAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nightly_price", nullable = false)
    private Long nightly_Price;

    @Column(name = "room_type", nullable = false)
    private String room_Type;

    @Column(name = "total_rooms")
    private Long total_Rooms;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "property_id_id", nullable = false)
    private Property property_Id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

}