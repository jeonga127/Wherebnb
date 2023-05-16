package com.example.wherebnb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Key;

@Entity
@Getter
@NoArgsConstructor
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private KeywordEnum keyword;

    public Keyword(String keyword){
        this.keyword = KeywordEnum.toEnum(keyword);
    }
}
