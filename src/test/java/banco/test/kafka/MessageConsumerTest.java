package banco.test.kafka;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MessageConsumerTest {

    @InjectMocks
    private MessageConsumer messageConsumer;

    @Test
    public void whenListen_thenLogCalled() {
        String message = "mensaje-prueba";
        messageConsumer.listen(message);
    }
}

