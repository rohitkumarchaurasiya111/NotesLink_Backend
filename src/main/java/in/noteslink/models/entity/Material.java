package in.noteslink.models.entity;

import in.noteslink.models.enums.MaterialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    // This control the order on which Materials will be display in Frontend
    @Column(name = "display_order", nullable = false)
    private Long displayOrder;

}

//-----------------DATABASE STRUCTURE------------
/*
        * CREATE TABLE materials (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            subject_id BIGINT NOT NULL,
            title VARCHAR(255) NOT NULL,
            `type` VARCHAR(50) NOT NULL,

            -- Controls order inside NOTES / PYQ / etc.
            display_order BIGINT NOT NULL,

            drive_link VARCHAR(10000) NOT NULL,
            is_premium BOOLEAN DEFAULT TRUE,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

            FOREIGN KEY (subject_id) REFERENCES subjects(id),
);
*/