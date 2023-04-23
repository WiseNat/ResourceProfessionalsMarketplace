package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private AccountType accountType;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "char(64)")
    private String hash;

    @Column(nullable = false, columnDefinition = "char(64)")
    private String salt;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean is_approved;

}
