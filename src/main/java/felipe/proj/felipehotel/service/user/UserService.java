package felipe.proj.felipehotel.service.user;

import felipe.proj.felipehotel.dto.LoginRequest;
import felipe.proj.felipehotel.dto.Response;
import felipe.proj.felipehotel.dto.UserDTO;
import felipe.proj.felipehotel.entidades.User;
import felipe.proj.felipehotel.excecao.GlobalExcecao;
import felipe.proj.felipehotel.repositorio.UserRepositorio;
import felipe.proj.felipehotel.utils.JWTUtils;
import felipe.proj.felipehotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepositorio userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User loginRequest) {
        return null;
    }

    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new GlobalExcecao("user Not found"));

            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpiracaoTempo("7 Days");
            response.setMessage("successful");

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error Occurred During USer Login " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {

        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Sucesso!");
            response.setUserList(userDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao procurar User " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistorico(String userId) {

        Response response = new Response();


        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new GlobalExcecao("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDTO);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {

        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new GlobalExcecao("User não encontrado"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("Sucesso!");

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Erro ao procurar User " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {

        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new GlobalExcecao("User não encontrado"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Sucesso!");
            response.setUser(userDTO);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Erro ao procurar User " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new GlobalExcecao("User não encontrado"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Sucesso!");
            response.setUser(userDTO);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Erro ao procurar User " + e.getMessage());
        }
        return response;
    }
}
