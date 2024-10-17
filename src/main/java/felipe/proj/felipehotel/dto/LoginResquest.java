package felipe.proj.felipehotel.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginResquest {
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Senha é obrigatório")
    private String password;
}
