package banco.test.service;

import java.util.List;

import banco.test.entity.PagoEntity;
import banco.test.dto.request.PagoRequestDto;
import banco.test.dto.response.PagoResponseDto;

public interface UserPagoService {

	 PagoResponseDto generarPagoUsuario(PagoRequestDto userSyncRequestDto);
	
	 PagoEntity obtenerPagoPorId(Long id);
	
	 boolean actualizarStatusPago(Long id, String statusPago);
	
	 List<PagoEntity> obtenerTodosPagosPorRealizaUsuario(String usuario);

	public List<PagoEntity> obtenerTodosPagos();
}