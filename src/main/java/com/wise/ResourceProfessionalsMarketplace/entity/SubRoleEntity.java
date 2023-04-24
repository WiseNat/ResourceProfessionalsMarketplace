package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "sub_role")
public class SubRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private MainRoleEntity mainRole;

}
