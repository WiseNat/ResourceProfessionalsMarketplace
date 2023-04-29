package com.wise.ResourceProfessionalsMarketplace.util;

import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.BandingEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.MainRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.SubRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.BandingEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.MainRoleEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.SubRoleEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountTypeRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.BandingRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.MainRoleRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.SubRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnumUtil {

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    BandingRepository bandingRepository;

    @Autowired
    MainRoleRepository mainRoleRepository;

    @Autowired
    SubRoleRepository subRoleRepository;

    public AccountTypeEntity accountTypeToEntity(AccountTypeEnum accountTypeEnum) {
        return accountTypeRepository.findByName(accountTypeEnum.value);
    }

    public BandingEntity bandingToEntity(BandingEnum bandingEnum) {
        return bandingRepository.findByName(bandingEnum.value);
    }

    public MainRoleEntity mainRoleToEntity(MainRoleEnum mainRoleEnum) {
        return mainRoleRepository.findByName(mainRoleEnum.value);
    }

    public SubRoleEntity subRoleToEntity(SubRoleEnum subRoleEnum) {
        return subRoleRepository.findByName(subRoleEnum.value);
    }
}
