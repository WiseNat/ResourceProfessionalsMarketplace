package com.wise.resource.professionals.marketplace.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Helper methods surrounding Java reflection
 */
@Component
public class ReflectionUtil {

    /**
     * Gets all the fields associated with a given class as a String array
     *
     * @param someClass the class to find the fields of
     * @return a {@link String} array of the found fields
     */
    public String[] getFields(Object someClass) {
        return Arrays.stream(someClass.getClass().getFields())
                .map(Field::getName)
                .toArray(String[]::new);
    }
}
