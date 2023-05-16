package com.example.wherebnb.repository;

import com.example.wherebnb.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<ImageFile, Long> {
    void deleteByRoomId(Long roomId);
}
