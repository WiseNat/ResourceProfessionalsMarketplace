package com.wise.resource.professionals.marketplace.validation.annotation;

import com.wise.resource.professionals.marketplace.validation.implementation.RolesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Roles validation annotation.
 *
 * @see RolesValidator
 */
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = RolesValidator.class)
public @interface Roles {

    String message() default "{ResourceTOAnnotation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}