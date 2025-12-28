package in.noteslink.mapper;

import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.entity.Material;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.MaterialType;

//This class maps from Material Entity to MaterialDTO and MaterialDTO to Material Entity
public class MaterialMapper {

    // 🔁 DTO → Entity
    public static Material toMaterialEntity(MaterialDTO materialDTO, Subject subject, MaterialType materialType){
        return Material.builder()
                .id(materialDTO.getId())
                .title(materialDTO.getTitle())
                .subject(subject)
                .type(materialType)
                .driveLink(materialDTO.getDriveLink())
                .isPremium(materialDTO.getIsPremium())
                .displayOrder(materialDTO.getDisplayOrder())
                .build();
    }

    // 🔁 Entity → DTO
    public static MaterialDTO toMaterialDTO(Material material){
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setId(material.getId());
        materialDTO.setTitle(material.getTitle());
        materialDTO.setSubjectId(material.getSubject().getId());
        materialDTO.setType(material.getType().name());
        materialDTO.setDriveLink(material.getDriveLink());
        materialDTO.setIsPremium(material.getIsPremium());
        materialDTO.setDisplayOrder(material.getDisplayOrder());

        return materialDTO;
    }
}
