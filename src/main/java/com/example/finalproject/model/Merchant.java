package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
//@Table(name = "merchant")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String company_name;
    private String commercial_record;


//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "merchant")
//    @PrimaryKeyJoinColumn
//    private MyUser myUser;

    /*
    //this is correct
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "merchant")
    @PrimaryKeyJoinColumn
    private MyUser myUser;

     */

    @OneToOne
//    @MapsId
    @JsonIgnore
    private MyUser myUser;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Point> point;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Bill> bill;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Branch> branch;
}
