package com.wise.resource.professionals.marketplace.repository;

import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    @Query("SELECT COUNT(r) AS count, r.banding AS banding, r.subRole AS subRole, r.mainRole AS mainRole, r.costPerHour AS costPerHour " +
            "FROM ResourceEntity r LEFT JOIN r.subRole " +
            "WHERE r.loanedClient is null " +
            "AND (:banding is null OR r.banding = :banding) " +
            "AND (:mainRole is null OR r.mainRole = :mainRole) " +
            "AND (:subRole is null OR r.subRole = :subRole) " +
            "AND (:costPerHour is null OR r.costPerHour <= :costPerHour) " +
            "GROUP BY r.banding, r.subRole, r.mainRole, r.costPerHour")
    List<IResourceCollection> findAllByCollectionWithPredicates(
            @Param("banding") BandingEntity banding,
            @Param("mainRole") MainRoleEntity mainRole,
            @Param("subRole") SubRoleEntity subRole,
            @Param("costPerHour") BigDecimal costPerHour
    );

    List<ResourceEntity> findByBandingAndMainRoleAndSubRoleAndCostPerHour(
            BandingEntity banding, MainRoleEntity mainRole, SubRoleEntity subRole, BigDecimal costPerHour);

    interface IResourceCollection {
        Long getCount();

        BandingEntity getBanding();

        MainRoleEntity getMainRole();

        Optional<SubRoleEntity> getSubRole();

        BigDecimal getCostPerHour();
    }
}
