package in.noteslink.mapper;

import in.noteslink.models.dto.OtherProductDTO;
import in.noteslink.models.entity.OtherProduct;

public class OtherProductMapper {

    public static OtherProductDTO toDTO(OtherProduct product) {
        return OtherProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .imageURL(product.getImageURL())
                .productUrl(product.getProductUrl())
                .displayOrder(product.getDisplayOrder())
                .isActive(product.getIsActive())
                .build();
    }
}

