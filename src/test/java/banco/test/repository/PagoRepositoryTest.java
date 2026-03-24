package banco.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import banco.test.entity.PagoEntity;

@ExtendWith(MockitoExtension.class)
public class PagoRepositoryTest {

    @Mock
    private PagoRepository pagoRepository;

    @Test
    public void whenSaveAndFind_thenReturnPago() {
        PagoEntity pago = new PagoEntity();
        pago.setConcepto("test");
        pago.setCantidadProductos(1);
        pago.setRealizaPago("usuarioTest");
        pago.setRecibePago("proveedorTest");
        pago.setMonto(100f);
        pago.setStatusPago("iniciado");

        when(pagoRepository.save(any(PagoEntity.class))).thenAnswer(invocation -> {
            PagoEntity arg = invocation.getArgument(0);
            arg.setIdPago(1L);
            return arg;
        });

        PagoEntity saved = pagoRepository.save(pago);

        when(pagoRepository.findByRealizaPago("usuarioTest")).thenReturn(Collections.singletonList(saved));

        List<PagoEntity> found = pagoRepository.findByRealizaPago("usuarioTest");

        assertThat(saved.getIdPago()).isNotNull();
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getRealizaPago()).isEqualTo("usuarioTest");
    }
}

