package com.example.healthcare.controller;

import com.example.healthcare.dto.BillingDTO;
import com.example.healthcare.entity.BillingStatus;
import com.example.healthcare.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public ResponseEntity<BillingDTO> createBilling(@RequestBody BillingDTO billingDTO) {
        BillingDTO createdBilling = billingService.createBilling(billingDTO);
        return new ResponseEntity<>(createdBilling, HttpStatus.CREATED);
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<BillingDTO> getBillingById(@PathVariable int billingId) {
        BillingDTO billingDTO = billingService.getBillingById(billingId);
        return ResponseEntity.ok(billingDTO);
    }

    @GetMapping
    public ResponseEntity<List<BillingDTO>> getAllBillings() {
        List<BillingDTO> billings = billingService.getAllBillings();
        return ResponseEntity.ok(billings);
    }

    @PutMapping("/{billingId}")
    public ResponseEntity<BillingDTO> updateBilling(
            @PathVariable int billingId,
            @RequestBody BillingDTO billingDTO) {
        BillingDTO updatedBilling = billingService.updateBilling(billingId, billingDTO);
        return ResponseEntity.ok(updatedBilling);
    }

    @DeleteMapping("/{billingId}")
    public ResponseEntity<Void> deleteBilling(@PathVariable int billingId) {
        billingService.deleteBilling(billingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BillingDTO>> getBillingsByPatientId(@PathVariable int patientId) {
        List<BillingDTO> billings = billingService.getBillingsByPatientId(patientId);
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<BillingDTO>> getBillingsByAppointmentId(@PathVariable int appointmentId) {
        List<BillingDTO> billings = billingService.getBillingsByAppointmentId(appointmentId);
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillingDTO>> getBillingsByStatus(@PathVariable BillingStatus status) {
        List<BillingDTO> billings = billingService.getBillingsByStatus(status);
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BillingDTO>> getOverdueBillings(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime currentDate) {
        List<BillingDTO> billings = billingService.getOverdueBillings(currentDate);
        return ResponseEntity.ok(billings);
    }

    @GetMapping("/outstanding")
    public ResponseEntity<List<BillingDTO>> getBillingsWithOutstandingBalance(
            @RequestParam BigDecimal minBalance) {
        List<BillingDTO> billings = billingService.getBillingsWithOutstandingBalance(minBalance);
        return ResponseEntity.ok(billings);
    }

    @PostMapping("/{billingId}/payment")
    public ResponseEntity<BillingDTO> processPayment(
            @PathVariable int billingId,
            @RequestParam BigDecimal paymentAmount) {
        BillingDTO updatedBilling = billingService.processPayment(billingId, paymentAmount);
        return ResponseEntity.ok(updatedBilling);
    }

    @PutMapping("/{billingId}/status")
    public ResponseEntity<BillingDTO> updateBillingStatus(
            @PathVariable int billingId,
            @RequestParam BillingStatus status) {
        BillingDTO updatedBilling = billingService.updateBillingStatus(billingId, status);
        return ResponseEntity.ok(updatedBilling);
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    public ResponseEntity<List<BillingDTO>> getBillingsByPatientIdAndStatus(
            @PathVariable int patientId,
            @PathVariable BillingStatus status) {
        List<BillingDTO> billings = billingService.getBillingsByPatientIdAndStatus(patientId, status);
        return ResponseEntity.ok(billings);
    }
} 