package com.example.healthcare.service.impl;

import com.example.healthcare.dao.AppointmentDAO;
import com.example.healthcare.dao.PatientDAO;
import com.example.healthcare.dto.AppointmentDTO;
import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.AppointmentProvider;
import com.example.healthcare.entity.AppointmentStatus;
import com.example.healthcare.entity.Patient;
import com.example.healthcare.service.AppointmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Autowired
    private PatientDAO patientDAO;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentDTO, appointment, "appointmentId", "patientId", "providerIds");

        // Set patient
        Patient patient = patientDAO.findById(appointmentDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + appointmentDTO.getPatientId()));
        appointment.setPatient(patient);

        // Set appointment providers
        if (appointmentDTO.getProviderIds() != null) {
            for (Integer providerId : appointmentDTO.getProviderIds()) {
                AppointmentProvider appointmentProvider = new AppointmentProvider();
                appointmentProvider.setAppointment(appointment);
                // Note: You'll need to fetch the HealthcareProvider entity and set it here
                // This is a simplified version
                appointment.getAppointmentProviders().add(appointmentProvider);
            }
        }

        Appointment savedAppointment = appointmentDAO.save(appointment);
        return convertToDTO(savedAppointment);
    }

    @Override
    public AppointmentDTO getAppointmentById(int appointmentId) {
        Appointment appointment = appointmentDAO.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        return convertToDTO(appointment);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentDAO.findAll();
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO updateAppointment(int appointmentId, AppointmentDTO appointmentDTO) {
        Appointment existingAppointment = appointmentDAO.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));

        BeanUtils.copyProperties(appointmentDTO, existingAppointment, "appointmentId", "patientId", "providerIds");

        // Update patient if changed
        if (appointmentDTO.getPatientId() != existingAppointment.getPatient().getPatientId()) {
            Patient patient = patientDAO.findById(appointmentDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id: " + appointmentDTO.getPatientId()));
            existingAppointment.setPatient(patient);
        }

        Appointment updatedAppointment = appointmentDAO.save(existingAppointment);
        return convertToDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        if (!appointmentDAO.existsById(appointmentId)) {
            throw new RuntimeException("Appointment not found with id: " + appointmentId);
        }
        appointmentDAO.deleteById(appointmentId);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(int patientId) {
        List<Appointment> appointments = appointmentDAO.findByPatient_PatientId(patientId);
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByProviderId(int providerId) {
        List<Appointment> appointments = appointmentDAO.findByProviderId(providerId);
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        List<Appointment> appointments = appointmentDAO.findByScheduledDateTimeBetween(start, end);
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByStatus(AppointmentStatus status) {
        List<Appointment> appointments = appointmentDAO.findByAppointmentStatus(status);
        return appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO updateAppointmentStatus(int appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentDAO.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        appointment.setAppointmentStatus(status);
        Appointment updatedAppointment = appointmentDAO.save(appointment);
        return convertToDTO(updatedAppointment);
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        BeanUtils.copyProperties(appointment, dto);
        dto.setPatientId(appointment.getPatient().getPatientId());
        // Set provider IDs
        dto.setProviderIds(appointment.getAppointmentProviders().stream()
                .map(ap -> ap.getHealthcareProvider().getProviderId())
                .collect(Collectors.toSet()));
        return dto;
    }
} 