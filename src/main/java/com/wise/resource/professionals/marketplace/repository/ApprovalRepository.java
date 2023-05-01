package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    ApprovalEntity findByAccount(AccountEntity account);

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

    // TODO: Project Manager only
    // TODO: Resource only

    // SELECT * FROM newdb.approval WHERE account_id IN (SELECT id FROM newdb.account WHERE first_name LIKE "%John%");

    // LIKE CONCAT('%',:firstName,'%'))
    // SELECT email
    //FROM account
    //WHERE id in (SELECT c_id
    //             FROM address
    //             WHERE city='new york');
}
