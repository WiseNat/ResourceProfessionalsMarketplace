package com.wise.ResourceProfessionalsMarketplace.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table
public class Approval {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private ApprovalType approvalType;

    @Column(nullable = false)
    private Date date;

}
