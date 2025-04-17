package com.example.healthcare.dto;

import com.example.healthcare.entity.BillingStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillingDTO {
    private int billingId;
    private int patientId;
    private int appointmentId;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private BigDecimal balance;
    private BillingStatus status;
    private String insuranceProvider;
    private String insuranceNumber;
    private String billingDescription;
    private LocalDateTime billingDate;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 