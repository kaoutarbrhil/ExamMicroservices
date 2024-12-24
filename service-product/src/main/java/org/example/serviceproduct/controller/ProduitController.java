package org.example.serviceproduct.controller;


import org.example.serviceproduct.model.Produit;
import org.example.serviceproduct.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping
    public ResponseEntity<Produit> ajouterProduit(@RequestBody Produit produit) {
        Produit nouveauProduit = produitService.ajouterProduit(produit);
        return ResponseEntity.status(201).body(nouveauProduit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> modifierProduit(@PathVariable Long id, @RequestBody Produit produitDetails) {
        Produit produitMisAJour = produitService.modifierProduit(id, produitDetails);
        return ResponseEntity.ok(produitMisAJour);
    }

    @GetMapping
    public List<Produit> consulterProduits() {
        return produitService.consulterProduits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> consulterProduitParId(@PathVariable Long id) {
        Produit produit = produitService.consulterProduitParId(id);
        return ResponseEntity.ok(produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        produitService.supprimerProduit(id);
        return ResponseEntity.noContent().build();
    }
}
