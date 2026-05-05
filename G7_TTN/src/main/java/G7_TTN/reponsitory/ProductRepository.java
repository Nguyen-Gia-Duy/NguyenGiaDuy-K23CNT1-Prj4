package G7_TTN.reponsitory;

import G7_TTN.entity.Product;
import G7_TTN.entity.Category;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndCategory(
            String keyword, Category category, Pageable pageable
    );
}