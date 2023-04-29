package com.wise.ResourceProfessionalsMarketplace.repository;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    ApprovalEntity findByAccount(AccountEntity account);
}
