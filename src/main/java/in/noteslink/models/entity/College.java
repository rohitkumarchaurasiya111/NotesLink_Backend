package in.noteslink.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "colleges")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String name;


    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true, length = 50)
    private String subdomain;

    @NotBlank
    @Size(max = 100)
    @Column(name = "email_domain", nullable = false, length = 100)
    private String emailDomain;

    @Column(name = "logo_url", length = 500)
    private String logoURL;

    @Column(name = "created_at", updatable = false, insertable = false)     //Database automatically fills this Field
    private LocalDateTime createdAt;
}

// -------------DATABASE STRUCTURE--------------
/*
          CREATE TABLE colleges (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            subdomain VARCHAR(50) NOT NULL UNIQUE,
            email_domain VARCHAR(100) NOT NULL,
            logo_url VARCHAR(500),
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );
* */
