package G7_TTN.service;

import G7_TTN.entity.Product;
import G7_TTN.entity.Category;
import G7_TTN.reponsitory.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Page<Product> getAll(String keyword, Category category, int page, String sort) {

        Sort sorting;

        switch (sort) {
            case "price_asc":
                sorting = Sort.by("price").ascending();
                break;
            case "price_desc":
                sorting = Sort.by("price").descending();
                break;
            default:
                sorting = Sort.by("id").descending();
        }

        Pageable pageable = PageRequest.of(page, 8, sorting);

        if (category != null) {
            return productRepo.findByNameContainingIgnoreCaseAndCategory(keyword, category, pageable);
        }

        return productRepo.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public Product findById(Long id) {
        return productRepo.findById(id).orElse(null);
    }
}