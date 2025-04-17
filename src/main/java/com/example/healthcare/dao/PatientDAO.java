package com.example.healthcare.dao;

import com.example.healthcare.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDAO extends JpaRepository<Patient, Integer> {
    // Custom query methods can be added here
    Patient findByEmail(String email);
} 