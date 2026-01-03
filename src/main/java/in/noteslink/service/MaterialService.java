package in.noteslink.service;

import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.enums.MaterialType;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MaterialService {
    public Map<MaterialType, List<MaterialDTO>> getAllMaterialsForGivenSubjectId(Long subjectId);
    public MaterialDTO addMaterial(MaterialDTO materialDTO, MultipartFile file);
    public  MaterialDTO updateMaterial(@Valid MaterialDTO materialDTO);
}
