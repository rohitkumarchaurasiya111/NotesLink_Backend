package in.noteslink.models.entity;

import in.noteslink.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;  // Users must have a name


    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.FREE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;
}


//-------------------------Database Structure----------------------------
/*
        CREATE TABLE users (
          `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
          `college_id` BIGINT,
          `email` VARCHAR(255) NOT NULL UNIQUE,
          `name` VARCHAR(255) NOT NULL,
          `role` VARCHAR(50) NOT NULL DEFAULT 'FREE',
                `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_users_college FOREIGN KEY (`college_id`) REFERENCES colleges(`id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
        ) ENGINE=InnoDB DEFAULT CHARSET = utf8mb4;
*/
