package felipe.proj.felipehotel.repositorio;

import felipe.proj.felipehotel.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorio extends JpaRepository<User, Long> {
}
