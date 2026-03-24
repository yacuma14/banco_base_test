package banco.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import banco.test.dto.request.PagoRequestDto;
import banco.test.service.UserPagoService;
import banco.test.dto.response.PagoResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class UserPagoControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private LocalValidatorFactoryBean validator;

    @InjectMocks
    private PagoController pagoController;

    @Mock
    private UserPagoService userPagoService;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(pagoController)
                .setValidator(validator)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Test
    public void givenPagoObject_whenCreatePago_thenReturnSavedPago() throws Exception{

        PagoRequestDto request = PagoRequestDto.builder()
                .concepto("internet")
                .cantidadProductos(1)
                .realizaPago("usuarioA")
                .recibePago("proveedorX")
                .monto(200f)
                .build();

        PagoResponseDto pagoResponseDto = PagoResponseDto.builder().idReferenciaPago(1L).usuarioPago("usuarioA").build();
        Mockito.when(userPagoService.generarPagoUsuario(any(PagoRequestDto.class))).thenReturn(pagoResponseDto);

        ResultActions response = mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idReferenciaPago").value(1));

    }

    @Test
    public void givenPagoObject_whenCreatePago_thenReturnBadRequest() throws Exception{

        PagoRequestDto pagoRequestDto = PagoRequestDto.builder()
                .cantidadProductos(1)
                .build();

        ResultActions response = mockMvc.perform(post("/api/v1/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pagoRequestDto))
                .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void givenUpdatePagoObject_whenUpdatePago_thenReturnNoContentPago() throws Exception{

        Mockito.when(userPagoService.actualizarStatusPago(anyLong(), anyString())).thenReturn(true);

        ResultActions response = mockMvc.perform(patch("/api/v1/pagos/1/status")
                .param("status", "finalizado")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andDo(print())
                .andExpect(status().isNoContent());

    }

}
