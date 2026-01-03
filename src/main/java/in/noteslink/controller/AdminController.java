package in.noteslink.controller;

import in.noteslink.exception.BadRequestException;
import in.noteslink.models.dto.BookDTO;
import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.dto.ProjectDTO;
import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.entity.Material;
import in.noteslink.models.entity.Subject;
import in.noteslink.service.BookService;
import in.noteslink.service.MaterialService;
import in.noteslink.service.ProjectService;
import in.noteslink.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ProjectService projectService;

    /*
    * Subject Controller
    * */

    //Valid - checks the validation that we have written in SubjectDTO, if fails - Spring returns 400 Bad Request
    @PostMapping("/subject")
    public ResponseEntity<SubjectDTO> addSubject(@Valid @RequestBody SubjectDTO subjectDTO){
        SubjectDTO responseSubjectDTO = subjectService.addSubject(subjectDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseSubjectDTO);
    }

    /*
    * Material Controller
    * */

    // Without consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    //Spring may reject the request
    //Content-type mismatch errors occur
    @PostMapping(value = "/material", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MaterialDTO> addMaterial(@Valid @RequestPart("material") MaterialDTO materialDTO,
                                                   @RequestPart("file") MultipartFile file){
        MaterialDTO responseMaterialDTO = materialService.addMaterial(materialDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMaterialDTO);
    }

    @PutMapping("/material/{id}")
    public ResponseEntity<MaterialDTO> updateMaterial(@Valid @RequestBody MaterialDTO materialDTO,
                                                      @PathVariable Long id){
        if(!Objects.equals(id, materialDTO.getId())) throw new BadRequestException("Contact Admin, Material Id in JSON != Id in API Call");
        MaterialDTO responseMaterialDTO = materialService.updateMaterial(materialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMaterialDTO);
    }

    /*
    * Book Controller
    * */
    // Without consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    //Spring may reject the request
    //Content-type mismatch errors occur
    @PostMapping(value = "/book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookDTO> addBook(@Valid @RequestPart("book") BookDTO bookDTO,
                                                   @RequestPart("file") MultipartFile file){
        BookDTO responseBookDTO = bookService.addBook(bookDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBookDTO);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO bookDTO, @PathVariable Long id){
        if(!Objects.equals(id, bookDTO.getId())) throw new BadRequestException("Contact Admin, Book Id in JSON != Id in API Call");
        BookDTO responseBookDTO =  bookService.updateBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBookDTO);
    }

    /*
    * Project Service
    * */
    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> createProject(
            @Valid @RequestBody ProjectDTO projectDTO) {

        ProjectDTO createdProject = projectService.createProject(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }
}
