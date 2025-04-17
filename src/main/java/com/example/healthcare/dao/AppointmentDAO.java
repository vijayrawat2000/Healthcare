package com.example.healthcare.dao;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentDAO extends JpaRepository<Appointment, Integer> {
    
    List<Appointment> findByPatient_PatientId(int patientId);
    
    List<Appointment> findByScheduledDateTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT a FROM Appointment a JOIN a.appointmentProviders ap WHERE ap.healthcareProvider.providerId = :providerId")
    List<Appointment> findByProviderId(@Param("providerId") int providerId);
    
    List<Appointment> findByAppointmentStatus(AppointmentStatus status);
} 