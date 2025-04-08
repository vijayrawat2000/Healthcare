package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Represents the relationship between appointments and healthcare providers.
 * This entity manages the many-to-many relationship and stores additional information
 * about the provider's role in the appointment.
 */
@Data
@Entity
@Table(name = "APPOINTMENT_PROVIDERS")
public class AppointmentProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPOINTMENT_PROVIDER_ID")
    private int appointmentProviderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPOINTMENT_ID", nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDER_ID", nullable = false)
    private HealthcareProvider healthcareProvider;

    @Column(name = "PROVIDER_ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderRole providerRole;

    @Column(name = "IS_PRIMARY_PROVIDER")
    private boolean isPrimaryProvider;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

/**
 * Represents the role of a healthcare provider in an appointment.
 */
enum ProviderRole {
    PRIMARY_CARE_PHYSICIAN,
    SPECIALIST,
    NURSE,
    TECHNICIAN,
    THERAPIST,
    CONSULTANT
} 