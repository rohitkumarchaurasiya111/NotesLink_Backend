package in.noteslink.service.impl;

import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.entity.Material;
import in.noteslink.repository.MaterialRepository;
import in.noteslink.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<MaterialDTO> getAllMaterialsForGivenSubjectId(Long subjectId) {
        List<Material> materials = materialRepository.findBySubjectId(subjectId);

        return  materials.stream().map(m -> new MaterialDTO(
                m.getId(),
                m.getSubject() != null ? m.getSubject().getId() : null,
                m.getTitle(),
                m.getType() != null ? m.getType().name() : null,
                m.getDriveLink(),
                m.getIsPremium()
        )).collect(Collectors.toList());
    }
}
