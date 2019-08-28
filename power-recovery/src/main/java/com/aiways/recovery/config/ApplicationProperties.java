package com.aiways.recovery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Energy Assets.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String aiwaysTenantId;

    public String getAiwaysTenantId() {
        return aiwaysTenantId;
    }

    public void setAiwaysTenantId(String aiwaysTenantId) {
        this.aiwaysTenantId = aiwaysTenantId;
    }
}
