package com.example.wherebnb.repository;

import com.example.wherebnb.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByUserIdAndRoomsId(Long user_id, Long rooms_id);
    Likes findByUserIdAndRoomsId(Long user_id, Long rooms_id);
    List<Likes> findAllByUserId(Long user_id);
}
