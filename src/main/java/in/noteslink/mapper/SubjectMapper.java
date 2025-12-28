package in.noteslink.mapper;

import in.noteslink.models.dto.SubjectDTO;
import in.noteslink.models.entity.College;
import in.noteslink.models.entity.Subject;
import in.noteslink.models.enums.Branches;
import in.noteslink.models.enums.Years;

//This class maps from Subject Entity to SubjectDTO and SubjectDTO to Subject Entity
public class SubjectMapper {

    // 🔁 Entity → DTO
    public static SubjectDTO toSubjectDTO(Subject subject) {

        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setImageURL(subject.getImageURL());
        dto.setDescription(subject.getDescription());
        dto.setYear(subject.getYear().name());
        dto.setBranch(subject.getBranch().name());
        dto.setIsProject(subject.getIsProject());

        College college = subject.getCollege();
        dto.setCollege_id(college.getId());
        dto.setCollege_name(college.getName());

        return dto;
    }

    // 🔁 DTO → Entity
    public static Subject toSubjectEntity(
            SubjectDTO dto,
            College college,
            Years year,
            Branches branch
    ) {
        // ✅ Enforce default image, if imageURL is Null or Blank
        if (dto.getImageURL() == null || dto.getImageURL().isBlank()) {
            dto.setImageURL(
                    "https://res.cloudinary.com/dfdusmc9k/image/upload/v1766859518/SubjectImageGirl_m6maff.png"
            );
        }

        return Subject.builder()
                .id(dto.getId())
                .name(dto.getName())
                .imageURL(dto.getImageURL())
                .description(dto.getDescription())
                .year(year)
                .branch(branch)
                .isProject(dto.getIsProject())
                .college(college)
                .build();
    }
}