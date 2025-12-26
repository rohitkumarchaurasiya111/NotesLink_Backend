package in.noteslink.service.impl;

import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.entity.Material;
import in.noteslink.models.enums.MaterialType;
import in.noteslink.repository.MaterialRepository;
import in.noteslink.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    //Here, Map<MaterialType(Notes,PYQ,etc..), ActualMaterialList>, this data will be sent to frontend so that materials can be displayed based on it's categories
    @Override
    public Map<MaterialType, List<MaterialDTO>> getAllMaterialsForGivenSubjectId(Long subjectId) {
        List<Material> materials = materialRepository.findBySubjectId(subjectId);

        Map<MaterialType, List<MaterialDTO>> categorizedMaterials = new HashMap<>();

        for (Material m: materials){
            MaterialDTO materialDTO = new MaterialDTO(
                    m.getId(),
                    m.getSubject() != null ? m.getSubject().getId() : null,
                    m.getTitle(),
                    m.getType() != null ? m.getType().name() : null,
                    m.getDriveLink(),
                    m.getIsPremium());

            //If key already present in our HashMap then add our MaterialDTO, if not present creates and List to store the MaterialDTO and then adds it
            categorizedMaterials.computeIfAbsent(m.getType(),k -> new ArrayList<>()).add(materialDTO);
        }
        return  categorizedMaterials;
    }
}
