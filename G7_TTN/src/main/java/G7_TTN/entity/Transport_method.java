package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "G7_transport_method")
@Data
public class Transport_method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private String notes;

    private Date createddate;
    private Date updateddate;
    private Integer isdelete;
    private Integer isactive;
}