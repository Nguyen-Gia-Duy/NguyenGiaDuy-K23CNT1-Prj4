package G7_TTN.service;

import G7_TTN.entity.Product;
import G7_TTN.entity.Product_variant;
import G7_TTN.reponsitory.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantService {

    private final ProductVariantRepository repo;

    public ProductVariantService(ProductVariantRepository repo) {
        this.repo = repo;
    }

    public List<Product_variant> getByProduct(Product product) {
        return repo.findByProduct(product);
    }
}