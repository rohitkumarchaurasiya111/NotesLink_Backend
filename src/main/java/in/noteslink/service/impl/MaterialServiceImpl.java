package in.noteslink.service.impl;

import in.noteslink.exception.BadRequestException;
import in.noteslink.mapper.MaterialMapper;
import in.noteslink.models.dto.MaterialDTO;
import in.noteslink.models.entity.DriveFolder;
import in.noteslink.models.entity.Material;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.MaterialType;
import in.noteslink.models.enums.Years;
import in.noteslink.repository.DriveFolderRepository;
import in.noteslink.repository.MaterialRepository;
import in.noteslink.repository.SubjectRepository;
import in.noteslink.service.GoogleDriveService;
import in.noteslink.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DriveFolderRepository driveFolderRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    //Here, Map<MaterialType(Notes,PYQ,etc..), ActualMaterialList>, this data will be sent to frontend so that materials can be displayed based on it's categories
    @Override
    public Map<MaterialType, List<MaterialDTO>> getAllMaterialsForGivenSubjectId(Long subjectId) {
        List<Material> materials = materialRepository.findBySubjectId(subjectId);

        Map<MaterialType, List<MaterialDTO>> categorizedMaterials = new HashMap<>();

        for (Material m: materials){
            MaterialDTO materialDTO = MaterialMapper.toMaterialDTO(m);          //Converts the Material to MaterialDTO

            //If key already present in our HashMap then add our MaterialDTO, if not present creates and List to store the MaterialDTO and then adds it
            categorizedMaterials.computeIfAbsent(m.getType(),k -> new ArrayList<>()).add(materialDTO);
        }

        // 🔥 Sort INSIDE each MaterialType by displayOrder
        categorizedMaterials.values().forEach(list ->
                list.sort(Comparator.comparing(MaterialDTO::getDisplayOrder))
        );

        return  categorizedMaterials;
    }

    @Override
    public MaterialDTO addMaterial(MaterialDTO materialDTO, MultipartFile file) {
        //Below Information are required to map to Material Entity. In order to save it to database.
        MaterialType enumType;
        try{
            enumType = MaterialType.valueOf(materialDTO.getType().toUpperCase());
        }catch(IllegalArgumentException e){
            throw new BadRequestException("Invalid Material Type, Create this Material Type is Required");
        }

        Subject subject = subjectRepository.findById(materialDTO.getSubjectId())
                        .orElseThrow(() -> new BadRequestException("SubjectId Doesn't Exist, First Create Subject with this ID"));

        Years enumYear = subject.getYear();
        Long college_id = subject.getCollege().getId();

        //Check DB: Does a folder mapping exist for this College + Name?
        DriveFolder driveFolder = driveFolderRepository.findByCollege_IdAndName(college_id, enumYear.name())
                .orElseThrow(() -> new BadRequestException("ERROR: Folder mapping not found for College Id: " + college_id + " and Year: " + enumYear.name() + " Ask Your ADMIN - Rohit to create a folder in Google Drive and map it in the database."));

        //If found, proceed to upload using the ID from DB
        Map<String, String> response;
        try{
            response = googleDriveService.uploadFile(file,driveFolder.getDriveFolderId(),materialDTO.getTitle());
        } catch (Exception e) {
            throw new BadRequestException("Some Error while uploading the File: " + e);
        }
        String fileId = response.get("fileId");
        String driveURL = response.get("url");
        String previewURL = response.get("previewUrl");

        materialDTO.setDriveLink(previewURL);
        Material material = MaterialMapper.toMaterialEntity(materialDTO, subject ,enumType);
        return MaterialMapper.toMaterialDTO(materialRepository.save(material));
    }

    @Override
    public MaterialDTO updateMaterial(MaterialDTO materialDTO) {
        //Below Information are required to map to Material Entity. In order to save it to database.
        if(materialDTO.getDriveLink() == null || materialDTO.getId() == null){
            throw new BadRequestException("Your Material Doesn't have Drive Link or the Id, Contact Your Admin");
        }
        MaterialType enumType;
        try{
            enumType = MaterialType.valueOf(materialDTO.getType().toUpperCase());
        }catch(IllegalArgumentException e){
            throw new BadRequestException("Invalid Material Type, Create this Material Type is Required");
        }

        Subject subject = subjectRepository.findById(materialDTO.getSubjectId())
                .orElseThrow(() -> new BadRequestException("SubjectId Doesn't Exist, First Create Subject with this ID"));

        Years enumYear = subject.getYear();
        Long college_id = subject.getCollege().getId();

        Material material = MaterialMapper.toMaterialEntity(materialDTO, subject ,enumType);
        return MaterialMapper.toMaterialDTO(materialRepository.save(material));
    }
}
