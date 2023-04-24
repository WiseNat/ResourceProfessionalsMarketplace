package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "resource")
public class ResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private BandingEntity banding;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private SubRoleEntity subRole;

    private String loanedClient;

    @Column(nullable = false, columnDefinition = "DECIMAL(7,2)")
    private Double dailyLateFee;

    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal costPerHour;

    private Date availabilityDate;
}
