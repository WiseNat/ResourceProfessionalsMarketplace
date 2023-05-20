package com.wise.resource.professionals.marketplace.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ResourceUtil {
    /**
     * Calculates the {@code Daily Late Fee} from the given {@code costPerHour} where
     * {@code dailyLateFee = (costPerHour * 8) * 5}
     *
     * @param costPerHour a BigDecimal for the cost per hour
     * @return the daily late fee
     */
    public BigDecimal calculateDailyLateFee(BigDecimal costPerHour) {
        return costPerHour.multiply(new BigDecimal("40"));
    }
}
