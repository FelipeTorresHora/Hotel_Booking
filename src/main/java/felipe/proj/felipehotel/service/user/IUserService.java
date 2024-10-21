package felipe.proj.felipehotel.service.user;

import felipe.proj.felipehotel.dto.LoginRequest;
import felipe.proj.felipehotel.dto.Response;
import felipe.proj.felipehotel.entidades.User;

public interface IUserService {
    Response register(User loginRequest);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistorico(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);
}
