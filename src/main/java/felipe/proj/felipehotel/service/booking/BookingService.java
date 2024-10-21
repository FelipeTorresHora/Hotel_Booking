package felipe.proj.felipehotel.service.booking;
import felipe.proj.felipehotel.dto.BookingDTO;
import felipe.proj.felipehotel.dto.Response;
import felipe.proj.felipehotel.entidades.Booking;
import felipe.proj.felipehotel.entidades.Quarto;
import felipe.proj.felipehotel.entidades.User;
import felipe.proj.felipehotel.excecao.GlobalExcecao;
import felipe.proj.felipehotel.repositorio.BookingRepositorio;
import felipe.proj.felipehotel.repositorio.QuartoRepositorio;
import felipe.proj.felipehotel.repositorio.UserRepositorio;
import felipe.proj.felipehotel.service.quarto.IQuartoService;
import felipe.proj.felipehotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepositorio bookingRepositorio;
    @Autowired
    private IQuartoService quartoService;
    @Autowired
    private QuartoRepositorio quartoRepository;
    @Autowired
    private UserRepositorio userRepositorio;


    @Override
    public Response saveBooking(Long quartoId, Long userId, Booking bookingRequest) {

        Response response = new Response();

        try {
            if (bookingRequest.getCheckOutData().isBefore(bookingRequest.getCheckInData())) {
                throw new IllegalArgumentException("Check in data deve vim antes do Check out");
            }
            Quarto quarto = quartoRepository.findById(quartoId).orElseThrow(() -> new GlobalExcecao("Quarto Não encontrado"));
            User user = userRepositorio.findById(userId).orElseThrow(() -> new GlobalExcecao("User Não encontrado"));

            List<Booking> existingBookings = quarto.getBookings();

            if (!quartoIsAvailable(bookingRequest, existingBookings)) {
                throw new GlobalExcecao("Quarto não está disponivel na data selecionada");
            }

            bookingRequest.setQuarto(quarto);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepositorio.save(bookingRequest);
            response.setStatusCode(200);
            response.setMessage("Sucesso");
            response.setBookingConfirmationCode(bookingConfirmationCode);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar booking: " + e.getMessage());

        }
        return response;
    }


    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {

        Response response = new Response();

        try {
            Booking booking = bookingRepositorio.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new GlobalExcecao("Booking Não encontrado"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedQuartos(booking, true);
            response.setStatusCode(200);
            response.setMessage("Sucesso");
            response.setBooking(bookingDTO);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao encontrar um booking: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllBookings() {

        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepositorio.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("Sucesso");
            response.setBookingList(bookingDTOList);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao ver todos os bookings: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {

        Response response = new Response();

        try {
            bookingRepositorio.findById(bookingId).orElseThrow(() -> new GlobalExcecao("Booking não existente"));
            bookingRepositorio.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Sucesso");

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao cancelar booking: " + e.getMessage());

        }
        return response;
    }


    private boolean quartoIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInData().equals(existingBooking.getCheckInData())
                                || bookingRequest.getCheckOutData().isBefore(existingBooking.getCheckOutData())
                                || (bookingRequest.getCheckInData().isAfter(existingBooking.getCheckInData())
                                && bookingRequest.getCheckInData().isBefore(existingBooking.getCheckOutData()))
                                || (bookingRequest.getCheckInData().isBefore(existingBooking.getCheckInData())

                                && bookingRequest.getCheckOutData().equals(existingBooking.getCheckOutData()))
                                || (bookingRequest.getCheckInData().isBefore(existingBooking.getCheckInData())

                                && bookingRequest.getCheckOutData().isAfter(existingBooking.getCheckOutData()))

                                || (bookingRequest.getCheckInData().equals(existingBooking.getCheckOutData())
                                && bookingRequest.getCheckOutData().equals(existingBooking.getCheckInData()))

                                || (bookingRequest.getCheckInData().equals(existingBooking.getCheckOutData())
                                && bookingRequest.getCheckOutData().equals(bookingRequest.getCheckInData()))
                );
    }
}

