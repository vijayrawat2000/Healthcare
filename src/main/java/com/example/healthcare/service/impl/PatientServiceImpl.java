package com.example.healthcare.service.impl;

import com.example.healthcare.dao.PatientDAO;
import com.example.healthcare.dto.PatientDTO;
import com.example.healthcare.entity.Patient;
import com.example.healthcare.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDAO patientDAO;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        Patient savedPatient = patientDAO.save(patient);
        PatientDTO savedPatientDTO = new PatientDTO();
        BeanUtils.copyProperties(savedPatient, savedPatientDTO);
        return savedPatientDTO;
    }

    @Override
    public PatientDTO getPatientById(int patientId) {
        Patient patient = patientDAO.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        PatientDTO patientDTO = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDTO);
        return patientDTO;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientDAO.findAll();
        return patients.stream()
                .map(patient -> {
                    PatientDTO patientDTO = new PatientDTO();
                    BeanUtils.copyProperties(patient, patientDTO);
                    return patientDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO updatePatient(int patientId, PatientDTO patientDTO) {
        Patient existingPatient = patientDAO.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        
        BeanUtils.copyProperties(patientDTO, existingPatient, "patientId");
        Patient updatedPatient = patientDAO.save(existingPatient);
        
        PatientDTO updatedPatientDTO = new PatientDTO();
        BeanUtils.copyProperties(updatedPatient, updatedPatientDTO);
        return updatedPatientDTO;
    }

    @Override
    public void deletePatient(int patientId) {
        if (!patientDAO.existsById(patientId)) {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }
        patientDAO.deleteById(patientId);
    }

    @Override
    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientDAO.findByEmail(email);
        if (patient == null) {
            throw new RuntimeException("Patient not found with email: " + email);
        }
        PatientDTO patientDTO = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDTO);
        return patientDTO;
    }
} 