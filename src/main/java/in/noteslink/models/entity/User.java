package in.noteslink.models.entity;

import in.noteslink.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.FREE;

    @ManyToOne
    @JoinColumn(name = "college_id" , nullable = true)            //Foreign key
    private College college;

}


//-------------------------Database Structure----------------------------
/*
        CREATE TABLE users (
          `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
          `college_id` BIGINT,
          `email` VARCHAR(255) NOT NULL UNIQUE,
          `name` VARCHAR(255),
          `role` VARCHAR(50) NOT NULL DEFAULT 'FREE',
                `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_users_college FOREIGN KEY (`college_id`) REFERENCES colleges(`id`)
        ON DELETE SET NULL
        ON UPDATE CASCADE
        ) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;
*/
