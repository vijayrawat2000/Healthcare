package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BILLINGS")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BILLING_ID")
    private int billingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPOINTMENT_ID", nullable = false)
    private Appointment appointment;

    @Column(name = "BILLING_AMOUNT", nullable = false)
    private BigDecimal billingAmount;

    @Column(name = "INSURANCE_COVERAGE_AMOUNT")
    private BigDecimal insuranceCoverageAmount;

    @Column(name = "PATIENT_RESPONSIBILITY_AMOUNT")
    private BigDecimal patientResponsibilityAmount;

    @Column(name = "BILLING_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Column(name = "BILLING_NOTES", columnDefinition = "TEXT")
    private String billingNotes;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingStatus status;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

    @Column(name = "PAID_AMOUNT", nullable = false)
    private BigDecimal paidAmount;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public BillingStatus getStatus() {
        return status;
    }

    public void setStatus(BillingStatus status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getAmount() {
        return billingAmount;
    }
}
