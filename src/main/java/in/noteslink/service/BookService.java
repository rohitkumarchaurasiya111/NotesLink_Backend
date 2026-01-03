package in.noteslink.service;

import in.noteslink.models.dto.BookDTO;
import in.noteslink.models.enums.BookCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    // User-side
    List<BookDTO> getAllActiveBooks();
    List<BookDTO> getActiveBooksByCategory(BookCategory category);
    BookDTO getActiveBookBySlug(String slug);

    // Admin-side
    public BookDTO addBook(BookDTO bookDTO, MultipartFile file);
    public BookDTO updateBook(BookDTO bookDTO);
}
