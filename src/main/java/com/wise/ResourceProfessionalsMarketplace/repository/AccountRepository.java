package com.wise.ResourceProfessionalsMarketplace.repository;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query("SELECT a FROM AccountEntity a WHERE a.email = ?1")
    AccountEntity findAccountByEmail(@Param("email") String email);
}
