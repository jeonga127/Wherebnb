package com.example.wherebnb.repository;

import com.example.wherebnb.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    //     키워드 조건 검색
//    List<Rooms> findAllByKeyword1OrKeyword2(String keyword1, String keyword2);

   List<Rooms> findAllByKeyword1OrKeyword2(String keyword1, String keyword2);
}
