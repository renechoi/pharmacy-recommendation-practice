package com.example.project.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.pharmacy.entity.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
