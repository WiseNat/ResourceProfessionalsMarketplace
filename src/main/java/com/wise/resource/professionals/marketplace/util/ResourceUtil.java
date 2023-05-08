package com.wise.resource.professionals.marketplace.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ResourceUtil {

    public BigDecimal costPerHourToDailyLateFee(BigDecimal costPerHour) {
        // dailyLateFee = (costPerHour * 8) * 5
        return costPerHour.multiply(new BigDecimal("40"));
    }

}
