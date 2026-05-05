package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "G7_product_images")
@Data
public class Product_images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String urlimg;

    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;
}