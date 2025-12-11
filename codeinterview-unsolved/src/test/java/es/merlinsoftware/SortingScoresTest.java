package es.merlinsoftware;

import static org.junit.Assert.assertTrue;

import es.merlinsoftware.pojo.ProductSales;
import es.merlinsoftware.pojo.ProductStock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingScoresTest {

    @Test
    public void testResultIsNotEmpty() {
        List<ProductSales> productSales = Arrays.asList(
                new ProductSales(1L, 10000.0),
                new ProductSales(2L, 50000.0)
        );
        List<ProductStock> productStock = Arrays.asList(
                new ProductStock(1L, 100000L),
                new ProductStock(2L, 400000L)
        );

        List<Long> result = Solution.sortProductsByScores(50, 50, productStock, productSales);

        assertTrue(!result.isEmpty());     
        assertTrue(result.size() == 2);

        // Cálculo de scores:
        // 1L = 10000 * 50 + 100000 * 50 = 5.500.000
        // 2L = 50000 * 50 + 400000 * 50 = 22.500.000
        
        assertTrue(result.get(0).equals(2L));
    }


    @Test
    public void testHappyPath() {
        List<ProductSales> productSales = new ArrayList<>();
        productSales.add(new ProductSales(1L, 10000.0));
        productSales.add(new ProductSales(2L, 50000.0));
        productSales.add(new ProductSales(3L, 100000.0));
        productSales.add(new ProductSales(4L, 75000.0));


        List<ProductStock> productStock = new ArrayList<>();
        productStock.add(new ProductStock(1L, 100000L));
        productStock.add(new ProductStock(2L, 400000L));
        productStock.add(new ProductStock(3L, 200000L));
        productStock.add(new ProductStock(4L, 300000L));


        // Cálculo de scores:
        // 1L = 10000 * 50 + 100000 * 50 = 5.500.000
        // 2L = 50000 * 50 + 400000 * 50 = 22.500.000
        // 3L = 100000 * 50 + 200000 * 50 = 15.000.000
        // 4L = 75000 * 50 + 300000 * 50 = 18.750.000

        Long[] expectedResult = {2L, 4L, 3L, 1L};

        Assert.assertArrayEquals(Solution.sortProductsByScores(50, 50, productStock, productSales).toArray(),
                expectedResult);
    }

    @Test
    public void testMoreWeightToSales() {
        List<ProductSales> productSales = Arrays.asList(
                new ProductSales(1L, 10000.0),
                new ProductSales(2L, 50000.0),
                new ProductSales(3L, 100000.0),
                new ProductSales(4L, 75000.0)
        );

        List<ProductStock> productStock = Arrays.asList(
                new ProductStock(1L, 100000L),
                new ProductStock(2L, 400000L),
                new ProductStock(3L, 200000L),
                new ProductStock(4L, 300000L)
        );

        // Cálculo de scores:
        // 1L = 10000 * 80 + 100000 * 20 = 2.800.000
        // 2L = 50000 * 80 + 400000 * 20 = 12.000.000
        // 3L = 100000 * 80 + 200000 * 20 = 12.000.000
        // 4L = 75000 * 80 + 300000 * 20 = 12.000.000

        // Como 2L, 3L y 4L tienen el mismo score, se le da mayor prioridad al que tiene más ventas
        // ya que es la de mayor peso
        
        Long[] expectedResult = {3L, 4L, 2L, 1L};

        Assert.assertArrayEquals(Solution.sortProductsByScores(20, 80, productStock, productSales).toArray(), 
                expectedResult);
    }


    @Test
    public void testProductWithoutSalesOrStock() {
        List<ProductSales> productSales = Arrays.asList(
                new ProductSales(1L, 10000.0),
                new ProductSales(2L, 50000.0)
        );
        List<ProductStock> productStock = Arrays.asList(
                new ProductStock(1L, 100000L),
                new ProductStock(3L, 200000L)
        );

        // Cálculo de scores:
        // 1L = 10000 * 50 + 100000 * 50 = 5.500.000
        // 2L = 50000 * 50 = 22.500.000 = 2.500.000
        // 3L = 200000 * 50 = 15.000.000 = 10.000.000

        Long[] expectedResult = {3L, 1L, 2L};

        Assert.assertArrayEquals(Solution.sortProductsByScores(50, 50, productStock, productSales).toArray(), 
                expectedResult);
    }
}
