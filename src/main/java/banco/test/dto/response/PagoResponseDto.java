package banco.test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PagoResponseDto {

	private Long idReferenciaPago;
	private String usuarioPago ;
	
}
