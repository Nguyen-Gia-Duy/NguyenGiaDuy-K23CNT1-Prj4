package G7_TTN.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "G7_color")
@Data
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}