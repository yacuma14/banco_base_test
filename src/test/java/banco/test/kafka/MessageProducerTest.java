package banco.test.kafka;

import banco.test.kafka.MessageProducer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class MessageProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private MessageProducer messageProducer;

    @Test
    public void whenSendMessage_thenKafkaTemplateSendCalled() {
        String topic = "Banco_base_topic";
        String message = "test-message";

        messageProducer.sendMessage(topic, message);

        verify(kafkaTemplate, times(1)).send(topic, message);
    }
}

