package es.merlinsoftware;

import es.merlinsoftware.pojo.ProductSales;
import es.merlinsoftware.pojo.ProductStock;

import java.util.*;

public class Solution {

    public static List<Long> sortProductsByScores(int stockWeight, int salesWeight,
                                              List<ProductStock> productsStockInformation,
                                              List<ProductSales> productsSalesInformation) {

        // Crear mapas (productId, valor) para acceder rápido por productId
        Map<Long, Long> stockMap = new HashMap<>();
        Map<Long, Double> salesMap = new HashMap<>();

        // Asociar cada productId con su stock correspondiente
        for (ProductStock productStock : productsStockInformation) {
            stockMap.put(productStock.getProductId(), productStock.getAvailableStock());
        }
        // Asociar cada productId con su importe de ventas correspondiente
        for (ProductSales productSales : productsSalesInformation) {
            salesMap.put(productSales.getProductId(), productSales.getSalesAmount());
        }

        // Obtener todos los product IDs únicos
        Set<Long> allProductIds = new HashSet<>();
        allProductIds.addAll(stockMap.keySet());
        allProductIds.addAll(salesMap.keySet());

        // Calcular scores para cada producto
        Map<Long, Double> scores = new HashMap<>();
        for (Long productId : allProductIds) {
            long stock = stockMap.getOrDefault(productId, 0L);
            double sales = salesMap.getOrDefault(productId, 0.0);
            
            double score = sales * salesWeight + stock * stockWeight;
            scores.put(productId, score);
        }

        // Ordenar productos por score de mayor a menor, con desempate
        List<Long> sortedProducts = new ArrayList<>(allProductIds);
        sortedProducts.sort((p1, p2) -> {
            int scoreComparison = Double.compare(scores.get(p2), scores.get(p1));

            // Si uno es mayor, devolver esa comparación
            if (scoreComparison != 0) {
                return scoreComparison;
            }
            // Desempate: usar el criterio con mayor peso
            if (salesWeight > stockWeight) {
                return Double.compare(salesMap.getOrDefault(p2, 0.0), salesMap.getOrDefault(p1, 0.0));
            } else {
                return Long.compare(stockMap.getOrDefault(p2, 0L), stockMap.getOrDefault(p1, 0L));
            }
        });

        return sortedProducts;
    }

}
