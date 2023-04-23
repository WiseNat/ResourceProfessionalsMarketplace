package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Table
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Banding banding;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private SubRole subRole;

    private String loanedClient;

    @Column(nullable = false, columnDefinition = "DECIMAL(7,2)")
    private Double dailyLateFee;

    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal costPerHour;

    private Date availabilityDate;
}
