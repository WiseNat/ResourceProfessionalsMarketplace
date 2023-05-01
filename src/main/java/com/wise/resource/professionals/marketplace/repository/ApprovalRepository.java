package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    ApprovalEntity findByAccount(AccountEntity account);

    // TODO: THIS
    @Query(value = "SELECT a FROM ApprovalEntity a WHERE a.account IN (SELECT a.id FROM AccountEntity WHERE a.firstName = firstName)")
    List<ApprovalEntity> findAllApprovalsByPredicates(@Param("firstName") String firstName);

    // LIKE CONCAT('%',:firstName,'%'))
    // SELECT email
    //FROM account
    //WHERE id in (SELECT c_id
    //             FROM address
    //             WHERE city='new york');
}
