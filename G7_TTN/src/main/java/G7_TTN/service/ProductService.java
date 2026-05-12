package G7_TTN.service;

import G7_TTN.entity.Category;
import G7_TTN.entity.Product;
import G7_TTN.reponsitory.ProductRepository;

import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(
            ProductRepository productRepo
    ) {

        this.productRepo = productRepo;
    }

    // ================= LIST USER =================

    public Page<Product> getAll(
            String keyword,
            Category category,
            int page,
            String sort
    ) {

        if (keyword == null) {

            keyword = "";
        }

        Sort sorting;

        switch (sort) {

            case "price_asc":

                sorting =
                        Sort.by("price")
                                .ascending();

                break;

            case "price_desc":

                sorting =
                        Sort.by("price")
                                .descending();

                break;

            default:

                sorting =
                        Sort.by("id")
                                .descending();
        }

        Pageable pageable =
                PageRequest.of(
                        page,
                        8,
                        sorting
                );

        // ================= FILTER CATEGORY =================

        if (category != null) {

            return productRepo
                    .findByNameContainingIgnoreCaseAndCategory(
                            keyword,
                            category,
                            pageable
                    );
        }

        // ================= ALL PRODUCT =================

        return productRepo
                .findByNameContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }

    // ================= ADMIN =================

    // LIST

    public List<Product> findAll() {

        return productRepo.findAll();
    }

    // SAVE

    public void save(Product product) {

        productRepo.save(product);
    }

    // DELETE

    public void delete(Long id) {

        productRepo.deleteById(id);
    }

    // COUNT

    public long count() {

        return productRepo.count();
    }

    // ================= DETAIL =================

    public Product findById(Long id) {

        return productRepo
                .findById(id)
                .orElse(null);
    }

    // ================= SALE =================

    public Page<Product> getSaleProducts(
            int page
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        8,
                        Sort.by("id")
                                .descending()
                );

        return productRepo
                .getSaleProducts(pageable);
    }
}