package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "G7_cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createddate;
    private Date updateddate;
    private Integer isactive;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private Users user;
}