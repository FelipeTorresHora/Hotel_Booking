package felipe.proj.felipehotel.service.quarto;
import felipe.proj.felipehotel.dto.QuartoDTO;
import felipe.proj.felipehotel.dto.Response;
import felipe.proj.felipehotel.entidades.Quarto;
import felipe.proj.felipehotel.excecao.GlobalExcecao;
import felipe.proj.felipehotel.repositorio.BookingRepositorio;
import felipe.proj.felipehotel.repositorio.QuartoRepositorio;
import felipe.proj.felipehotel.service.AwsS3Service;
import felipe.proj.felipehotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class QuartoService implements IQuartoService {

    @Autowired
    private QuartoRepositorio quartoRepositorio;
    @Autowired
    private BookingRepositorio bookingRepositorio;
    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Response addNewQuarto(MultipartFile photo, String tipoQuarto, BigDecimal precoQuarto, String descricaoQuarto) {
        Response response = new Response();

        try {
            String fotoUrlQuarto = awsS3Service.saveImageToS3(photo);
            Quarto room = new Quarto();
            room.setFotoUrlQuarto(fotoUrlQuarto);
            room.setTipoQuarto(tipoQuarto);
            room.setPrecoQuarto(precoQuarto);
            room.setDescricaoQuarto(descricaoQuarto);
            Quarto savedQuarto = quartoRepositorio.save(room);
            QuartoDTO roomDTO = Utils.mapQuartoEntityToQuartoDTO(savedQuarto);
            response.setStatusCode(200);
                response.setMessage("Sucesso");
            response.setQuarto(roomDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar quarto: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllQuartoTipos() {
        return quartoRepositorio.findAllQuartoTipos();
    }

    @Override
    public Response getAllQuartos() {
        Response response = new Response();

        try {
            List<Quarto> roomList = quartoRepositorio.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<QuartoDTO> roomDTOList = Utils.mapQuartoListEntityToQuartoListDTO(roomList);
            response.setStatusCode(200);
                response.setMessage("Sucesso");
            response.setQuartoList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar quarto: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteQuarto(Long roomId) {
        Response response = new Response();

        try {
            quartoRepositorio.findById(roomId).orElseThrow(() -> new GlobalExcecao("Quarto não encontrado"));
            quartoRepositorio.deleteById(roomId);
            response.setStatusCode(200);
                response.setMessage("Sucesso");

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar quarto: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateQuarto(Long roomId, String descricaoQuarto, String tipoQuarto, BigDecimal precoQuarto, MultipartFile photo) {
        Response response = new Response();

        try {
            String fotoUrlQuarto = null;
            if (photo != null && !photo.isEmpty()) {
                fotoUrlQuarto = awsS3Service.saveImageToS3(photo);
            }
            Quarto room = quartoRepositorio.findById(roomId).orElseThrow(() -> new GlobalExcecao("Quarto não encontrado"));
            if (tipoQuarto != null) room.setTipoQuarto(tipoQuarto);
            if (precoQuarto != null) room.setPrecoQuarto(precoQuarto);
            if (descricaoQuarto != null) room.setDescricaoQuarto(descricaoQuarto);
            if (fotoUrlQuarto != null) room.setFotoUrlQuarto(fotoUrlQuarto);

            Quarto updatedQuarto = quartoRepositorio.save(room);
            QuartoDTO roomDTO = Utils.mapQuartoEntityToQuartoDTO(updatedQuarto);

            response.setStatusCode(200);
                response.setMessage("Sucesso");
            response.setQuarto(roomDTO);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar quarto: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getQuartoById(Long roomId) {
        Response response = new Response();

        try {
            Quarto room = quartoRepositorio.findById(roomId).orElseThrow(() -> new GlobalExcecao("Quarto não encontrado"));
            QuartoDTO roomDTO = Utils.mapQuartoEntityToQuartoDTOPlusBookings(room);
            response.setStatusCode(200);
                response.setMessage("Sucesso");
            response.setQuarto(roomDTO);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar quarto: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getDiponibilidadeQuartosByDataAndTipo(LocalDate checkInDate, LocalDate checkOutDate, String tipoQuarto) {
        Response response = new Response();

        try {
            List<Quarto> availableQuartos = quartoRepositorio.findDiponibilidadeQuartosByDataAndTipo(checkInDate, checkOutDate, tipoQuarto);
            List<QuartoDTO> roomDTOList = Utils.mapQuartoListEntityToQuartoListDTO(availableQuartos);
            response.setStatusCode(200);
                response.setMessage("Sucesso");
            response.setQuartoList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar quarto: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllDiponiveisQuartos() {
        Response response = new Response();

        try {
            List<Quarto> roomList = quartoRepositorio.getAllDiponiveisQuartos();
            List<QuartoDTO> roomDTOList = Utils.mapQuartoListEntityToQuartoListDTO(roomList);
            response.setStatusCode(200);
                response.setMessage("Sucesso");
            response.setQuartoList(roomDTOList);

        } catch (GlobalExcecao e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Erro ao salvar quarto: " + e.getMessage());
        }
        return response;
    }
}

