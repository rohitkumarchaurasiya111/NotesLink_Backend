package in.noteslink.service.impl;

import in.noteslink.exception.BadRequestException;
import in.noteslink.mapper.BookMapper;
import in.noteslink.models.dto.BookDTO;
import in.noteslink.models.entity.Book;
import in.noteslink.models.enums.BookCategory;
import in.noteslink.models.enums.MaterialType;
import in.noteslink.repository.BookRepository;
import in.noteslink.service.BookService;
import in.noteslink.service.GoogleDriveService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@Transactional  //It ensures that all database operations inside the service method either complete successfully together or are rolled back together if something fails.
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GoogleDriveService googleDriveService;

    public BookServiceImpl(BookRepository bookRepository, GoogleDriveService googleDriveService) {

        this.bookRepository = bookRepository;
        this.googleDriveService = googleDriveService;
    }

    /* ================= USER SIDE ================= */

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllActiveBooks() {
        return bookRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getActiveBooksByCategory(BookCategory category) {
        return bookRepository
                .findByIsActiveTrueAndBookCategoryOrderByDisplayOrderAsc(category)
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getActiveBookBySlug(String slug) {
        Book book = bookRepository.findBySlugAndIsActiveTrue(slug)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        return BookMapper.toDTO(book);
    }

    /* ================= ADMIN SIDE ================= */

    @Override
    public BookDTO addBook(BookDTO bookDTO, MultipartFile file) {
        //Below Information are required to map to Book Entity. In order to save it to database.
        BookCategory enumCategory;
        try{
            enumCategory = BookCategory.valueOf(bookDTO.getBookCategory().toUpperCase());
        }catch(IllegalArgumentException e){
            throw new BadRequestException("Invalid Book Category, Create this Book Category is Required");
        }

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Book file is required");
        }

        //Upload the File into the Google Drive
        String BOOK_FOLDER_ID="1UPpIQYgmo2rwyDbiHGJpOZJRX5PIgDKo";
        Map<String, String> response;
        try {
            response = googleDriveService.uploadFile(
                    file,
                    BOOK_FOLDER_ID,
                    bookDTO.getTitle()
            );
        } catch (Exception e) {
            throw new BadRequestException("Error uploading book file: " + e.getMessage());
        }

        String previewURL = response.get("previewUrl");
        bookDTO.setDriveLink(previewURL);

        Book book = BookMapper.toEntity(bookDTO,enumCategory);
        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO){
        //Below Information are required to map to Book Entity. In order to save it to database.
        if(bookDTO.getDriveLink() == null || bookDTO.getId() == null){
            throw new BadRequestException("Your Book Doesn't have Drive Link or the Id, Contact Your Admin");
        }
        BookCategory enumCategory;
        try{
            enumCategory = BookCategory.valueOf(bookDTO.getBookCategory().toUpperCase());
        }catch(IllegalArgumentException e){
            throw new BadRequestException("Invalid Book Category, Create this Book Category is Required");
        }

        Book book =  bookRepository.save(BookMapper.toEntity(bookDTO, enumCategory));
        return BookMapper.toDTO(book);
    }


    /* ================= HELPERS ================= */

    private String generateUniqueSlug(String title) {
        String baseSlug = slugify(title);
        String slug = baseSlug;
        int counter = 1;

        while (bookRepository.existsBySlug(slug)) {
            counter++;
            slug = baseSlug + "-" + counter;
        }
        return slug;
    }

    private String slugify(String input) {
        return input.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
