package com.example.healthcare.dao;

import com.example.healthcare.entity.MedicalRecord;
import com.example.healthcare.enums.MedicalRecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordDAO extends JpaRepository<MedicalRecord, Integer> {
    List<MedicalRecord> findByPatient_PatientId(int patientId);
    List<MedicalRecord> findByAppointment_AppointmentId(int appointmentId);
    List<MedicalRecord> findByRecordType(MedicalRecordType recordType);
    List<MedicalRecord> findByPatient_PatientIdAndRecordType(int patientId, MedicalRecordType recordType);
} 