package G7_TTN.reponsitory;

import G7_TTN.entity.Product;
import G7_TTN.entity.Product_variant;
import G7_TTN.entity.Size;
import G7_TTN.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<Product_variant, Long> {

    // ================= ALL VARIANTS =================
    List<Product_variant> findByProduct(Product product);

    // ================= DISTINCT SIZE =================
    @Query("SELECT DISTINCT v.size FROM Product_variant v WHERE v.product = :product")
    List<Size> findDistinctSizeByProduct(@Param("product") Product product);

    // ================= DISTINCT COLOR =================
    @Query("SELECT DISTINCT v.color FROM Product_variant v WHERE v.product = :product")
    List<Color> findDistinctColorByProduct(@Param("product") Product product);
}