package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByEmailAndAccountType(String email, AccountTypeEntity accountType);

    AccountEntity findByResource(ResourceEntity resource);

    @Query("SELECT a " +
            "FROM AccountEntity a " +
            "WHERE (:firstName is null OR a.firstName LIKE CONCAT('%',:firstName,'%')) " +
            "AND (:lastName is null OR a.lastName LIKE CONCAT('%',:lastName,'%')) " +
            "AND a.resource IN (" +
                "SELECT r.id FROM ResourceEntity r " +
                "WHERE r.loanedClient IS NOT null " +
                "AND (:loanedClient is null OR r.loanedClient LIKE CONCAT('%',:loanedClient,'%')) " +
                "AND (:banding is null OR r.banding = :banding) " +
                "AND (:mainRole is null OR r.mainRole = :mainRole) " +
                "AND (:subRole is null OR r.subRole = :subRole) " +
            ")")
    List<AccountEntity> findAllWithPredicates(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("loanedClient") String loanedClient,
            @Param("banding") BandingEntity banding,
            @Param("mainRole") MainRoleEntity mainRole,
            @Param("subRole") SubRoleEntity subRole
    );
}
