package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "G7_orders")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idorders;
    private Date ordersdate;
    private BigDecimal totalmoney;

    private String namereceiver;
    private String address;
    private String phone;
    private String status;

    private Integer isdelete;
    private Integer isactive;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "idpayment")
    private Payment_method payment;

    @ManyToOne
    @JoinColumn(name = "idtransport")
    private Transport_method transport;
}