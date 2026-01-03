package in.noteslink.models.entity;

import in.noteslink.models.enums.BookCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "drive_link", nullable = false, length = 10000)
    private String driveLink;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageURL;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_category", nullable = false)
    private BookCategory bookCategory;

    @Column(name = "display_order", nullable = false)
    private Long displayOrder;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

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
                    "https://res.cloudinary.com/dfdusmc9k/image/upload/v1767414131/Book_Cover_Image_c97hus.png";
        }
    }
}


/*--------------------DATABASE STRUCTURE----------------------------------
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    author_name VARCHAR(255),
    image_url VARCHAR(500) NOT NULL,
    description TEXT NOT NULL,
    book_category VARCHAR(100) NOT NULL,
    display_order BIGINT NOT NULL,
    drive_link VARCHAR(10000) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
*
* */
