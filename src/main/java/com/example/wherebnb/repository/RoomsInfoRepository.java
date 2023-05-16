package com.example.wherebnb.repository;

import com.example.wherebnb.entity.RoomsInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomsInfoRepository extends JpaRepository<RoomsInfo, Long> {
    Optional<RoomsInfo> findByRoomId(Long id);

    List<RoomsInfo> findByGuestNumGreaterThanEqualAndInfantExistAndPetExist(
            int guestNum,
            boolean infant,
            boolean pet,
            Pageable pageable);

    List<RoomsInfo> findAllByPeriodGreaterThanEqual(
            int period,
            Pageable pageable);
}
