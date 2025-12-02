package in.noteslink.models.entity;

import in.noteslink.models.enums.Branches;
import in.noteslink.models.enums.Years;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "college_id" , nullable = false)            //Foreign key
    private College college;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Years year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Branches branch = Branches.CSE;

}


//---------------------DATABASE STRUCTURE---------------------
/*
        * CREATE TABLE subjects (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            college_id BIGINT NOT NULL,
            `name` VARCHAR(255) NOT NULL,
            image_url VARCHAR(500),
            `description` TEXT,
            `year` VARCHAR(50) NOT NULL,
            `branch` VARCHAR(50) NOT NULL DEFAULT 'CSE',
            FOREIGN KEY (college_id) REFERENCES colleges(id)
        );
* */