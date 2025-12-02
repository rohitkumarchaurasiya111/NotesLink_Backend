package in.noteslink.service.impl;

import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.Years;
import in.noteslink.repository.SubjectRepository;
import in.noteslink.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<SubjectDTO> getAllSubjectsForSpecificYear(Years year) {
        List<Subject> subjects = subjectRepository.findByYear(year);

        return subjects.stream().map(s -> new SubjectDTO(
                s.getId(),
                s.getCollege() != null ? s.getCollege().getId() : null,
                s.getName(),
                s.getImageURL(),
                s.getDescription(),
                s.getYear().name(),
                s.getBranch().name()
        )).collect(Collectors.toList());
    }
}
