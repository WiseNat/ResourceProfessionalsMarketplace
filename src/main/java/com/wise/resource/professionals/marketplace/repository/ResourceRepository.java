package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    interface IResourceCollection {
        Long getCount();

        BandingEntity getBanding();

        MainRoleEntity getMainRole();

        Optional<SubRoleEntity> getSubRole();

        BigDecimal getCostPerHour();
    }

    @Query("SELECT COUNT(r) AS count, r.banding AS banding, r.subRole AS subRole, r.mainRole AS mainRole, r.costPerHour AS costPerHour " +
            "FROM ResourceEntity r LEFT JOIN r.subRole WHERE r.loanedClient is null GROUP BY r.banding, r.subRole, r.mainRole, r.costPerHour")
    List<IResourceCollection> findAllByCollection();

    // TODO: This
//    List<IResourceCollection> findAllByCollectionWithPredicates();
}
