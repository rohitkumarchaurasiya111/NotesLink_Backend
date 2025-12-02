package in.noteslink.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "colleges")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name;

    @Column(unique = true)
    private String subdomain;

    private String emailDomain;
    private String logoURL;
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
