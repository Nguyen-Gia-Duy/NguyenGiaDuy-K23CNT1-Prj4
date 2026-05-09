package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "G7_product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String image;
    private String brand;
    private String material;

    private BigDecimal price;
    private Integer quantity;
    private String slug;

    private Date createddate;
    private Date updateddate;
    private Integer isdelete;
    private Integer isactive;

    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;

    @OneToOne(mappedBy = "product")
    private ProductSale sale;

    @OneToMany(mappedBy = "product")
    private List<Product_images> images;
}