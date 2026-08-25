package in.noteslink.controller;

import in.noteslink.models.dto.*;
import in.noteslink.models.entity.Book;
import in.noteslink.models.entity.College;
import in.noteslink.models.enums.BookCategory;
import in.noteslink.models.enums.MaterialType;
import in.noteslink.models.enums.ProjectDifficulty;
import in.noteslink.models.enums.Years;
import in.noteslink.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private OtherProductService otherProductService;


    /*
    * Subject Controller
    * */
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

    @GetMapping("/subjects/{subjectId}/materials")
    public ResponseEntity<Map<MaterialType, List<MaterialDTO>>> getAllMaterialsForGivenSubjectId(@PathVariable Long subjectId){
        Map<MaterialType, List<MaterialDTO>> listOfMaterials = materialService.getAllMaterialsForGivenSubjectId(subjectId);
        return ResponseEntity.ok(listOfMaterials);
    }

    /*
    * College Controller
    * */
    @GetMapping("/colleges")
    public ResponseEntity<List<College>> getAllCollegesDetails(){
        List<College> colleges = collegeService.getAllCollegeDetails();
        return ResponseEntity.ok(colleges);
    }

    /*
    * Book Controller
    * */
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllActiveBooks());
    }

    @GetMapping("/books/category/{category}")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(
            @PathVariable String category) {

        BookCategory bookCategory = BookCategory.valueOf(category.toUpperCase());
        return ResponseEntity.ok(
                bookService.getActiveBooksByCategory(bookCategory)
        );
    }

    /*
    * Project Controller
    * */
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllActiveProjects());
    }

    @GetMapping("/projects/difficulty/{difficulty}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByDifficulty(
            @PathVariable String difficulty) {
        ProjectDifficulty level =
                ProjectDifficulty.valueOf(difficulty.toUpperCase());
        return ResponseEntity.ok(
                projectService.getActiveProjectsByDifficulty(level)
        );
    }

    /*
    * OtherProduct Controller
    * */
    @GetMapping("/otherproducts")
    public ResponseEntity<List<OtherProductDTO>> getAllProducts() {
        return ResponseEntity.ok(otherProductService.getAllActiveProducts());
    }
}
