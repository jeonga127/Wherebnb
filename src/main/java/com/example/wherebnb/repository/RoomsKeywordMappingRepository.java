package com.example.wherebnb.repository;

import com.example.wherebnb.entity.RoomsKeywordMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomsKeywordMappingRepository extends JpaRepository<RoomsKeywordMapping, Long> {
    Optional<List<RoomsKeywordMapping>> findByRoomsId(Long id);
}
