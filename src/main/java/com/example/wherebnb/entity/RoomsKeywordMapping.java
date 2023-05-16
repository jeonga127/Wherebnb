package com.example.wherebnb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RoomsKeywordMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @ManyToOne
    @JoinColumn(name = "rooms_id", nullable = false)
    private Rooms rooms;

    public RoomsKeywordMapping(Keyword keyword, Rooms rooms){
        this.keyword = keyword;
        this.rooms = rooms;
    }
}
