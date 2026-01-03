package in.noteslink.service;

import in.noteslink.models.dto.OtherProductDTO;

import java.util.List;

public interface OtherProductService {
    public List<OtherProductDTO> getAllActiveProducts();
}
