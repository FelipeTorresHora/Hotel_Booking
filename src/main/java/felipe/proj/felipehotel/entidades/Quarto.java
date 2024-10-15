package felipe.proj.felipehotel.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoQuarto;
    private BigDecimal precoQuarto;
    private String fotoUrlQuarto;
    private String descricaoQuarto;
    @OneToMany(mappedBy = "quarto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();


    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", tipoQuarto='" + tipoQuarto + '\'' +
                ", precoQuarto=" + precoQuarto +
                ", fotoQuarto='" + fotoUrlQuarto + '\'' +
                ", descricaoQuarto='" + descricaoQuarto + '\'' +
                '}';
    }
}
