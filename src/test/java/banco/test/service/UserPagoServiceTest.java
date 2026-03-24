package banco.test.service;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import banco.test.entity.PagoEntity;
import banco.test.repository.PagoRepository;
import banco.test.dto.request.PagoRequestDto;
import banco.test.dto.response.PagoResponseDto;

@ExtendWith(MockitoExtension.class)
public class UserPagoServiceTest {

    @InjectMocks
    private UserPagoServicempl userPagoService;

    @Mock
    private PagoRepository pagoRepository;
    
    @Mock
    private banco.test.kafka.MessageProducer messageProducer;

    @Test
    public void givenPagoObject_whenCreatePago_thenPagoResponseDto() throws Exception {

        PagoEntity pagoEntity = new PagoEntity();
        pagoEntity.setIdPago(1L);
        pagoEntity.setRealizaPago("yacuma");

        PagoRequestDto pagoRequestDto = PagoRequestDto.builder()
                .cantidadProductos(1)
                .concepto("internet")
                .monto(200f)
                .realizaPago("yacuma")
                .recibePago("ATT")
                .build();

        when(pagoRepository.findByStatusPagoAndRealizaPago(any(), any())).thenReturn(Optional.empty());
        when(pagoRepository.save(any(PagoEntity.class))).thenReturn(pagoEntity);

        PagoResponseDto pagoResponseDto = userPagoService.generarPagoUsuario(pagoRequestDto);

        Assertions.assertEquals(1L, pagoResponseDto.getIdReferenciaPago());
    }

    @Test
    public void givenPagoObject_whenUpdatePago_thenResponseTrue() throws Exception {

        PagoEntity pagoEntity = new PagoEntity();
        pagoEntity.setIdPago(1L);
        pagoEntity.setRealizaPago("yacuma");

        when(pagoRepository.save(any(PagoEntity.class))).thenReturn(pagoEntity);
        when(pagoRepository.findById(any(Long.class))).thenReturn(Optional.of(pagoEntity));

        boolean result = userPagoService.actualizarStatusPago(1L, "finalizado");

        Assertions.assertTrue(result);
    }

}
