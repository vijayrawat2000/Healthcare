package com.example.healthcare.dao;

import com.example.healthcare.entity.Billing;
import com.example.healthcare.entity.BillingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillingDAO extends JpaRepository<Billing, Integer> {
    
    List<Billing> findByPatient_PatientId(int patientId);
    
    List<Billing> findByAppointment_AppointmentId(int appointmentId);
    
    List<Billing> findByStatus(BillingStatus status);
    
    List<Billing> findByDueDateBeforeAndStatus(LocalDateTime date, BillingStatus status);
    
    @Query("SELECT b FROM Billing b WHERE b.balance > :minBalance")
    List<Billing> findWithOutstandingBalance(@Param("minBalance") BigDecimal minBalance);
    
    List<Billing> findByPatient_PatientIdAndStatus(int patientId, BillingStatus status);
} 