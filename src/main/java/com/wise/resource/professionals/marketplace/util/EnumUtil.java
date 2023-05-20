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

    public AccountTypeEntity accountTypeToEntity(AccountTypeEnum accountTypeEnum) {
        if (accountTypeEnum == null) {
            return null;
        }

        return accountTypeRepository.findByName(accountTypeEnum.value);
    }

    public BandingEntity bandingToEntity(BandingEnum bandingEnum) {
        if (bandingEnum == null) {
            return null;
        }

        return bandingRepository.findByName(bandingEnum.value);
    }

    public MainRoleEntity mainRoleToEntity(MainRoleEnum mainRoleEnum) {
        if (mainRoleEnum == null) {
            return null;
        }

        return mainRoleRepository.findByName(mainRoleEnum.value);
    }

    public SubRoleEntity subRoleToEntity(SubRoleEnum subRoleEnum) {
        if (subRoleEnum == null) {
            return null;
        }

        return subRoleRepository.findByName(subRoleEnum.value);
    }
}
