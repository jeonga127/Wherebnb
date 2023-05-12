package com.example.wherebnb.host.repository;

import com.example.wherebnb.host.entity.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
}
