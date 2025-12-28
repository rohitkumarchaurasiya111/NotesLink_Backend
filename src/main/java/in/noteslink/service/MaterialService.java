package in.noteslink.service;

import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.enums.MaterialType;

import java.util.List;
import java.util.Map;

public interface MaterialService {
    public Map<MaterialType, List<MaterialDTO>> getAllMaterialsForGivenSubjectId(Long subjectId);
    public MaterialDTO addMaterial(MaterialDTO materialDTO);
}
