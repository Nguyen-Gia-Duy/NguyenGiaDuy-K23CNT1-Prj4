package G7_TTN.reponsitory;

import G7_TTN.entity.Category;
import G7_TTN.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    // ================= ALL PRODUCT =================

    Page<Product> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    // ================= FILTER CATEGORY =================

    Page<Product> findByNameContainingIgnoreCaseAndCategory(
            String keyword,
            Category category,
            Pageable pageable
    );

    // ================= SALE PRODUCT =================

    @Query("""
            SELECT p
            FROM Product p
            JOIN p.sale s
            WHERE s.isactive = 1
            """)
    Page<Product> getSaleProducts(
            Pageable pageable
    );

}