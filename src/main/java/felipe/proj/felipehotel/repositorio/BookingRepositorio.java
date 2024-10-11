package felipe.proj.felipehotel.repositorio;

import felipe.proj.felipehotel.entidades.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepositorio extends JpaRepository<Booking, Long> {

}
