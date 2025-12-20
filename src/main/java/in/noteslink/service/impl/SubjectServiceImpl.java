package in.noteslink.service.impl;

import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.Years;
import in.noteslink.repository.CollegeRepository;
import in.noteslink.repository.SubjectRepository;
import in.noteslink.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    CollegeRepository collegeRepository;

    @Override
    public List<SubjectDTO> getAllSubjectsForSpecificYear(Years year) {
        List<Subject> subjects = subjectRepository.findByYear(year);

        return subjects.stream().map(s -> new SubjectDTO(
                s.getId(),
                s.getCollege() != null ? s.getCollege().getId() : null,
                getCollegeName(s),
                s.getName(),
                s.getImageURL(),
                s.getDescription(),
                s.getYear().name(),
                s.getBranch().name()
        )).collect(Collectors.toList());
    }

    @Override
    public List<SubjectDTO> getSubjectsByYearsAndCollege(Long collegeId, Years year) {
        List<Subject> subjects = subjectRepository.findForCollegeByYear(collegeId, year);

        return subjects.stream().map(s -> new SubjectDTO(
                s.getId(),
                s.getCollege() != null ? s.getCollege().getId() : null,
                getCollegeName(s),
                s.getName(),
                s.getImageURL(),
                s.getDescription(),
                s.getYear().name(),
                s.getBranch().name()
        )).collect(Collectors.toList());
    }

    //This method returns the specific subject details
    @Override
    public SubjectDTO getSpecificSubjectDetails(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectId));

        return new SubjectDTO(
                subject.getId(),
                subject.getCollege() != null ? subject.getCollege().getId() : null,
                getCollegeName(subject),
                subject.getName(),
                subject.getImageURL(),
                subject.getDescription(),
                subject.getYear().name(),
                subject.getBranch().name()
        );
    }

    public String getCollegeName(Subject subject){
        if (subject.getCollege() != null){
            return collegeRepository.findById(subject.getCollege().getId()).get().getName();
        }
        return "Placement";
    }
}
