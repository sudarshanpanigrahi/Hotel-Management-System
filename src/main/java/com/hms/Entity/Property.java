package com.hms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "no_of_bedroom", nullable = false)
    private Integer noOfBedroom;

    @Column(name = "no_of_bathroom", nullable = false)
    private Integer noOfBathroom;

    @Column(name = "no_of_guest", nullable = false)
    private Integer noOfGuest;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

}