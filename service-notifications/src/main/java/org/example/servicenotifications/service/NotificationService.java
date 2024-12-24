package org.example.servicenotifications.service;


import org.example.servicenotifications.model.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = "commande-topic", groupId = "notifications-group")
    public void consommerMessage(Notification notification) {
        System.out.println("Notification reçue pour la commande ID : " + notification.getCommandeId());
        System.out.println("Message : " + notification.getMessage());
        // Ajoutez ici une logique pour envoyer la notification, ex. par e-mail ou SMS
    }
}
