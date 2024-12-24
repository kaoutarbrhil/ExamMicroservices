package org.example.serviceorder.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProduitGraphQLService {

    private final WebClient webClient;

    public ProduitGraphQLService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://service-product/graphql").build();
    }

    public String verifierDisponibiliteProduit(Long produitId) {
        String query = "{" +
                "  produit(id: " + produitId + ") {" +
                "    stock" +
                "  }" +
                "}";

        return webClient.post()
                .bodyValue("{\"query\":\"" + query + "\"}")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
