package com.brainynoise.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "name")
    private String name;
    @Column(name = "name2")
    private String name2;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "lastname2")
    private String lastname2;
    @Column(name = "doctype")
    private int doctype;
    @Column(name = "document")
    private String document;
    @Column(name = "birthdate")
    private String birthdate;
    @Column(name = "idEmployee")
    private BigInteger idEmployee;
    @Column(name = "jobtitle")
    private String jobtitle;
    @Column(name = "area")
    private String area;
    @Column(name = "observations")
    private String observations;
    @Column(name = "creationDate")
    private String creationDate;
}
