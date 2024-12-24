package org.example.serviceorder.service;


import org.example.serviceorder.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "commande-topic";

    @Autowired
    private KafkaTemplate<String, Commande> kafkaTemplate;

    public void envoyerMessage(Commande commande) {
        kafkaTemplate.send(TOPIC, commande);
        System.out.println("Message envoyé au topic Kafka : " + commande.getId());
    }
}
