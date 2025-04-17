package com.example.healthcare.controller;

import com.example.healthcare.dto.MedicalRecordDTO;
import com.example.healthcare.enums.MedicalRecordType;
import com.example.healthcare.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO createdRecord = medicalRecordService.createMedicalRecord(medicalRecordDTO);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(@PathVariable int recordId) {
        MedicalRecordDTO medicalRecordDTO = medicalRecordService.getMedicalRecordById(recordId);
        return ResponseEntity.ok(medicalRecordDTO);
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        List<MedicalRecordDTO> records = medicalRecordService.getAllMedicalRecords();
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
            @PathVariable int recordId,
            @RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO updatedRecord = medicalRecordService.updateMedicalRecord(recordId, medicalRecordDTO);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable int recordId) {
        medicalRecordService.deleteMedicalRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByPatientId(@PathVariable int patientId) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByPatientId(patientId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByAppointmentId(@PathVariable int appointmentId) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByAppointmentId(appointmentId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/type/{recordType}")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByType(@PathVariable MedicalRecordType recordType) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByType(recordType);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/patient/{patientId}/type/{recordType}")
    public ResponseEntity<List<MedicalRecordDTO>> getMedicalRecordsByPatientIdAndType(
            @PathVariable int patientId,
            @PathVariable MedicalRecordType recordType) {
        List<MedicalRecordDTO> records = medicalRecordService.getMedicalRecordsByPatientIdAndType(patientId, recordType);
        return ResponseEntity.ok(records);
    }
} 