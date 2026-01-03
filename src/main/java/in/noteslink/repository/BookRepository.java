package in.noteslink.repository;

import in.noteslink.models.entity.Book;
import in.noteslink.models.enums.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * User-side: fetch all active books ordered for display
     */
    List<Book> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * User-side: fetch active books by category
     */
    List<Book> findByIsActiveTrueAndBookCategoryOrderByDisplayOrderAsc(
            BookCategory bookCategory
    );

    /**
     * User-side: fetch single book by slug
     */
    Optional<Book> findBySlugAndIsActiveTrue(String slug);

    /**
     * Admin-side: fetch all books (active + inactive)
     */
    List<Book> findAllByOrderByDisplayOrderAsc();

    /**
     * Admin validation: check slug uniqueness
     */
    boolean existsBySlug(String slug);
}
