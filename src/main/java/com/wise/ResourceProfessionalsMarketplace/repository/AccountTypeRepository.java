package com.wise.ResourceProfessionalsMarketplace.repository;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountTypeRepository extends JpaRepository<AccountTypeEntity, Long> {
    @Query("SELECT a FROM AccountTypeEntity a WHERE a.name = ?1")
    AccountTypeEntity findAccountTypeByString(@Param("name") String name);
}
