package in.noteslink.models.entity;

import in.noteslink.models.enums.Branches;
import in.noteslink.models.enums.Years;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder            //used to create objects in a clean, readable, and safe way without messy constructors or setters.
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //College ID cannot be Null, For Global/Platform Related Subjects, Colleg_id = 1;
    @ManyToOne
    @JoinColumn(name = "college_id" , nullable = false)            //Foreign key
    private College college;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageURL = "https://res.cloudinary.com/dfdusmc9k/image/upload/v1766859518/SubjectImageGirl_m6maff.png";
//    https://res.cloudinary.com/dfdusmc9k/image/upload/v1766859483/SubjectImage_jydtuy.png   - This link can also be used

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Years year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Branches branch = Branches.CSE;

    @Column(nullable = false)
    private Boolean isProject = Boolean.FALSE;
}


//---------------------DATABASE STRUCTURE---------------------
/*
    CREATE TABLE subjects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    college_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(500),
    description TEXT NOT NULL,
    year VARCHAR(50) NOT NULL,
    branch VARCHAR(50) NOT NULL DEFAULT 'CSE',
    is_project BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_subject_college
    FOREIGN KEY (college_id) REFERENCES colleges(id)
);
* */