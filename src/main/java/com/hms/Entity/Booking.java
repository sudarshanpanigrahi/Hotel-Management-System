package com.hms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "guest_name", nullable = false)
    private String guest_Name;

    @Column(name = "no_of_guest", nullable = false)
    private Integer noOfGuest;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Version
    private long version;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}