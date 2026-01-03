package in.noteslink.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "colleges")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String subdomain;

    @Column(name = "email_domain")          //We need to write column name explicitly becasue the variableName is different.
    private String emailDomain;

    @Column(name = "logo_url", length = 1000)
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
