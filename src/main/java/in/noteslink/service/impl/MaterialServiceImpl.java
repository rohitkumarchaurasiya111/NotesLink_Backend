package in.noteslink.service.impl;

import in.noteslink.exception.BadRequestException;
import in.noteslink.mapper.MaterialMapper;
import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.entity.Material;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.MaterialType;
import in.noteslink.repository.MaterialRepository;
import in.noteslink.repository.SubjectRepository;
import in.noteslink.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    //Here, Map<MaterialType(Notes,PYQ,etc..), ActualMaterialList>, this data will be sent to frontend so that materials can be displayed based on it's categories
    @Override
    public Map<MaterialType, List<MaterialDTO>> getAllMaterialsForGivenSubjectId(Long subjectId) {
        List<Material> materials = materialRepository.findBySubjectId(subjectId);

        Map<MaterialType, List<MaterialDTO>> categorizedMaterials = new HashMap<>();

        for (Material m: materials){
            MaterialDTO materialDTO = MaterialMapper.toMaterialDTO(m);          //Converts the Material to MaterialDTO

            //If key already present in our HashMap then add our MaterialDTO, if not present creates and List to store the MaterialDTO and then adds it
            categorizedMaterials.computeIfAbsent(m.getType(),k -> new ArrayList<>()).add(materialDTO);
        }

        // 🔥 Sort INSIDE each MaterialType by displayOrder
        categorizedMaterials.values().forEach(list ->
                list.sort(Comparator.comparing(MaterialDTO::getDisplayOrder))
        );

        return  categorizedMaterials;
    }

    @Override
    public MaterialDTO addMaterial(MaterialDTO materialDTO) {
        MaterialType enumType;
        try{
            enumType = MaterialType.valueOf(materialDTO.getType().toUpperCase());
        }catch(IllegalArgumentException e){
            throw new BadRequestException("Invalid Material Type, Create this Material Type is Required");
        }

        Subject subject = subjectRepository.findById(materialDTO.getSubjectId())
                        .orElseThrow(() -> new BadRequestException("SubjectId Doesn't Exist, First Create Subject with this ID"));

        Material material = MaterialMapper.toMaterialEntity(materialDTO, subject ,enumType);
        return MaterialMapper.toMaterialDTO(materialRepository.save(material));
    }
}
