package in.noteslink.controller;

import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.entity.Material;
import in.noteslink.models.entity.Subject;
import in.noteslink.service.MaterialService;
import in.noteslink.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MaterialService materialService;

    //Valid - checks the validation that we have written in SubjectDTO, if fails - Spring returns 400 Bad Request
    @PostMapping("/subject")
    public ResponseEntity<SubjectDTO> addSubject(@Valid @RequestBody SubjectDTO subjectDTO){
        SubjectDTO responseSubjectDTO = subjectService.addSubject(subjectDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseSubjectDTO);
    }

    @PostMapping("/material")
    public ResponseEntity<MaterialDTO> addMaterial(@Valid @RequestBody MaterialDTO materialDTO){
        MaterialDTO responseMaterialDTO = materialService.addMaterial(materialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMaterialDTO);
    }
}
