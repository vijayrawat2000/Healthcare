package com.example.healthcare.service;

import com.example.healthcare.dto.PatientDTO;
import java.util.List;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO);
    PatientDTO getPatientById(int patientId);
    List<PatientDTO> getAllPatients();
    PatientDTO updatePatient(int patientId, PatientDTO patientDTO);
    void deletePatient(int patientId);
    PatientDTO getPatientByEmail(String email);
} 