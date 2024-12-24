package org.example.serviceorder.controller;

import org.example.serviceorder.model.Commande;
import org.example.serviceorder.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    /**
     * Créer une nouvelle commande.
     * Utilise le service CommandeService pour créer une commande.
     */
    @PostMapping
    public ResponseEntity<Commande> creerCommande(@RequestBody Commande commande) {
        try {
            Commande nouvelleCommande = commandeService.creerCommande(commande);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleCommande);
        } catch (RuntimeException e) {
            // Si le produit est en rupture de stock ou en cas d'erreur liée au produit
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Autres erreurs génériques
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Modifier le statut d'une commande existante.
     */
    @PutMapping("/{id}/statut")
    public ResponseEntity<Commande> modifierStatutCommande(@PathVariable Long id, @RequestParam String statut) {
        try {
            Commande commandeMisAJour = commandeService.modifierStatutCommande(id, statut);
            return ResponseEntity.ok(commandeMisAJour);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Commande non trouvée
        }
    }

    /**
     * Récupérer la liste de toutes les commandes.
     */
    @GetMapping
    public ResponseEntity<List<Commande>> consulterCommandes() {
        List<Commande> commandes = commandeService.consulterCommandes();
        return ResponseEntity.ok(commandes);
    }

    /**
     * Récupérer une commande par son identifiant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Commande> consulterCommandeParId(@PathVariable Long id) {
        try {
            Commande commande = commandeService.consulterCommandeParId(id);
            return ResponseEntity.ok(commande);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Commande non trouvée
        }
    }

    /**
     * Gérer les erreurs liées au service Produit (méthode de secours du circuit breaker).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erreur lors de la création de la commande : " + e.getMessage());
    }
}
