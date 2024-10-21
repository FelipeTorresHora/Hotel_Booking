package felipe.proj.felipehotel.controller;

import felipe.proj.felipehotel.dto.Response;
import felipe.proj.felipehotel.service.booking.IBookingService;
import felipe.proj.felipehotel.service.quarto.IQuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class QuartoController {

    @Autowired
    private IQuartoService quartoService;
    @Autowired
    private IBookingService iBookingService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewQuarto(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "roomType", required = false) String roomType,
            @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
            @RequestParam(value = "roomDescription", required = false) String roomDescription
    ) {

        if (photo == null || photo.isEmpty() || roomType == null || roomType.isBlank() || roomPrice == null || roomType.isBlank()) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(photo, roomType,roomPrice)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = quartoService.addNewQuarto(photo, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllQuartos() {
        Response response = quartoService.getAllQuartos();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getAllQuartoTipos() {
        return quartoService.getAllQuartoTipos();
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> getQuartoById(@PathVariable Long roomId) {
        Response response = quartoService.getQuartoById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-rooms")
    public ResponseEntity<Response> getAllDiponiveisQuartos() {
        Response response = quartoService.getAllDiponiveisQuartos();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> getDiponibilidadeQuartosByDataAndTipo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(required = false) String roomType
    ) {
        if (checkInDate == null || roomType == null || roomType.isBlank() || checkOutDate == null) {
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(checkInDate, roomType,checkOutDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response = quartoService.getDiponibilidadeQuartosByDataAndTipo(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateQuarto(@PathVariable Long roomId,
                                               @RequestParam(value = "photo", required = false) MultipartFile photo,
                                               @RequestParam(value = "roomType", required = false) String roomType,
                                               @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
                                               @RequestParam(value = "roomDescription", required = false) String roomDescription

    ) {
        Response response = quartoService.updateQuarto(roomId, roomDescription, roomType, roomPrice, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteQuarto(@PathVariable Long roomId) {
        Response response = quartoService.deleteQuarto(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
}
