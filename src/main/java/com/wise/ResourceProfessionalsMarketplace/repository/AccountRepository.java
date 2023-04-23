package com.wise.ResourceProfessionalsMarketplace.repository;

import com.wise.ResourceProfessionalsMarketplace.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Account findAccountByEmail(@Param("email") String email);
}
