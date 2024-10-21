package felipe.proj.felipehotel.service.quarto;

import felipe.proj.felipehotel.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IQuartoService {
    Response addNewQuarto(MultipartFile photo, String tipoQuarto, BigDecimal precoQuarto, String descricaoQuarto);

    List<String> getAllQuartoTipos();

    Response getAllQuartos();

    Response deleteQuarto(Long quartoId);

    Response updateQuarto(Long quartoId, String descricaoQuarto, String tipoQuarto, BigDecimal precoQuarto, MultipartFile photo);

    Response getQuartoById(Long quartoId);

    Response getDiponibilidadeQuartosByDataAndTipo(LocalDate checkInData, LocalDate checkOutData, String tipoQuarto);

    Response getAllDiponiveisQuartos();
}
