package com.example.healthcare.dto;

import com.example.healthcare.enums.MedicalRecordType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MedicalRecordDTO {
    private int recordId;
    private int patientId;
    private int appointmentId;
    private MedicalRecordType recordType;
    private String diagnosis;
    private String treatment;
    private String notes;
    private String testResults;
    private String prescriptions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 