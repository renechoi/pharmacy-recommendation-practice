package com.example.project.direction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.direction.entity.Direction;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
