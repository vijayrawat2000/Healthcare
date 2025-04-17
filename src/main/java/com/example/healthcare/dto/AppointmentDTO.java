package com.example.healthcare.dto;

import com.example.healthcare.entity.AppointmentStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AppointmentDTO {
    private int appointmentId;
    private int patientId;
    private Set<Integer> providerIds;
    private LocalDateTime scheduledDateTime;
    private String notes;
    private AppointmentStatus appointmentStatus;
    private String appointmentReason;
    private String appointmentNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 