package in.noteslink.models.entity;

import in.noteslink.models.enums.ProjectDifficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageURL;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "tech_stacks_used", nullable = false, length = 1000)
    private String techStacksUsed;

    @Column(name = "deployed_link", length = 1000)
    private String deployedLink;

    @Column(name = "github_link", length = 1000)
    private String githubLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false)
    private ProjectDifficulty difficultyLevel;

    @Column(name = "display_order", nullable = false)
    private Long displayOrder;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    //@PrePersist is a JPA lifecycle callback.
    //It tells Hibernate:
    //“Before inserting this entity into the database, run this method.”
    //So this method runs exactly once, right before the INSERT SQL happens.
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
        if (this.imageURL == null) {
            this.imageURL =
                    "https://res.cloudinary.com/dfdusmc9k/image/upload/v1767415622/Project_Cover_Image_rzze7w.png";
        }
    }
}


/*--------------------DATABASE STRUCTURE-----------------------------------
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    image_url VARCHAR(500) NOT NULL,
    description TEXT NOT NULL,
    tech_stacks_used VARCHAR(500) NOT NULL,
    deployed_link VARCHAR(500),
    github_link VARCHAR(500),
    difficulty_level VARCHAR(100) NOT NULL,
    display_order BIGINT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
*
* */