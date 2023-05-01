package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

}
