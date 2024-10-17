package felipe.proj.felipehotel.service.impl;

import felipe.proj.felipehotel.dto.LoginResquest;
import felipe.proj.felipehotel.dto.Response;
import felipe.proj.felipehotel.dto.UserDTO;
import felipe.proj.felipehotel.entidades.User;
import felipe.proj.felipehotel.excecao.GlobalExcecao;
import felipe.proj.felipehotel.repositorio.UserRepositorio;
import felipe.proj.felipehotel.service.interafac.IUserService;
import felipe.proj.felipehotel.utils.JWTUtils;
import felipe.proj.felipehotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
    public Response login(LoginResquest loginRequest) {
        return null;
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
        return null;
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
