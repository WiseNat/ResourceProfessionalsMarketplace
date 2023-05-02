package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    @Query(value = "SELECT a FROM ApprovalEntity a WHERE a.account IN (" +
            "SELECT u.id FROM AccountEntity u WHERE " +
            "u.firstName LIKE CONCAT('%',:firstName,'%')" +
            "AND u.lastName LIKE CONCAT('%',:lastName,'%')" +
            "AND u.email LIKE CONCAT('%',:email,'%')" +
            ")")
    List<ApprovalEntity> findAllApprovalsByPredicates(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );

    @Query(value = "SELECT a FROM ApprovalEntity a WHERE a.account IN (" +
            "SELECT u.id FROM AccountEntity u WHERE " +
            "u.firstName LIKE CONCAT('%',:firstName,'%')" +
            "AND u.lastName LIKE CONCAT('%',:lastName,'%')" +
            "AND u.email LIKE CONCAT('%',:email,'%')" +
            "AND u.accountType = :accountType" +
            ")")
    List<ApprovalEntity> findApprovalsByPredicatesAndAccountType(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("accountType") AccountTypeEntity accountType
    );

}
