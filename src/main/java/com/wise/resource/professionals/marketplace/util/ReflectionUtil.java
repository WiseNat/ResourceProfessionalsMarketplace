package com.wise.resource.professionals.marketplace.util;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class ReflectionUtil {
    public String[] getFields(Object someClass) {
        return Arrays.stream(someClass.getClass().getFields())
                .map(Field::getName)
                .toArray(String[]::new);
    }

    @SneakyThrows
    public Field getField(Object someClass, String field) {
        return someClass.getClass().getField(field);
    }

    public String getStringField(Object someClass, String field) {
        return getField(someClass, field).toString();
    }
}
