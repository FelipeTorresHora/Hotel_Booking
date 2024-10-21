package felipe.proj.felipehotel.repositorio;

import felipe.proj.felipehotel.entidades.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepositorio extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

}
