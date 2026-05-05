package G7_TTN.reponsitory;

import G7_TTN.entity.Product;
import G7_TTN.entity.Product_images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<Product_images, Long> {

    List<Product_images> findByProduct(Product product);
}