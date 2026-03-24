package banco.test.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageConsumer {

    @KafkaListener(topics = "Banco_base_topic", groupId = "banco-base-group-id")
    public void listen(String message) {
		log.info("Received message {}",message);	
    }

}    