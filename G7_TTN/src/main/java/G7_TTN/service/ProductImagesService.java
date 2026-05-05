package G7_TTN.service;

import G7_TTN.entity.Product;
import G7_TTN.entity.Product_images;
import G7_TTN.reponsitory.ProductImagesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImagesService {

    private final ProductImagesRepository repo;

    public ProductImagesService(ProductImagesRepository repo) {
        this.repo = repo;
    }

    public List<Product_images> getByProduct(Product product) {
        return repo.findByProduct(product);
    }
}