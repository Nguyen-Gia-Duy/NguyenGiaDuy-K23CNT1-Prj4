package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "G7_size")
@Data
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}