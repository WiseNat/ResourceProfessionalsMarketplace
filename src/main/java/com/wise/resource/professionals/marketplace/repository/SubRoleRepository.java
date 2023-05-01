package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubRoleRepository extends JpaRepository<SubRoleEntity, Long> {

    SubRoleEntity findByName(String name);

}
