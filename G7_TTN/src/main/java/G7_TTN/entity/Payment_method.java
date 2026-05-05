package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "G7_payment_method")
@Data
public class Payment_method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String notes;
    private Date createddate;
    private Date updateddate;
    private Integer isdelete;
    private Integer isactive;
}