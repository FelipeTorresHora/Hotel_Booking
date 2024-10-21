package felipe.proj.felipehotel.repositorio;

import felipe.proj.felipehotel.entidades.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface QuartoRepositorio extends JpaRepository<Quarto, Long> {
    @Query("SELECT DISTINCT r.tipoQuarto FROM Quarto r")
    List<String> findAllQuartoTipos();


    @Query("SELECT r FROM Quarto r WHERE r.tipoQuarto LIKE %:tipoQuarto% AND r.id NOT IN (SELECT bk.quarto.id FROM Booking bk WHERE" +
            "(bk.checkInData <= :checkOutData) AND (bk.checkOutData >= :checkInData))")
    List<Quarto> findDiponibilidadeQuartosByDataAndTipo(LocalDate checkInData, LocalDate checkOutData, String tipoQuarto);


    @Query("SELECT r FROM Quarto r WHERE r.id NOT IN (SELECT b.quarto.id FROM Booking b)")
    List<Quarto> getAllDiponiveisQuartos();
}
