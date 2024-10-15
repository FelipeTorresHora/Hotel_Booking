package felipe.proj.felipehotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuartoDTO {
    private Long id;
    private String tipoQuarto;
    private BigDecimal precoQuarto;
    private String fotoUrlQuarto;
    private String descricaoQuarto;
    private List<BookingDTO> bookings;
}
