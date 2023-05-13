package com.example.wherebnb.repository;

import com.example.wherebnb.entity.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
}
