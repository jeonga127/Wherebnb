package com.example.wherebnb.repository;

import com.example.wherebnb.entity.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
   Page<Rooms> findAll(Pageable pageable);
   List<Rooms> findAllByKeyword1OrKeyword2(String keyword1, String keyword2);

   List<Rooms> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqualAndGuestNumGreaterThanEqualAndInfantAndPet(
           LocalDate startDate,
           LocalDate endDate,
           int guestNum,
           boolean infant,
           boolean pet);

   List<Rooms>findAllByPeriodGreaterThanEqualAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndGuestNumGreaterThanEqualAndInfantAndPet(
           int period,
           LocalDate startDate,
           LocalDate endDate,
           int guestNum,
           boolean infant,
           boolean pet);

}
