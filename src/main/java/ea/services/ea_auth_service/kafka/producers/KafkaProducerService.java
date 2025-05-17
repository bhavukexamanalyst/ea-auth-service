package ea.services.ea_auth_service.kafka.producers;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import ea.services.ea_auth_service.kafka.model.UserEvent;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(UserEvent event) {
        kafkaTemplate.send("user-events", event);
    }
}
