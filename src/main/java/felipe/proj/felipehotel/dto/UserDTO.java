package felipe.proj.felipehotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String email;
    private String nome;
    private String Numero;
    private String role;
    private List<BookingDTO> bookings = new ArrayList<>();
}
