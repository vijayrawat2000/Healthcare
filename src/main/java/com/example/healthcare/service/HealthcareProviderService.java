package com.example.healthcare.service;

import com.example.healthcare.dto.HealthcareProviderDTO;
import java.util.List;

public interface HealthcareProviderService {
    HealthcareProviderDTO createProvider(HealthcareProviderDTO providerDTO);
    HealthcareProviderDTO getProviderById(int providerId);
    List<HealthcareProviderDTO> getAllProviders();
    HealthcareProviderDTO updateProvider(int providerId, HealthcareProviderDTO providerDTO);
    void deleteProvider(int providerId);
    List<HealthcareProviderDTO> getProvidersBySpecialization(String specialization);
    List<HealthcareProviderDTO> getProvidersByDepartment(String department);
    List<HealthcareProviderDTO> getActiveProviders();
    List<HealthcareProviderDTO> searchProvidersByName(String name);
    List<HealthcareProviderDTO> getProvidersByDepartmentAndSpecialization(String department, String specialization);
    HealthcareProviderDTO updateProviderStatus(int providerId, boolean isActive);
} 