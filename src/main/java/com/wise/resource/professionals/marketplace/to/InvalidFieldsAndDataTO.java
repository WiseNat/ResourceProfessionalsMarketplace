package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

/**
 * A POJO for bundling together violating fields and data during validation
 *
 * @param <T> any kind of data to be returned alongside the violating fields
 */
@Data
public class InvalidFieldsAndDataTO<T> {
    private T data;
    private String[] invalidFields;
}
