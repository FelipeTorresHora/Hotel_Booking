package felipe.proj.felipehotel.dto;

import java.util.List;

public class Response {
    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expiracaoTempo;
    private String bookingConfirmationCode;

    private UserDTO user;
    private QuartoDTO quarto;
    private BookingDTO booking;
    private List<UserDTO> userList;
    private List<QuartoDTO> quartoList;
    private List<BookingDTO> bookingList;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getExpiracaoTempo() {
        return expiracaoTempo;
    }

    public void setExpiracaoTempo(String expiracaoTempo) {
        this.expiracaoTempo = expiracaoTempo;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public QuartoDTO getQuarto() {
        return quarto;
    }

    public void setQuarto(QuartoDTO quarto) {
        this.quarto = quarto;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    public List<QuartoDTO> getQuartoList() {
        return quartoList;
    }

    public void setQuartoList(List<QuartoDTO> quartoList) {
        this.quartoList = quartoList;
    }

    public List<BookingDTO> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<BookingDTO> bookingList) {
        this.bookingList = bookingList;
    }
}
