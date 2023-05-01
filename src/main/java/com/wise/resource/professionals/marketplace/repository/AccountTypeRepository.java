package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountTypeEntity, Long> {
    AccountTypeEntity findByName(String name);
}
