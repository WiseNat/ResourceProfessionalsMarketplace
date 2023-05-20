package com.wise.resource.professionals.marketplace.service;

import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.ApprovalTO;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.AccountUtil;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ReflectionUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.Set;

@Service
public class CreateAnAccountService {

    @Autowired
    private Validator validator;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private ReflectionUtil reflectionUtil;

    @Autowired
    private AccountUtil accountUtil;

    public String[] createAccount(CreateAccountTO createAccountTO) {
        if (createAccountTO.getAccountType() == AccountTypeEnum.ADMIN) {
            return new String[]{"accountType"};
        }

        Set<ConstraintViolation<CreateAccountTO>> violations = validator.validate(createAccountTO);

        if (violations.size() > 0) {
            return validatorUtil.getFieldsFromConstraintViolations(violations);
        }

        AccountEntity existingAccountEntity = accountRepository.findByEmailAndAccountType(
                createAccountTO.getEmail(), enumUtil.accountTypeToEntity(createAccountTO.getAccountType()));

        if (existingAccountEntity != null) {
            System.out.println("Account already exists. If you've already submitted a create account request then please wait.");
            return reflectionUtil.getFields(createAccountTO);
        }

        persistAccountAndApproval(createAccountTO);

        return new String[]{};
    }

    public void persistAccountAndApproval(CreateAccountTO createAccountTO) {
        persistAccount(createAccountTO);

        ApprovalTO approvalTO = new ApprovalTO();
        approvalTO.setAccount(createAccountTO);
        approvalTO.setDate(new Date(System.currentTimeMillis()));

        persistApproval(approvalTO);
    }

    public void persistAccount(CreateAccountTO createAccountTO) {
        createAccountTO.setIsApproved(false);
        createAccountTO.setEncodedPassword(accountUtil.hashPassword(createAccountTO.getPassword()));

        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(createAccountTO, accountEntity, "resource, accountType");

        accountEntity.setResource(null);
        accountEntity.setAccountType(enumUtil.accountTypeToEntity(createAccountTO.getAccountType()));

        accountRepository.save(accountEntity);
    }

    public void persistApproval(ApprovalTO approvalTO) {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        BeanUtils.copyProperties(approvalTO, approvalEntity, "account");

        String email = approvalTO.getAccount().getEmail();
        AccountTypeEntity accountTypeEntity = enumUtil.accountTypeToEntity(approvalTO.getAccount().getAccountType());
        approvalEntity.setAccount(accountRepository.findByEmailAndAccountType(email, accountTypeEntity));

        approvalRepository.save(approvalEntity);
    }
}
