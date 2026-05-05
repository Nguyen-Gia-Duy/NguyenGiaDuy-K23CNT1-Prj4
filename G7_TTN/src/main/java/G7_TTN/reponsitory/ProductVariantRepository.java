package G7_TTN.reponsitory;

import G7_TTN.entity.Product;
import G7_TTN.entity.Product_variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<Product_variant, Long> {

    List<Product_variant> findByProduct(Product product);
}