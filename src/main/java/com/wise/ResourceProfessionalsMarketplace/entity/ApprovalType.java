package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class ApprovalType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

}
