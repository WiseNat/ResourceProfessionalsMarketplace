package com.wise.ResourceProfessionalsMarketplace.repository;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query("SELECT a FROM AccountEntity a WHERE a.email = ?1 AND a.accountType = ?2")
    AccountEntity findAccountByEmailAndAccountType(
            @Param("email") String email,
            @Param("account_type") AccountTypeEntity accountTypeEntity
    );
}
