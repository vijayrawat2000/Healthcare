package com.example.healthcare.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HealthcareProviderDTO {
    private int providerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String specialization;
    private String licenseNumber;
    private String department;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 