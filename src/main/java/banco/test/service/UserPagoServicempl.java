package banco.test.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import banco.test.entity.PagoEntity;
import banco.test.kafka.KafkaTopic;
import banco.test.exception.ExistPagoUsuarioException;
import banco.test.exception.NotFoundPagoException;
import banco.test.kafka.MessageProducer;
import banco.test.repository.PagoRepository;
import banco.test.dto.request.PagoRequestDto;
import banco.test.dto.response.PagoResponseDto;

import static banco.test.converter.PagoConverter.toPagoDto;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class UserPagoServicempl implements UserPagoService {

	private final PagoRepository pagoRepository;
	
	
    private final  MessageProducer messageProducer;
	
	private static final String PAGO_ESTATUS_INICIADO = "iniciado";  

	@Override
	@Transactional
	public PagoResponseDto generarPagoUsuario(PagoRequestDto pagoRequestDto) {
		

		pagoRepository.findByStatusPagoAndRealizaPago(PAGO_ESTATUS_INICIADO, pagoRequestDto.getRealizaPago()).
		ifPresent(pagoRequest -> {
			throw new  ExistPagoUsuarioException("Existe una transaccion en proceso  " + pagoRequest.getRealizaPago());
		});

		PagoEntity pagoEntity = toPagoDto(pagoRequestDto);
		pagoEntity.setStatusPago(PAGO_ESTATUS_INICIADO);
		
		pagoEntity = pagoRepository.save(pagoEntity);
		
		return PagoResponseDto.builder()
				 .idReferenciaPago(pagoEntity.getIdPago())
				 .usuarioPago(pagoEntity.getRealizaPago())
				.build();		
	}

	@Override
	public PagoEntity obtenerPagoPorId(Long id) {
		Optional<PagoEntity> pagoEntity = pagoRepository.findById(id);
		if(pagoEntity.isPresent()) {
		    return pagoEntity.get();	
		}else {
			throw new  NotFoundPagoException("No existe el pago con el id indicado  " + id);

		}
	}

	@Override
	public boolean actualizarStatusPago(Long id, String statusPago) {

		PagoEntity pagoEntity = obtenerPagoPorId(id);
        pagoEntity.setStatusPago(statusPago);
        
        pagoRepository.save(pagoEntity);	

		messageProducer.sendMessage(KafkaTopic.BANCO_BASE_TOPIC.getTopic(), pagoEntity.toString());
        
        return true;
	}

	@Override
	public List<PagoEntity> obtenerTodosPagosPorRealizaUsuario(String realizaPago) {
		return pagoRepository.findByRealizaPago(realizaPago);
	}

	@Override
	public List<PagoEntity> obtenerTodosPagos() {
		return pagoRepository.findAll();
	}

}
