package com.example.wherebnb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ImageFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageurl; // 이미지url

    @Column(nullable = false)
    private String originfilename;  // 파일 원래 이름

    @Column(nullable = false)
    private String filename;  // db에 저장될 파일이름

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Rooms room;

    public ImageFile(String imageurl, String originfilename, String filename, Rooms room){
        this.imageurl = imageurl;
        this.originfilename = originfilename;
        this.filename = filename;
        this.room = room;
    }
}
