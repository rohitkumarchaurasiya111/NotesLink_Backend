package in.noteslink.service;

import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.enums.Years;

import java.util.List;

public interface SubjectService {
    public List<SubjectDTO> getAllSubjectsForSpecificYear(Years year);
    public List<SubjectDTO> getSubjectsByYearsAndCollege(Long collegeId, Years year);
    public SubjectDTO getSpecificSubjectDetails(Long subjectId);
}
