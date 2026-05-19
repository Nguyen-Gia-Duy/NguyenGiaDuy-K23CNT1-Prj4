package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "G7_users")
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate;
    private Integer isdelete;
    private Integer isactive;
}
