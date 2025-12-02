package in.noteslink.service;

import in.noteslink.models.dto.MaterialDTO;
import java.util.List;

public interface MaterialService {
    List<MaterialDTO> getAllMaterialsForGivenSubjectId(Long subjectId);
}
