package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "G7_product_sale")
@Getter
@Setter
public class ProductSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer discountpercent;

    private Double discountprice;

    private Date startdate;

    private Date enddate;

    private Date createddate;

    private Integer isactive;

    @ManyToOne
    @JoinColumn(name = "idproduct")
    private Product product;
}