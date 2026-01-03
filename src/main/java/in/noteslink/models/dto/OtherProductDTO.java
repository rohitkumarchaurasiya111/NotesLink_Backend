package in.noteslink.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtherProductDTO {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Image URL cannot be blank")
    private String imageURL;

    @NotBlank(message = "Product URL cannot be blank")
    private String productUrl;

    @NotNull(message = "Display order is required")
    private Long displayOrder;

    private Boolean isActive;
}

