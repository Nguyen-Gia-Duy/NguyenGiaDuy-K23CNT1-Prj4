package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "G7_order_tracking")
@Data
public class Order_tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String location;
    private String note;
    private Date updatedtime;

    @ManyToOne
    @JoinColumn(name = "idorder")
    private Orders orders;
}
