package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date enddate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date createddate;

    private Integer isactive;
}