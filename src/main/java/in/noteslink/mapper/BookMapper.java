package in.noteslink.mapper;

import in.noteslink.models.dto.BookDTO;
import in.noteslink.models.entity.Book;
import in.noteslink.models.enums.BookCategory;

public class BookMapper {

    private BookMapper() {
        // utility class
    }

    public static BookDTO toDTO(Book book) {
        if (book == null) return null;

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .slug(book.getSlug())
                .authorName(book.getAuthorName())
                .imageURL(book.getImageURL())
                .description(book.getDescription())
                .bookCategory(book.getBookCategory().toString())
                .driveLink(book.getDriveLink())
                .displayOrder(book.getDisplayOrder())
                .isActive(book.getIsActive())
                .build();
    }

    public static Book toEntity(BookDTO dto, BookCategory enumCategory) {
        if (dto == null) return null;

        return Book.builder()
                .title(dto.getTitle())
                .authorName(dto.getAuthorName())
                .imageURL(dto.getImageURL())
                .description(dto.getDescription())
                .bookCategory(enumCategory)
                .driveLink(dto.getDriveLink())
                .displayOrder(dto.getDisplayOrder())
                .isActive(dto.getIsActive())
                .build();
    }
}
