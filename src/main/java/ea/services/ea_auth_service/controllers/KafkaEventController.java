package ea.services.ea_auth_service.controllers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ea.services.ea_auth_service.kafka.model.UserEvent;
import ea.services.ea_auth_service.kafka.producers.KafkaProducerService;

@RestController
@RequestMapping("/kafka")
public class KafkaEventController {
    private final KafkaProducerService producerService;

    public KafkaEventController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/publish-event")
    public String publishEvent(@RequestBody UserEvent event) {
        producerService.sendEvent(event);
        return "UserEvent published to Kafka";
    }
}
