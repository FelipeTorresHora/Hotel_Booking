package felipe.proj.felipehotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private Long id;
    private LocalDate checkInData;
    private LocalDate checkOutData;
    private int numDeAdultos;
    private int numDeCriancas;
    private int totalNumDeVisitantes;
    private String bookingConfirmationCode;
    private UserDTO user;
    private QuartoDTO quarto;
}
