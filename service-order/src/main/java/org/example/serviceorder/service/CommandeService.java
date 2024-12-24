package org.example.serviceorder.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.serviceorder.model.Commande;
import org.example.serviceorder.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitGraphQLService produitGraphQLService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Nom du circuit breaker, à associer à la configuration dans application.yml
    @CircuitBreaker(name = "produitsCircuitBreaker", fallbackMethod = "fallbackVerifierDisponibiliteProduit")
    public Commande creerCommande(Commande commande) {
        // Appel à produitGraphQLService avec gestion du circuit breaker
        String disponibilite = produitGraphQLService.verifierDisponibiliteProduit(commande.getProduitId());

        if (disponibilite == null || disponibilite.isEmpty() || Integer.parseInt(disponibilite) < commande.getQuantite()) {
            throw new RuntimeException("Stock insuffisant pour le produit ID : " + commande.getProduitId());
        }

        commande.setStatut("CREATED");
        Commande savedCommande = commandeRepository.save(commande);
        kafkaProducerService.envoyerMessage(savedCommande);
        return savedCommande;
    }

    // Méthode de secours en cas d'échec du service Produit
    public Commande fallbackVerifierDisponibiliteProduit(Commande commande, Throwable throwable) {
        // Retourner une commande par défaut ou lancer une exception
        throw new RuntimeException("Le service Produits est temporairement indisponible. Impossible de vérifier la disponibilité.");
    }

    public Commande modifierStatutCommande(Long id, String statut) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatut(statut);
        return commandeRepository.save(commande);
    }

    public List<Commande> consulterCommandes() {
        return commandeRepository.findAll();
    }

    public Commande consulterCommandeParId(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }
}
