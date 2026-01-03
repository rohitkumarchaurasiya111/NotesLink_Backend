package in.noteslink.models.dto;

import in.noteslink.models.enums.BookCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    private String slug;

    @Size(max = 255, message = "Author name cannot exceed 255 characters")
    private String authorName;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageURL;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private String driveLink;

    @NotNull(message = "Book category is required")
    private String bookCategory;

    @NotNull(message = "Display order is required")
    private Long displayOrder;

    private Boolean isActive;
}
