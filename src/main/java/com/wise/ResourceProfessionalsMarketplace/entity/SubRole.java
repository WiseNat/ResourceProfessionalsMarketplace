package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class SubRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private MainRole mainRole;

}
