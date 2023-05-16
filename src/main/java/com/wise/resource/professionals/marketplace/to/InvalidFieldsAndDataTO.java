package com.wise.resource.professionals.marketplace.to;

import lombok.Data;

@Data
public class InvalidFieldsAndDataTO<T> {
    private T data;
    private String[] invalidFields;
}
