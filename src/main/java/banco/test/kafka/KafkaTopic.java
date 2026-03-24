package banco.test.kafka;

/**
 * Enum con los topics de Kafka usados en la aplicación.
 */
public enum KafkaTopic {

    BANCO_BASE_TOPIC("Banco_base_topic");

    private final String topic;

    KafkaTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}

