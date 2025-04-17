package com.example.healthcare.service;

import com.example.healthcare.dto.BillingDTO;
import com.example.healthcare.entity.BillingStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BillingService {
    BillingDTO createBilling(BillingDTO billingDTO);
    BillingDTO getBillingById(int billingId);
    List<BillingDTO> getAllBillings();
    BillingDTO updateBilling(int billingId, BillingDTO billingDTO);
    void deleteBilling(int billingId);
    List<BillingDTO> getBillingsByPatientId(int patientId);
    List<BillingDTO> getBillingsByAppointmentId(int appointmentId);
    List<BillingDTO> getBillingsByStatus(BillingStatus status);
    List<BillingDTO> getOverdueBillings(LocalDateTime currentDate);
    List<BillingDTO> getBillingsWithOutstandingBalance(BigDecimal minBalance);
    BillingDTO processPayment(int billingId, BigDecimal paymentAmount);
    BillingDTO updateBillingStatus(int billingId, BillingStatus status);
    List<BillingDTO> getBillingsByPatientIdAndStatus(int patientId, BillingStatus status);
} 