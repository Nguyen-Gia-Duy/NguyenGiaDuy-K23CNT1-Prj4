package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
@Table(name = "G7_category")
@Data
public class Category {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String notes;
    private String icon;
    private String slug;
    private String metatitle;
    private String metakeyword;
    private String metadescription;

    private Date createddate;
    private Date updateddate;
    private Integer isdelete;
    private Integer isactive;

    @ManyToOne
    @JoinColumn(name = "idparent")
    private Category parent;
}
