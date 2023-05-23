package com.example.project.pharmacy.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.pharmacy.entity.Pharmacy;
import com.example.project.pharmacy.repository.PharmacyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {

	private final PharmacyRepository pharmacyRepository;

	@Transactional
	public void updateAddress(Long id, String address){
		Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

		if (Objects.isNull(pharmacy)){
			log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
			return;
		}

		pharmacy.changePharmacyAddress(address);
	}
}
