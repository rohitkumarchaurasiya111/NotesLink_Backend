package in.noteslink.service.impl;

import in.noteslink.mapper.OtherProductMapper;
import in.noteslink.models.dto.OtherProductDTO;
import in.noteslink.repository.OtherProductRepository;
import in.noteslink.service.OtherProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherProductServiceImpl implements OtherProductService {
    private final OtherProductRepository repository;

    public OtherProductServiceImpl(OtherProductRepository repository) {
        this.repository = repository;
    }

    public List<OtherProductDTO> getAllActiveProducts() {
        return repository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(OtherProductMapper::toDTO)
                .toList();
    }
}
