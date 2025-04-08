package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "HEALTHCARE_PROVIDERS")
public class HealthcareProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROVIDER_ID")
    private int providerId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL_ADDRESS", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "SPECIALIZATION", nullable = false)
    private String specialization;

    @Column(name = "LICENSE_NUMBER", nullable = false)
    private String licenseNumber;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "OFFICE_LOCATION")
    private String officeLocation;

    @OneToMany(mappedBy = "healthcareProvider", cascade = CascadeType.ALL)
    private Set<AppointmentProvider> appointmentProviders = new HashSet<>();

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}