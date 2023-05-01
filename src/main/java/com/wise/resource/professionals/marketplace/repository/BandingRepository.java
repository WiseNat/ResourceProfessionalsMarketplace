package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandingRepository extends JpaRepository<BandingEntity, Long> {

    BandingEntity findByName(String name);

}
