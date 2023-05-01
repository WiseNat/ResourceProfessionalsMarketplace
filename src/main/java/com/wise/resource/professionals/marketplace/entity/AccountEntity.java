package com.wise.resource.professionals.marketplace.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private ResourceEntity resource;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private AccountTypeEntity accountType;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "char(60)")
    private String encodedPassword;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Boolean isApproved;

}
