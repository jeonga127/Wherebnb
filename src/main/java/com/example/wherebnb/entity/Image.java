package com.example.wherebnb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;  // This line sets the imageUrl field to the value passed to the constructor
    }

    public void associateWithRoom(Rooms room) {
        this.room = room;
        room.getImages().add(this);
    }
}