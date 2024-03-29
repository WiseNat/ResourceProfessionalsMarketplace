package com.wise.resource.professionals.marketplace.validation.implementation;

import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.to.RolesContainer;
import com.wise.resource.professionals.marketplace.validation.annotation.Roles;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

/**
 * Validator logic for {@link Roles}.
 * <p>
 * This checks whether the combination of the {@link RolesContainer#mainRole} and {@link RolesContainer#subRole} values
 * is valid.
 */
public class RolesValidator implements ConstraintValidator<Roles, RolesContainer> {

    @Override
    public void initialize(Roles constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RolesContainer value, ConstraintValidatorContext context) {
        boolean isValid = true;

        MainRoleEnum mainRole = value.getMainRole();
        SubRoleEnum subRole = value.getSubRole();

        if (mainRole == null) {
            return true;
        }

        SubRoleEnum[] validSubRoles = ROLE_MAPPING.get(mainRole);

        if (validSubRoles.length > 0 && !Arrays.asList(validSubRoles).contains(subRole)) {
            isValid = false;
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("invalid role combination")
                    .addPropertyNode("mainRole")
                    .addConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("invalid role combination")
                    .addPropertyNode("subRole")
                    .addConstraintViolation();
        }

        return isValid;
    }
}