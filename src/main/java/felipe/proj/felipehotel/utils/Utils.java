package felipe.proj.felipehotel.utils;

import felipe.proj.felipehotel.dto.BookingDTO;
import felipe.proj.felipehotel.dto.QuartoDTO;
import felipe.proj.felipehotel.dto.UserDTO;
import felipe.proj.felipehotel.entidades.Booking;
import felipe.proj.felipehotel.entidades.Quarto;
import felipe.proj.felipehotel.entidades.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom secureRandom = new SecureRandom();


    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }


    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setNome(user.getNome());
        userDTO.setEmail(user.getEmail());
        userDTO.setNumero(user.getNumero());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static QuartoDTO mapQuartoEntityToQuartoDTO(Quarto quarto) {
        QuartoDTO quartoDTO = new QuartoDTO();

        quartoDTO.setId(quarto.getId());
        quartoDTO.setTipoQuarto(quarto.getTipoQuarto());
        quartoDTO.setPrecoQuarto(quarto.getPrecoQuarto());
        quartoDTO.setFotoUrlQuarto(quarto.getFotoUrlQuarto());
        quartoDTO.setDescricaoQuarto(quarto.getDescricaoQuarto());
        return quartoDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        // Map simple fields
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInData(booking.getCheckInData());
        bookingDTO.setCheckOutData(booking.getCheckOutData());
        bookingDTO.setNumDeAdultos(booking.getNumDeAdultos());
        bookingDTO.setNumDeCriancas(booking.getNumDeCriancas());
        bookingDTO.setTotalNumDeVisitantes(booking.getTotalNumDeVisitantes());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;
    }

    public static QuartoDTO mapQuartoEntityToQuartoDTOPlusBookings(Quarto quarto) {
        QuartoDTO quartoDTO = new QuartoDTO();

        quartoDTO.setId(quarto.getId());
        quartoDTO.setTipoQuarto(quarto.getTipoQuarto());
        quartoDTO.setPrecoQuarto(quarto.getPrecoQuarto());
        quartoDTO.setFotoUrlQuarto(quarto.getFotoUrlQuarto());
        quartoDTO.setDescricaoQuarto(quarto.getDescricaoQuarto());

        if (quarto.getBookings() != null) {
            quartoDTO.setBookings(quarto.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }
        return quartoDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedQuartos(Booking booking, boolean mapUser) {

        BookingDTO bookingDTO = new BookingDTO();
        // Map simple fields
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInData(booking.getCheckInData());
        bookingDTO.setCheckOutData(booking.getCheckOutData());
        bookingDTO.setNumDeAdultos(booking.getNumDeAdultos());
        bookingDTO.setNumDeCriancas(booking.getNumDeCriancas());
        bookingDTO.setTotalNumDeVisitantes(booking.getTotalNumDeVisitantes());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        if (mapUser) {
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }
        if (booking.getQuarto() != null) {
            QuartoDTO quartoDTO = new QuartoDTO();

            quartoDTO.setId(booking.getQuarto().getId());
            quartoDTO.setTipoQuarto(booking.getQuarto().getTipoQuarto());
            quartoDTO.setPrecoQuarto(booking.getQuarto().getPrecoQuarto());
            quartoDTO.setFotoUrlQuarto(booking.getQuarto().getFotoUrlQuarto());
            quartoDTO.setDescricaoQuarto(booking.getQuarto().getDescricaoQuarto());
            bookingDTO.setQuarto(quartoDTO);
        }
        return bookingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsAndQuarto(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setNome(user.getNome());
        userDTO.setEmail(user.getEmail());
        userDTO.setNumero(user.getNumero());
        userDTO.setRole(user.getRole());

        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedQuartos(booking, false)).collect(Collectors.toList()));
        }
        return userDTO;
    }


    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<QuartoDTO> mapQuartoListEntityToQuartoListDTO(List<Quarto> quartoList) {
        return quartoList.stream().map(Utils::mapQuartoEntityToQuartoDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }

}
