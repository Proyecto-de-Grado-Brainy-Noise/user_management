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
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "result")
public class Result {
    @Id
    @Column(name = "id", unique = true)
    private BigInteger id;
    @Column(name = "emailSearcher")
    private String emailSearcher;
    @Column(name = "searchDate")
    private Date searchDate;
    @Column(name = "result")
    private String result;
    @Column(name = "fileName")
    private String fileName;
}
