package com.example.healthcare.service;

import com.example.healthcare.dto.AppointmentDTO;
import com.example.healthcare.entity.AppointmentStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    AppointmentDTO getAppointmentById(int appointmentId);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO updateAppointment(int appointmentId, AppointmentDTO appointmentDTO);
    void deleteAppointment(int appointmentId);
    List<AppointmentDTO> getAppointmentsByPatientId(int patientId);
    List<AppointmentDTO> getAppointmentsByProviderId(int providerId);
    List<AppointmentDTO> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end);
    List<AppointmentDTO> getAppointmentsByStatus(AppointmentStatus status);
    AppointmentDTO updateAppointmentStatus(int appointmentId, AppointmentStatus status);
} 