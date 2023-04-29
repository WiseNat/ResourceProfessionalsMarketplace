package com.wise.ResourceProfessionalsMarketplace.repository;

import com.wise.ResourceProfessionalsMarketplace.entity.BandingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandingRepository extends JpaRepository<BandingEntity, Long> {

    BandingEntity findByName(String name);

}
