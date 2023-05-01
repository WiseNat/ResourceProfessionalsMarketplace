package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRoleRepository extends JpaRepository<MainRoleEntity, Long> {

    MainRoleEntity findByName(String name);

}
