package com.openclassrooms.chatop.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Wilhelm Zwertvaegher
 * Audit config, only used to enable Jpa auditing
 * Jpa Auditing is used to track creation and update dates for User, Rental and Message
 * via @CreatedDate and @LastModifiedDate annotations
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {
}