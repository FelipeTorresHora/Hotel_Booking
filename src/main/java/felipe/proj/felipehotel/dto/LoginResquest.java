package felipe.proj.felipehotel.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class LoginResquest {
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @NotBlank(message = "Senha é obrigatório")
    private String password;

    public @NotBlank(message = "Email é obrigatório") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email é obrigatório") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Senha é obrigatório") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Senha é obrigatório") String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResquest that = (LoginResquest) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "LoginResquest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
