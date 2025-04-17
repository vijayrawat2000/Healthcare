package com.example.healthcare.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientDTO {
    private int patientId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String insuranceProvider;
    private String insuranceNumber;
    private LocalDate createdAt;
    private LocalDate updatedAt;
} 