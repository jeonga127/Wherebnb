package com.example.wherebnb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "rooms_id", nullable = false)
    private RoomsInfo roomsInfo;

    public Likes(RoomsInfo roomsInfo, Users user){
        this.roomsInfo = roomsInfo;
        this.user = user;
    }
}
