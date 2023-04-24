package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "approval")
public class ApprovalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private ApprovalTypeEntity approvalType;

    @Column(nullable = false)
    private Date date;

}
