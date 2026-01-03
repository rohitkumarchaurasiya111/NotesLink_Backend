package in.noteslink.repository;

import in.noteslink.models.entity.OtherProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OtherProductRepository extends JpaRepository<OtherProduct, Long> {
    List<OtherProduct> findByIsActiveTrueOrderByDisplayOrderAsc();
}
