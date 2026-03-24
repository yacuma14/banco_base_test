package banco.test.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PagoRequestDto {
	
	@NotBlank(message = "Concepto es mandatorio")
	private String concepto;
    
	@NotNull(message = "Cantidad productos es mandatorio")
	private Integer cantidadProductos;

	@NotBlank(message = "Realiza pago es mandatorio")
	private String realizaPago;

	@NotBlank(message = "Recibe pago es mandatorio")
	private String recibePago;

	@NotNull(message = "Monto es mandatorio")
	private Float monto;


}
