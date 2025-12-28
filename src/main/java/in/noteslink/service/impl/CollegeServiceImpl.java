package in.noteslink.service.impl;

import in.noteslink.models.entity.College;
import in.noteslink.repository.CollegeRepository;
import in.noteslink.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    @Override
    public List<College> getAllCollegeDetails() {
        return collegeRepository.findAll();
    }
}
