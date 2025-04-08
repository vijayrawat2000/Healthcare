package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MEDICAL_RECORDS")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEDICAL_RECORD_ID")
    private int medicalRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPOINTMENT_ID", nullable = false)
    private Appointment appointment;

    @Column(name = "VISIT_NUMBER", nullable = false)
    private Integer visitNumber;

    @Column(name = "RECORD_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MedicalRecordType recordType;

    @Column(name = "DIAGNOSIS", nullable = false)
    private String diagnosis;

    @Column(name = "SYMPTOMS", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "TREATMENT", columnDefinition = "TEXT")
    private String treatment;

    @Column(name = "PRESCRIPTIONS", columnDefinition = "TEXT")
    private String prescriptions;

    @Column(name = "TEST_RESULTS", columnDefinition = "TEXT")
    private String testResults;

    @Column(name = "NOTES", columnDefinition = "TEXT")
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