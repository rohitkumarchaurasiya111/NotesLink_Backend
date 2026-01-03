package in.noteslink.models.entity;

import jakarta.persistence.*;
import lombok.Data;

//This Entity Represents drive_folders Table made in DB to store the Id of the Folders in which we are going to store our Materials
@Entity
@Data
@Table(
        name = "drive_folders",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_college_name",
                        columnNames = {"college_id", "name"}
                )
        }
)
public class DriveFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK → colleges.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "drive_folder_id", nullable = false, length = 1000)
    private String driveFolderId;

    // kept for future hierarchy use
    @Column(name = "parent_drive_folder_id", length = 1000)
    private String parentDriveFolderId;
}


/*
*-----------------DATABASE STRUCTURE-----------------------------
* CREATE TABLE drive_folders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    college_id BIGINT NOT NULL,

    name VARCHAR(100) NOT NULL,

    drive_folder_id VARCHAR(1000) NOT NULL,

    -- kept for future hierarchy support
    parent_drive_folder_id VARCHAR(1000) NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_drive_college
        FOREIGN KEY (college_id)
        REFERENCES colleges(id)
        ON DELETE RESTRICT,

    -- for now, uniqueness is only by college + folder name
    UNIQUE KEY uq_college_name (college_id, name)
);

* */