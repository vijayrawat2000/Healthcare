package com.example.healthcare.service.impl;

import com.example.healthcare.dao.HealthcareProviderDAO;
import com.example.healthcare.dto.HealthcareProviderDTO;
import com.example.healthcare.entity.HealthcareProvider;
import com.example.healthcare.service.HealthcareProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HealthcareProviderServiceImpl implements HealthcareProviderService {

    @Autowired
    private HealthcareProviderDAO healthcareProviderDAO;

    @Override
    public HealthcareProviderDTO createProvider(HealthcareProviderDTO providerDTO) {
        HealthcareProvider provider = convertToEntity(providerDTO);
        provider = healthcareProviderDAO.save(provider);
        return convertToDTO(provider);
    }

    @Override
    public HealthcareProviderDTO getProviderById(int providerId) {
        return healthcareProviderDAO.findById(providerId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<HealthcareProviderDTO> getAllProviders() {
        return healthcareProviderDAO.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HealthcareProviderDTO updateProvider(int providerId, HealthcareProviderDTO providerDTO) {
        return healthcareProviderDAO.findById(providerId)
                .map(existingProvider -> {
                    updateEntityFromDTO(existingProvider, providerDTO);
                    return convertToDTO(healthcareProviderDAO.save(existingProvider));
                })
                .orElse(null);
    }

    @Override
    public void deleteProvider(int providerId) {
        healthcareProviderDAO.deleteById(providerId);
    }

    @Override
    public List<HealthcareProviderDTO> getProvidersBySpecialization(String specialization) {
        return healthcareProviderDAO.findBySpecialization(specialization).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HealthcareProviderDTO> getProvidersByDepartment(String department) {
        return healthcareProviderDAO.findByDepartment(department).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HealthcareProviderDTO> getActiveProviders() {
        // Since we don't have an active field, return all providers
        return getAllProviders();
    }

    @Override
    public List<HealthcareProviderDTO> searchProvidersByName(String name) {
        return healthcareProviderDAO.findAll().stream()
                .filter(provider -> provider.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                        provider.getLastName().toLowerCase().contains(name.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HealthcareProviderDTO> getProvidersByDepartmentAndSpecialization(String department, String specialization) {
        return healthcareProviderDAO.findByDepartmentAndSpecialization(department, specialization).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HealthcareProviderDTO updateProviderStatus(int providerId, boolean isActive) {
        // Since we don't have an active field, just return the provider
        return getProviderById(providerId);
    }

    private HealthcareProvider convertToEntity(HealthcareProviderDTO dto) {
        HealthcareProvider provider = new HealthcareProvider();
        updateEntityFromDTO(provider, dto);
        return provider;
    }

    private void updateEntityFromDTO(HealthcareProvider provider, HealthcareProviderDTO dto) {
        provider.setFirstName(dto.getFirstName());
        provider.setLastName(dto.getLastName());
        provider.setEmailAddress(dto.getEmail());
        provider.setPhoneNumber(dto.getPhoneNumber());
        provider.setSpecialization(dto.getSpecialization());
        provider.setLicenseNumber(dto.getLicenseNumber());
        provider.setDepartment(dto.getDepartment());
    }

    private HealthcareProviderDTO convertToDTO(HealthcareProvider provider) {
        HealthcareProviderDTO dto = new HealthcareProviderDTO();
        dto.setProviderId(provider.getProviderId());
        dto.setFirstName(provider.getFirstName());
        dto.setLastName(provider.getLastName());
        dto.setEmail(provider.getEmailAddress());
        dto.setPhoneNumber(provider.getPhoneNumber());
        dto.setSpecialization(provider.getSpecialization());
        dto.setLicenseNumber(provider.getLicenseNumber());
        dto.setDepartment(provider.getDepartment());
        dto.setActive(true); // Since we don't have an active field, always set to true
        dto.setCreatedAt(provider.getCreatedAt().atStartOfDay());
        dto.setUpdatedAt(provider.getUpdatedAt().atStartOfDay());
        return dto;
    }
} 