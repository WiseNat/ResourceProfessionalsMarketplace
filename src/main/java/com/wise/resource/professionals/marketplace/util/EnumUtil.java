package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.AccountTypeRepository;
import com.wise.resource.professionals.marketplace.repository.BandingRepository;
import com.wise.resource.professionals.marketplace.repository.MainRoleRepository;
import com.wise.resource.professionals.marketplace.repository.SubRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Helper methods surrounding enums that exist in the database
 */
@Component
public class EnumUtil {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private BandingRepository bandingRepository;

    @Autowired
    private MainRoleRepository mainRoleRepository;

    @Autowired
    private SubRoleRepository subRoleRepository;

    /**
     * Finds the {@link AccountTypeEntity} associated with a given {@link AccountTypeEnum}
     *
     * @param accountTypeEnum the {@link AccountTypeEnum} to search for
     * @return the found {@link AccountTypeEntity} or null if it doesn't exist in the database
     */
    public AccountTypeEntity accountTypeToEntity(AccountTypeEnum accountTypeEnum) {
        if (accountTypeEnum == null) {
            return null;
        }

        return accountTypeRepository.findByName(accountTypeEnum.value);
    }

    /**
     * Finds the {@link BandingEntity} associated with a given {@link BandingEnum}
     *
     * @param bandingEnum the {@link BandingEnum} to search for
     * @return the found {@link BandingEntity} or null if it doesn't exist in the database
     */
    public BandingEntity bandingToEntity(BandingEnum bandingEnum) {
        if (bandingEnum == null) {
            return null;
        }

        return bandingRepository.findByName(bandingEnum.value);
    }

    /**
     * Finds the {@link MainRoleEntity} associated with a given {@link MainRoleEnum}
     *
     * @param mainRoleEnum the {@link MainRoleEnum} to search for
     * @return the found {@link MainRoleEntity} or null if it doesn't exist in the database
     */
    public MainRoleEntity mainRoleToEntity(MainRoleEnum mainRoleEnum) {
        if (mainRoleEnum == null) {
            return null;
        }

        return mainRoleRepository.findByName(mainRoleEnum.value);
    }

    /**
     * Finds the {@link SubRoleEntity} associated with a given {@link SubRoleEnum}
     *
     * @param subRoleEnum the {@link SubRoleEnum} to search for
     * @return the found {@link SubRoleEntity} or null if it doesn't exist in the database
     */
    public SubRoleEntity subRoleToEntity(SubRoleEnum subRoleEnum) {
        if (subRoleEnum == null) {
            return null;
        }

        return subRoleRepository.findByName(subRoleEnum.value);
    }
}
