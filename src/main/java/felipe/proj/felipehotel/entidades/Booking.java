package felipe.proj.felipehotel.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in data é obbrigatório")
    private LocalDate checkInData;

    @Future(message = "check out data deve ser após check in")
    private LocalDate checkOutData;

    @Min(value = 1, message = "Numero de adultos é menor que 1")
    private int numDeAdultos;

    @Min(value = 0, message = "Numero de criança não pode ser menor que zero")
    private int numDeCriancas;

    private int totalNumDeVisitantes;

    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarto_id")
    private Quarto quarto;

    public void calculateTotalNumDeVisitantes() {
        this.totalNumDeVisitantes = this.numDeAdultos + this.numDeCriancas;
    }

    public void setNumDeAdultos(int numDeAdultos) {
        this.numDeAdultos = numDeAdultos;
        calculateTotalNumDeVisitantes();
    }

    public void setNumDeCriancas(int numDeCriancas) {
        this.numDeCriancas = numDeCriancas;
        calculateTotalNumDeVisitantes();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkInData=" + checkInData +
                ", checkOutData=" + checkOutData +
                ", numDeAdultos=" + numDeAdultos +
                ", numDeCriancas=" + numDeCriancas +
                ", totalNumDeVisitantes=" + totalNumDeVisitantes +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }
}
