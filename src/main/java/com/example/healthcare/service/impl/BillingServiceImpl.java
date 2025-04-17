package com.example.healthcare.service.impl;

import com.example.healthcare.dao.AppointmentDAO;
import com.example.healthcare.dao.BillingDAO;
import com.example.healthcare.dao.PatientDAO;
import com.example.healthcare.dto.BillingDTO;
import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.Billing;
import com.example.healthcare.entity.Patient;
import com.example.healthcare.entity.BillingStatus;
import com.example.healthcare.service.BillingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BillingDAO billingDAO;

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Override
    public BillingDTO createBilling(BillingDTO billingDTO) {
        Billing billing = new Billing();
        BeanUtils.copyProperties(billingDTO, billing, "billingId", "patientId", "appointmentId");

        // Set patient
        Patient patient = patientDAO.findById(billingDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + billingDTO.getPatientId()));
        billing.setPatient(patient);

        // Set appointment if provided
        if (billingDTO.getAppointmentId() > 0) {
            Appointment appointment = appointmentDAO.findById(billingDTO.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + billingDTO.getAppointmentId()));
            billing.setAppointment(appointment);
        }

        // Calculate initial balance
        billing.setBalance(billing.getAmount().subtract(billing.getPaidAmount()));

        Billing savedBilling = billingDAO.save(billing);
        return convertToDTO(savedBilling);
    }

    @Override
    public BillingDTO getBillingById(int billingId) {
        Billing billing = billingDAO.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + billingId));
        return convertToDTO(billing);
    }

    @Override
    public List<BillingDTO> getAllBillings() {
        List<Billing> billings = billingDAO.findAll();
        return billings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BillingDTO updateBilling(int billingId, BillingDTO billingDTO) {
        Billing existingBilling = billingDAO.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + billingId));

        BeanUtils.copyProperties(billingDTO, existingBilling, "billingId", "patientId", "appointmentId");

        // Update patient if changed
        if (billingDTO.getPatientId() != existingBilling.getPatient().getPatientId()) {
            Patient patient = patientDAO.findById(billingDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id: " + billingDTO.getPatientId()));
            existingBilling.setPatient(patient);
        }

        // Update appointment if changed
        if (billingDTO.getAppointmentId() > 0 && 
            (existingBilling.getAppointment() == null || 
             billingDTO.getAppointmentId() != existingBilling.getAppointment().getAppointmentId())) {
            Appointment appointment = appointmentDAO.findById(billingDTO.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + billingDTO.getAppointmentId()));
            existingBilling.setAppointment(appointment);
        }

        // Recalculate balance
        existingBilling.setBalance(existingBilling.getAmount().subtract(existingBilling.getPaidAmount()));

        Billing updatedBilling = billingDAO.save(existingBilling);
        return convertToDTO(updatedBilling);
    }

    @Override
    public void deleteBilling(int billingId) {
        if (!billingDAO.existsById(billingId)) {
            throw new RuntimeException("Billing not found with id: " + billingId);
        }
        billingDAO.deleteById(billingId);
    }

    @Override
    public List<BillingDTO> getBillingsByPatientId(int patientId) {
        List<Billing> billings = billingDAO.findByPatient_PatientId(patientId);
        return billings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingDTO> getBillingsByAppointmentId(int appointmentId) {
        List<Billing> billings = billingDAO.findByAppointment_AppointmentId(appointmentId);
        return billings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingDTO> getBillingsByStatus(BillingStatus status) {
        List<Billing> billings = billingDAO.findByStatus(status);
        return billings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingDTO> getOverdueBillings(LocalDateTime currentDate) {
        List<Billing> billings = billingDAO.findByDueDateBeforeAndStatus(currentDate, BillingStatus.PENDING);
        return billings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillingDTO> getBillingsWithOutstandingBalance(BigDecimal minBalance) {
        List<Billing> billings = billingDAO.findWithOutstandingBalance(minBalance);
        return billings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BillingDTO processPayment(int billingId, BigDecimal paymentAmount) {
        Billing billing = billingDAO.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + billingId));

        BigDecimal newPaidAmount = billing.getPaidAmount().add(paymentAmount);
        billing.setPaidAmount(newPaidAmount);
        
        // Update balance
        BigDecimal newBalance = billing.getAmount().subtract(newPaidAmount);
        billing.setBalance(newBalance);

        // Update status based on payment
        if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
            billing.setStatus(BillingStatus.PAID);
        } else if (newPaidAmount.compareTo(BigDecimal.ZERO) > 0) {
            billing.setStatus(BillingStatus.PARTIALLY_PAID);
        }

        Billing updatedBilling = billingDAO.save(billing);
        return convertToDTO(updatedBilling);
    }

    @Override
    public BillingDTO updateBillingStatus(int billingId, BillingStatus status) {
        Billing billing = billingDAO.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + billingId));
        billing.setStatus(status);
        Billing updatedBilling = billingDAO.save(billing);
        return convertToDTO(updatedBilling);
    }

    @Override
    public List<BillingDTO> getBillingsByPatientIdAndStatus(int patientId, BillingStatus status) {
        List<Billing> billings = billingDAO.findByPatient_PatientIdAndStatus(patientId, status);
        return billings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BillingDTO convertToDTO(Billing billing) {
        BillingDTO dto = new BillingDTO();
        BeanUtils.copyProperties(billing, dto);
        dto.setPatientId(billing.getPatient().getPatientId());
        if (billing.getAppointment() != null) {
            dto.setAppointmentId(billing.getAppointment().getAppointmentId());
        }
        return dto;
    }
} 