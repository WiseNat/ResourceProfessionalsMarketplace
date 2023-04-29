package com.wise.ResourceProfessionalsMarketplace.repository;

import com.wise.ResourceProfessionalsMarketplace.entity.MainRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRoleRepository extends JpaRepository<MainRoleEntity, Long> {

    MainRoleEntity findByName(String name);

}
