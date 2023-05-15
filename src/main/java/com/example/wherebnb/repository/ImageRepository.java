package com.example.wherebnb.repository;

import com.example.wherebnb.entity.Image;
import com.example.wherebnb.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
