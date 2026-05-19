package G7_TTN.service;

import G7_TTN.entity.*;
import G7_TTN.reponsitory.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantService {

    private final ProductVariantRepository repo;

    public ProductVariantService(ProductVariantRepository repo) {
        this.repo = repo;
    }

    public List<Product_variant> getByProduct(Product product) {
        return repo.findByProduct(product);
    }

    // lấy danh sách size theo product
    public List<Size> getSizesByProduct(Product product) {
        return repo.findByProduct(product)
                .stream()
                .map(Product_variant::getSize)
                .distinct()
                .collect(Collectors.toList());
    }

    // lấy danh sách color theo product
    public List<Color> getColorsByProduct(Product product) {
        return repo.findByProduct(product)
                .stream()
                .map(Product_variant::getColor)
                .distinct()
                .collect(Collectors.toList());
    }
}