package com.example.healthcare.service;

import com.example.healthcare.dto.MedicalRecordDTO;
import com.example.healthcare.enums.MedicalRecordType;
import java.util.List;

public interface MedicalRecordService {
    MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO);
    MedicalRecordDTO getMedicalRecordById(int recordId);
    List<MedicalRecordDTO> getAllMedicalRecords();
    MedicalRecordDTO updateMedicalRecord(int recordId, MedicalRecordDTO medicalRecordDTO);
    void deleteMedicalRecord(int recordId);
    List<MedicalRecordDTO> getMedicalRecordsByPatientId(int patientId);
    List<MedicalRecordDTO> getMedicalRecordsByAppointmentId(int appointmentId);
    List<MedicalRecordDTO> getMedicalRecordsByType(MedicalRecordType recordType);
    List<MedicalRecordDTO> getMedicalRecordsByPatientIdAndType(int patientId, MedicalRecordType recordType);
} 