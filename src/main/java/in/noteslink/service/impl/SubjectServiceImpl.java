package in.noteslink.service.impl;

import in.noteslink.mapper.SubjectMapper;
import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.entity.College;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.Branches;
import in.noteslink.models.enums.Years;
import in.noteslink.repository.CollegeRepository;
import in.noteslink.repository.SubjectRepository;
import in.noteslink.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.noteslink.exception.BadRequestException;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    CollegeRepository collegeRepository;

    @Override
    public List<SubjectDTO> getAllSubjectsForSpecificYear(Years year) {
        List<Subject> subjects = subjectRepository.findByYear(year);

        //Convert these subject Entity to subjectDTO and returns as a List
        return subjects.stream()
                .map(SubjectMapper::toSubjectDTO)
                .toList();
    }

    @Override
    public List<SubjectDTO> getSubjectsByYearsAndCollege(Long collegeId, Years year) {
        List<Subject> subjects = subjectRepository.findForCollegeByYear(collegeId, year);

        //Convert these subject Entity to subjectDTO and returns as a List
        return subjects.stream()
                .map(SubjectMapper::toSubjectDTO)
                .toList();
    }

    //This method returns the specific subject details
    @Override
    public SubjectDTO getSpecificSubjectDetails(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new BadRequestException("Subject not found with id: " + subjectId));

        //Convert Subject to SubjectDTO
        return SubjectMapper.toSubjectDTO(subject);
    }

    @Override
    public SubjectDTO addSubject(SubjectDTO subjectDTO) {

        Years enumYear;
        Branches enumBranch;
        try{
            enumYear = Years.valueOf(subjectDTO.getYear().toUpperCase());
            enumBranch = Branches.valueOf(subjectDTO.getBranch().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid year or Branch");
        }

        College college = collegeRepository.findById(subjectDTO.getCollege_id())
                .orElseThrow(() -> new BadRequestException("Invalid College ID"));

        Subject subject = SubjectMapper.toSubjectEntity(subjectDTO,college, enumYear, enumBranch);

        return SubjectMapper.toSubjectDTO(subjectRepository.save(subject));
    }
}
