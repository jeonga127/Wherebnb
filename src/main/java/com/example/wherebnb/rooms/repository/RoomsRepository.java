package com.example.wherebnb.rooms.repository;

import com.example.wherebnb.host.entity.HostEntity;
import com.example.wherebnb.rooms.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
}
