package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    List<ResourceEntity> findAllByLoanedClientNull();

}
