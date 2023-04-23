package com.wise.ResourceProfessionalsMarketplace.util;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulatorUtil {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Populate database with constants
        // Need TO objects..

        // AccountType
        // ApprovalType
        // Banding
        // MainRole
        // SubRole
    }
}
