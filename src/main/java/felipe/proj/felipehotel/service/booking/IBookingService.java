package felipe.proj.felipehotel.service.booking;

import felipe.proj.felipehotel.dto.Response;
import felipe.proj.felipehotel.entidades.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
