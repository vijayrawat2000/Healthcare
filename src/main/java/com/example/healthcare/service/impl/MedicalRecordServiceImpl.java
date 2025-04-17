package com.example.healthcare.service.impl;

import com.example.healthcare.dao.AppointmentDAO;
import com.example.healthcare.dao.MedicalRecordDAO;
import com.example.healthcare.dao.PatientDAO;
import com.example.healthcare.dto.MedicalRecordDTO;
import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.MedicalRecord;
import com.example.healthcare.entity.Patient;
import com.example.healthcare.enums.MedicalRecordType;
import com.example.healthcare.service.MedicalRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordDAO medicalRecordDAO;

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Override
    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = new MedicalRecord();
        BeanUtils.copyProperties(medicalRecordDTO, medicalRecord, "recordId", "patientId", "appointmentId");

        // Set patient
        Patient patient = patientDAO.findById(medicalRecordDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + medicalRecordDTO.getPatientId()));
        medicalRecord.setPatient(patient);

        // Set appointment if provided
        if (medicalRecordDTO.getAppointmentId() > 0) {
            Appointment appointment = appointmentDAO.findById(medicalRecordDTO.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + medicalRecordDTO.getAppointmentId()));
            medicalRecord.setAppointment(appointment);
        }

        MedicalRecord savedRecord = medicalRecordDAO.save(medicalRecord);
        return convertToDTO(savedRecord);
    }

    @Override
    public MedicalRecordDTO getMedicalRecordById(int recordId) {
        MedicalRecord medicalRecord = medicalRecordDAO.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + recordId));
        return convertToDTO(medicalRecord);
    }

    @Override
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordDAO.findAll();
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalRecordDTO updateMedicalRecord(int recordId, MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord existingRecord = medicalRecordDAO.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + recordId));

        BeanUtils.copyProperties(medicalRecordDTO, existingRecord, "recordId", "patientId", "appointmentId");

        // Update patient if changed
        if (medicalRecordDTO.getPatientId() != existingRecord.getPatient().getPatientId()) {
            Patient patient = patientDAO.findById(medicalRecordDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id: " + medicalRecordDTO.getPatientId()));
            existingRecord.setPatient(patient);
        }

        // Update appointment if changed
        if (medicalRecordDTO.getAppointmentId() > 0 && 
            (existingRecord.getAppointment() == null || 
             medicalRecordDTO.getAppointmentId() != existingRecord.getAppointment().getAppointmentId())) {
            Appointment appointment = appointmentDAO.findById(medicalRecordDTO.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + medicalRecordDTO.getAppointmentId()));
            existingRecord.setAppointment(appointment);
        }

        MedicalRecord updatedRecord = medicalRecordDAO.save(existingRecord);
        return convertToDTO(updatedRecord);
    }

    @Override
    public void deleteMedicalRecord(int recordId) {
        if (!medicalRecordDAO.existsById(recordId)) {
            throw new RuntimeException("Medical record not found with id: " + recordId);
        }
        medicalRecordDAO.deleteById(recordId);
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(int patientId) {
        List<MedicalRecord> records = medicalRecordDAO.findByPatient_PatientId(patientId);
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByAppointmentId(int appointmentId) {
        List<MedicalRecord> records = medicalRecordDAO.findByAppointment_AppointmentId(appointmentId);
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByType(MedicalRecordType recordType) {
        List<MedicalRecord> records = medicalRecordDAO.findByRecordType(recordType);
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByPatientIdAndType(int patientId, MedicalRecordType recordType) {
        List<MedicalRecord> records = medicalRecordDAO.findByPatient_PatientIdAndRecordType(patientId, recordType);
        return records.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MedicalRecordDTO convertToDTO(MedicalRecord medicalRecord) {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        BeanUtils.copyProperties(medicalRecord, dto);
        dto.setPatientId(medicalRecord.getPatient().getPatientId());
        if (medicalRecord.getAppointment() != null) {
            dto.setAppointmentId(medicalRecord.getAppointment().getAppointmentId());
        }
        return dto;
    }
} 