package banco.test.converter;

import banco.test.entity.PagoEntity;
import banco.test.dto.request.PagoRequestDto;


public final class PagoConverter {

	PagoConverter() {
	}


	public static PagoEntity toPagoDto(final PagoRequestDto pagoDto) {
		PagoEntity pagoEntity =new PagoEntity();
		
		pagoEntity.setConcepto(pagoDto.getConcepto());
		pagoEntity.setCantidadProductos(pagoDto.getCantidadProductos());
		pagoEntity.setRealizaPago(pagoDto.getRealizaPago());
		pagoEntity.setRecibePago(pagoDto.getRealizaPago());
		pagoEntity.setMonto(pagoDto.getMonto());

  
		return pagoEntity;
	}

}