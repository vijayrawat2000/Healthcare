package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a medical appointment in the healthcare system.
 * Tracks appointment details, associated healthcare providers, and related records.
 */
@Data
@Entity
@Table(name = "APPOINTMENTS")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPOINTMENT_ID")
    private int appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Set<AppointmentProvider> appointmentProviders = new HashSet<>();

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Set<Billing> billingRecords = new HashSet<>();

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private Set<MedicalRecord> medicalRecords = new HashSet<>();

    @Column(name = "SCHEDULED_DATE_TIME", nullable = false)
    private LocalDateTime scheduledDateTime;

    @Column(name = "APPOINTMENT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @Column(name = "APPOINTMENT_REASON")
    private String appointmentReason;

    @Column(name = "APPOINTMENT_NOTES")
    private String appointmentNotes;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Sets creation and update timestamps when the entity is first persisted.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the modification timestamp whenever the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

/**
 * Represents the possible states of an appointment in the system.
 */
enum AppointmentStatus {
    PENDING,
    COMPLETED,
    CANCELED
}