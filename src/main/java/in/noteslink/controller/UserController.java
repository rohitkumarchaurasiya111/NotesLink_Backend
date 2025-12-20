package in.noteslink.controller;

import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.enums.Years;
import in.noteslink.service.CollegeService;
import in.noteslink.service.MaterialService;
import in.noteslink.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private SubjectService subjectService;


    @GetMapping("/subjects/{subjectId}/materials")
    public ResponseEntity<List<MaterialDTO>> getAllMaterialsForGivenSubjectId(@PathVariable Long subjectId){
        List<MaterialDTO> listOfMaterials = materialService.getAllMaterialsForGivenSubjectId(subjectId);
        return ResponseEntity.ok(listOfMaterials);
    }

    @GetMapping("/subjects/college/{collegeId}/year/{year}")
    public ResponseEntity<List<SubjectDTO>> getAllSubjectsForSpecificCollegeAndYear(@PathVariable String year, @PathVariable Long collegeId){
        Years enumYear = Years.valueOf(year.toUpperCase());      //Converting year from String to Enum for further Processing
        List<SubjectDTO> subjects = subjectService.getSubjectsByYearsAndCollege(collegeId, enumYear);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<SubjectDTO> getSpecificSubjectDetails(@PathVariable Long subjectId){
        SubjectDTO subjectDTO = subjectService.getSpecificSubjectDetails(subjectId);
        return ResponseEntity.ok(subjectDTO);
    }

}
