package banco.test.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import banco.test.bean.ErrorMessage;
import banco.test.entity.PagoEntity;
import banco.test.dto.request.PagoRequestDto;
import banco.test.service.UserPagoService;
import banco.test.dto.response.PagoResponseDto;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PagoController {

	@Autowired
	private UserPagoService userPagoService;

	@PostMapping(value = "/pagos", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Crear pago", description = "Crea un nuevo pago para un usuario y retorna el detalle del pago creado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Pago creado",
					content = @Content(schema = @Schema(implementation = PagoResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
	})
	public ResponseEntity<PagoResponseDto> crearPago(@Valid @RequestBody PagoRequestDto pagoRequestDto) {
		log.info("crearPago request {}", pagoRequestDto);
		PagoResponseDto response = userPagoService.generarPagoUsuario(pagoRequestDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/pagos")
	@Operation(summary = "Listar pagos", description = "Obtiene los pagos; opcionalmente filtra por usuario usando el query parameter 'usuario'.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de pagos",
					content = @Content(schema = @Schema(implementation = PagoEntity.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
	})
	public ResponseEntity<List<PagoEntity>> listarPagos(@RequestParam(name = "usuario", required = false) String usuario) {
		if (usuario != null && !usuario.isBlank()) {
			return ResponseEntity.ok(userPagoService.obtenerTodosPagosPorRealizaUsuario(usuario));
		}
		return ResponseEntity.ok(userPagoService.obtenerTodosPagos());
	}

	@GetMapping("/pagos/{id}")
	@Operation(summary = "Obtener pago por id", description = "Obtiene el pago correspondiente al id proporcionado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pago encontrado",
					content = @Content(schema = @Schema(implementation = PagoEntity.class))),
			@ApiResponse(responseCode = "404", description = "Pago no encontrado",
					content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
	})
	public ResponseEntity<PagoEntity> obtenerPagoPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userPagoService.obtenerPagoPorId(id));
	}

	@PatchMapping("/pagos/{id}/status")
	@Operation(summary = "Actualizar status de pago", description = "Actualiza el estado del pago especificado por id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Estado actualizado"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error",
					content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
	})
	public ResponseEntity<Void> actualizarStatusPago(@PathVariable("id") Long id, @RequestParam("status") String status) {
		userPagoService.actualizarStatusPago(id, status);
		return ResponseEntity.noContent().build();
	}
}
