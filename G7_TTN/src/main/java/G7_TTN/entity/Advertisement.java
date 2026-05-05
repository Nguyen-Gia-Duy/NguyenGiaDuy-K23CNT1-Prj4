package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "G7_advertisement")
@Data
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private String linkurl;
    private String position;

    private Date startdate;
    private Date enddate;
    private Date createddate;
    private Integer isactive;
}
