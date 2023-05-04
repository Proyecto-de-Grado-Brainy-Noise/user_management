package com.brainynoise.usermanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "credential", unique = true)
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
    @Column(name = "id_employee")
    private Integer idEmployee;
    @Column(name = "jobtitle")
    private String jobtitle;
    @Column(name = "area")
    private String area;
    @Column(name = "observations")
    private String observations;
    @Column(name = "creation_date")
    private String creationDate;
    @Column(name = "password")
    private String password;
    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
