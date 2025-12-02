package in.noteslink.models.entity;

import in.noteslink.models.enums.MaterialType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MaterialType type;

    @Column(name = "drive_link", nullable = false)
    private String driveLink;

    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium = Boolean.TRUE;

}

//-----------------DATABASE STRUCTURE------------
/*
        * CREATE TABLE materials (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            subject_id BIGINT NOT NULL,
            title VARCHAR(255) NOT NULL,
            `type` VARCHAR(50) NOT NULL,
            drive_link VARCHAR(10000) NOT NULL,
            is_premium BOOLEAN DEFAULT TRUE,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (subject_id) REFERENCES subjects(id)
        );
*/